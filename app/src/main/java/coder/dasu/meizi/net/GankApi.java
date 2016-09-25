package coder.dasu.meizi.net;

import coder.dasu.meizi.data.bean.Meizi;
import coder.dasu.meizi.net.response.GankDataResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dasu on 2016/8/25.
 */
public interface GankApi {

    /**
     * 默认一次加载的数量
     */
    int DEFAULT_COUNT = 10;

    @GET("data/福利/{count}/{page}")
    Call<GankDataResponse<Meizi>> getMeizhi(@Path("count") int count, @Path("page") int page);

}
