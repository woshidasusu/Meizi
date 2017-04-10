/**
 * Copyright (C) 2015, Easiio, Inc.
 * All Rights Reserved.
 */
package com.dasu.gank.mode.database;


import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.LinkedHashMap;

/**
 * ConfDataStore
 *
 * @author gavin.zhuang
 */
public class ConfDataStore {


    private ConfDataStore() {
    }


    public static final int DB_VERSION = 35;

    static final String DB_FILE = "easiio_conf.db";

    public interface ConfColumns {
        public static final String USER_ID = "UserId";
        public static final String DEFAULT_SORT_ORDER = "_ID ASC";
    }

    static LinkedHashMap<String, ConfDbTable> sPBXDbTables = new LinkedHashMap<String, ConfDbTable>();

    static {
        sPBXDbTables.put(CurrentUserTable.getInstance().getName(), CurrentUserTable.getInstance());
        sPBXDbTables.put(AccountInfoTable.getInstance().getName(), AccountInfoTable.getInstance());
        sPBXDbTables.put(ContactsTable.getInstance().getName(), ContactsTable.getInstance());
        sPBXDbTables.put(GroupsTable.getInstance().getName(), GroupsTable.getInstance());
        sPBXDbTables.put(GroupMemberTable.getInstance().getName(), GroupMemberTable.getInstance());
        sPBXDbTables.put(ConferencesTable.getInstance().getName(), ConferencesTable.getInstance());
        sPBXDbTables.put(ConferenceMemberTable.getInstance().getName(), ConferenceMemberTable.getInstance());
        sPBXDbTables.put(VideoAudioTable.getInstance().getName(), VideoAudioTable.getInstance());
        sPBXDbTables.put(CompanyUserTable.getInstance().getName(), CompanyUserTable.getInstance());
        sPBXDbTables.put(CompanyUserInfoTable.getInstance().getName(), CompanyUserInfoTable.getInstance());
        sPBXDbTables.put(CompanyGroupUserTable.getInstance().getName(), CompanyGroupUserTable.getInstance());
        sPBXDbTables.put(HistoryTable.getInstance().getName(), HistoryTable.getInstance());
//        sPBXDbTables.put(MonitorTable.getInstance().getName(), MonitorTable.getInstance());
        sPBXDbTables.put(OnvifMonitorTable.getInstance().getName(), OnvifMonitorTable.getInstance());
        sPBXDbTables.put(ConfGroupTable.getInstance().getName(), ConfGroupTable.getInstance());
        sPBXDbTables.put(ConfGroupMembersTable.getInstance().getName(), ConfGroupMembersTable.getInstance());
    }
}
