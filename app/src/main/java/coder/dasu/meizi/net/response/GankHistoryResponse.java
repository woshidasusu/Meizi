package coder.dasu.meizi.net.response;

import java.util.List;

/**
 * Created by dasu on 2016/10/3.
 * https://github.com/woshidasusu/Meizi
 */
public class GankHistoryResponse extends GankResponse {

    /**
     * {
     *    "error": false,
     *    "results": [
     *        "2016-09-30",
     *        "2016-09-29",
     *        "2016-09-28",
     *        "2016-09-27",
     *        ****
     *    ]
     *  }
     */

    public List<String> results;
}
