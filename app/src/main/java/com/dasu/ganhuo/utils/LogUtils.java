package com.dasu.ganhuo.utils;

import android.util.Log;

import com.dasu.ganhuo.BuildConfig;

import java.util.Formatter;


/**
 * Created by dasu on 2017/4/10.
 *
 * Log 工具类，release 自动关闭日志输出
 * 支持打印当前线程，方法，支持点击方法跳转
 */
public class LogUtils {
    private static final String TAG = LogUtils.class.getSimpleName();

    private static final boolean debug = BuildConfig.DEBUG;

    private static final int V = 0x01;
    private static final int D = 0x02;
    private static final int I = 0x04;
    private static final int W = 0x08;
    private static final int E = 0x10;
    private static final int A = 0x20;

    public static void v(String tag, String msg) {
        log(V, tag, msg);
    }

    public static void d(String tag, String msg) {
        log(D, tag, msg);
    }

    public static void i(String tag, String msg) {
        log(I, tag, msg);
    }

    public static void w(String tag, String msg) {
        log(W, tag, msg);
    }

    public static void e(String tag, String msg) {
        log(E, tag, msg);
    }

    public static void a(String tag, String msg) {
        log(A, tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
    }

    private static void log(int type, String tag, String msg) {
        if (!debug) {
            return;
        }
        switch (type) {
            case V:
                break;
            case D:
            case I:
            case W:
            case E:
            case A:
                msg = getHeader() + msg;
                break;
        }
        switch (type) {
            case V:
                Log.v(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case A:
                Log.wtf(tag, msg);
                break;
        }
    }

    private static String getHeader() {
        StackTraceElement targetElement = Thread.currentThread().getStackTrace()[5];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0];
        }
        String head = new Formatter().format("Thread: %s, %s(%s.java:%d)" + System.getProperty("line.separator"),
                Thread.currentThread().getName(),
                targetElement.getMethodName(),
                className,
                targetElement.getLineNumber()).toString();
        return head;
    }
}
