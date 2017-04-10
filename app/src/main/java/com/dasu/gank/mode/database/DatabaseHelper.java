package com.dasu.gank.mode.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by dasu on 2017/4/11.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private static final String TEMP_SUFFIX = "_temp_";

    private SQLiteDatabase db_r = null; // readable database
    private SQLiteDatabase db_w = null; // writable database

    private static ConfDbHelper mDbHelper;

    private ConfDbHelper(Context context) {
        super(context, ConfDataStore.DB_FILE, null, ConfDataStore.DB_VERSION);
    }

    public static synchronized ConfDbHelper getInstance(Context context) {
        if(mDbHelper == null) {
            mDbHelper = new ConfDbHelper(context);
        }
        return mDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (LogLevel.DEV) {
            DevLog.d(TAG, "onCreate()...");
        }

        Collection<ConfDbTable> tables = ConfDataStore.sPBXDbTables.values();
        Iterator<ConfDbTable> iterator = tables.iterator();

        try {
            db.beginTransaction();
            while (iterator.hasNext()) {
                iterator.next().onCreate(db);
            }
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            //TODO Implement proper error handling
            if (LogLevel.MARKET) {
                MarketLog.e(TAG, "onCreate(): DB creation failed:", e);
            }

            throw new RuntimeException("DB creation failed: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Performs DB upgrade to the new scheme.
     * The data in matching tables' columns is preserved during upgrade.
     * The following operations are supported:
     * 1. Add new tables.
     * 2. Remove tables.
     * 3. Add new columns to existing tables.
     *    New columns are filled with the default column values according to the table scheme,
     *    or with NULL if no default value is specified.
     * 4. Remove columns.
     * If any additional operations need to be performed during upgrade for a specific table
     * (e.g. new column contents should be calculated based on other columns,
     * or existing columns shall be filled with the default values instead of copying from the old table, etc.),
     * the PBXDbTable.onUpgrade() method should be overridden for the specific table.
     * If DB upgrade needs additional operations across the whole DB (e.g. move data from one table to another, etc.),
     * this method may be modified accordingly for the specific DB versions.
     *
     * @see SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (LogLevel.MARKET) {
            MarketLog.d(TAG, "onUpgrade(oldVersion = " + oldVersion + ", newVersion = " + newVersion + ")...");
        }

        //Get table names in the old DB
        Collection<String> old_tables = ConfDbTable.listTables(db);
        if (old_tables == null || old_tables.size() == 0) {
            if (LogLevel.MARKET) {
                MarketLog.d(TAG, "onUpgrade(): no existing tables; calling onCreate()...");
            }
            onCreate(db);
            return;
        }

        //Get table names in the new DB
        Set<String> new_tables = ConfDataStore.sPBXDbTables.keySet();

        try {
            db.beginTransaction();
            //Remove old tables which are not in the new DB scheme
            HashSet<String> obsolete_tables = new HashSet<String>();
            for (String table : old_tables) {
                if (!new_tables.contains(table)) {
                    if (LogLevel.DEV) {
                        DevLog.d(TAG, "onUpgrade(): remove table: " + table);
                    }
                    ConfDbTable.dropTable(db, table);
                    obsolete_tables.add(table);
                }
            }
            old_tables.removeAll(obsolete_tables);

            //Create and upgrade new tables
            ConfDbTable table_descriptor;
            for (String table : new_tables) {
                table_descriptor = ConfDataStore.sPBXDbTables.get(table);

                //Check if the new table exists in the old DB
                if (old_tables.contains(table)) {
                    String temp_name = getTempTableName(table, old_tables, new_tables);
                    table_descriptor.onUpgrade(db, oldVersion, newVersion, temp_name);
                } else {
                    table_descriptor.onCreate(db);
                }
            }
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            if (LogLevel.MARKET) {
                MarketLog.e(TAG, "onUpgrade(): DB upgrade failed:", e);
            }

            throw new RuntimeException("DB upgrade failed: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }


    /**
     * Generates temporary name for use during table upgrade.
     * The name is guaranteed to be unique, i.e. not used as table name
     * in the old and new DB schemes.
     *
     * @param tableName
     * @param oldTableNames
     * @param newTableNames
     * @return
     */
    private String getTempTableName(String tableName, Collection<String> oldTableNames, Set<String> newTableNames) {
        String temp_name_base = tableName + TEMP_SUFFIX;

        if (!oldTableNames.contains(temp_name_base) && !newTableNames.contains(temp_name_base)) {
            return temp_name_base;
        }

        Random random = new Random();
        String temp_name;
        for (;;) {
            temp_name = temp_name_base + random.nextInt();
            if (!oldTableNames.contains(temp_name) && !newTableNames.contains(temp_name)) {
                return temp_name;
            }
        }
    }


    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (db_r == null || !db_r.isOpen()) {
            try {
                db_r = super.getReadableDatabase();
            } catch (SQLiteException e) {
                //TODO Implement proper error handling
                db_r = null;
                if (LogLevel.MARKET) {
                    MarketLog.e(TAG, "getReadableDatabase(): Error opening", e);
                }

                throw e;
            }
        }
        return db_r;
    }


    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (db_w == null || !db_w.isOpen() || db_w.isReadOnly()) {
            try {
                db_w = super.getWritableDatabase();
            } catch (SQLiteException e) {
                //TODO Implement proper error handling
                db_w = null;
                if (LogLevel.MARKET) {
                    MarketLog.e(TAG, "getWritableDatabase(): Error", e);
                }

                throw e;
            }
        }
        return db_w;
    }

}
