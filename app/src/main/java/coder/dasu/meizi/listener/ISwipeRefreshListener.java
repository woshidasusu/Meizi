package coder.dasu.meizi.listener;

/**
 * Created by sxq on 2016/9/15.
 */
public interface ISwipeRefreshListener {

    /**
     * 下拉刷新的回调函数，需要显示调用，并不会自动调用
     */
    void loadData();

    /**
     * 控制刷新加载动画的显示与否
     * @param enable true 显示加载动画
     */
    void setRefresh(boolean enable);
}
