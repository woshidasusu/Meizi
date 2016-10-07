package coder.dasu.meizi.utils;

import java.util.List;

/**
 * Created by dasu on 2016/9/28.
 * https://github.com/woshidasusu/Meizi
 */
public class ListUtils {

    private ListUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 全部为空
     * @param list
     * @param moreList
     * @return
     */
    public static boolean isEmpty(List list, List... moreList) {
        if (!isEmpty(list)) {
            return false;
        }
        for (List l:moreList) {
            if (!isEmpty(l)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * 全部不为空
     * @param list
     * @param moreList
     * @return
     */
    public static boolean isNotEmpty(List list, List... moreList) {
        if (isEmpty(list)) {
            return false;
        }
        for (List l : moreList) {
            if (isEmpty(l)) {
                return false;
            }
        }
        return true;
    }
}
