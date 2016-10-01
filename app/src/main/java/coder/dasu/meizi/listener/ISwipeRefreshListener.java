package coder.dasu.meizi.listener;

/**
 * Created by dasu on 2016/9/15.
 * https://github.com/woshidasusu/Meizi
 */
public interface ISwipeRefreshListener {

    /**
     * 下拉刷新的回调函数，需要显示调用，并不会自动调用
     */
    void loadData();

    /**
     * 只是控制刷新加载动画的显示与否，并不会触发OnRefreshListener回调
     * @param enable true 显示加载动画, false 取消动画，为防止加载过程太快，建议实现时默认延长1s
     */
    void setRefresh(boolean enable);

    /**
     * 立即取消加载动画，{@link #setRefresh(boolean)} 默认取消延长的补充
     */
    void dismissAnimation();

    boolean isRefreshing();
}
