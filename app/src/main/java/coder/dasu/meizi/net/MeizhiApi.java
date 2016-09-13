package coder.dasu.meizi.net;

import coder.dasu.meizi.data.bean.Meizi;
import coder.dasu.meizi.net.response.GankDataResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sxq on 2016/9/12.
 */
public class MeizhiApi {

    interface Api{
        @GET("福利/{count}/{page}")
        Call<GankDataResponse<Meizi>> getMeizhi(@Path("count") int count, @Path("page") int page);
    }
}
