package coder.dasu.meizi.net.response;

import java.util.List;

/**
 * Created by sxq on 2016/9/13.
 */
public abstract class GankResponse<T> {

    public boolean error;

    public List<T> results;
}
