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

}
