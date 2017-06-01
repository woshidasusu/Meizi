package com.dasu.ganhuo.mode.logic.base;

/**
 * Created by dasu on 2017/4/17.
 *
 * 数据源接口，加载数据，以及加载结果的回调
 */

public abstract class AbstractDataSource<T> {
    public static final int TYPE_LOCAL = 1;    //从本地加载数据
    public static final int TYPE_SERVICE = 0;  //从网络加载数据

    public interface LoadCallback<T> {
        void onSuccess(T data);
        void onError(String description);
    }

    private int mType;

    public AbstractDataSource() {
        //默认从服务器加载数据
        mType = TYPE_SERVICE;
    }

    public AbstractDataSource(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public abstract void load(LoadCallback<T> callback);

}
