package coder.dasu.meizi.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by suxq on 2017/4/7.
 */

public class FileUtils {

    /**
     * 判断外部存储是否可用
     *
     * @return true: 可用
     */
    public static boolean isSDcardAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 获取外部存储的根目录路径
     *
     * @return
     * @throws IOException
     */
    public static String getSDcardPath() throws IOException{
        if (isSDcardAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            throw new FileNotFoundException("没有外部存储");
        }
    }

    /**
     * 获取程序的外部存储的数据存放根目录 {/gank}
     *
     * @return
     */
    public static String getAppRootDirectoryPath(){
        if (isSDcardAvailable()) {
            try {
                String path = getSDcardPath();
                File file = new File(path, "gank");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取程序外部存储下的download目录路径
     *
     * @return
     */
    public static String getAppDownloadDirectory() {
        if (isSDcardAvailable()) {
            String path = getAppRootDirectoryPath();
            File file = new File(path, "download");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 创建子目录
     *
     * @param parent
     * @param name
     * @return
     */
    public static boolean createDirectory(String parent, String name) {
        File file = new File(parent, name);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 复制文件，默认删除原文件
     *
     * @param sourceFile
     * @param destFile
     */
    public static void copyFile(File sourceFile, File destFile) {
        copyFile(sourceFile, destFile, true);
    }

    /**
     * 复制文件
     *
     * @param sourceFile
     * @param destFile
     * @param isDeleteSource
     */
    public static void copyFile(File sourceFile, File destFile, boolean isDeleteSource) {
        try {
            int byteRead = 0;
            if (sourceFile.exists()) {
                FileInputStream is = new FileInputStream(sourceFile);
                FileOutputStream os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                while ((byteRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, byteRead);
                }
                is.close();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param file
     * @throws IOException
     */
    public static void deleteFile(File file) throws IOException{
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        } else {
            file.delete();
        }
    }



}
