package coder.dasu.meizi.net.response;

import java.util.List;

import coder.dasu.meizi.data.entity.Data;

/**
 * Created by dasu on 2016/9/13.
 * https://github.com/woshidasusu/Meizi
 */
public class GankDataResponse extends GankResponse {

    /**
     *  {
     *     "error": false,
     *     "results": [
     *          {
     *              "_id": "57eb0100421aa95de3b8ab00",
     *               "createdAt": "2016-09-28T07:30:08.163Z",
     *              "desc": "Android \u4e0b\u96ea\u6548\u679c",
     *              "images": [
     *              "http://img.gank.io/cf2f253d-a7d7-453e-a948-3dda9d9984ae"
     *              ],
     *              "publishedAt": "2016-09-28T11:35:12.91Z",
     *              "source": "chrome",
     *              "type": "Android",
     *              "url": "https://github.com/HelloVass/SnowingView",
     *              "used": true,
     *              "who": "\u4ee3\u7801\u5bb6"
     *          }
     *     ]
     *  }
     */


    public List<Data> results;

}
