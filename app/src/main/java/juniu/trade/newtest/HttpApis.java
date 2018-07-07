package juniu.trade.newtest;

import com.alibaba.fastjson.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpApis {

    /**
     * 登陆
     *
     * @param mobile
     * @param password
     * @param device
     * @return
     */
    @FormUrlEncoded
    @POST("/v1/user/login")
    Observable<HttpResult<JSONObject>> login(
            @Field(HttpParameter.MOBILE) String mobile,
            @Field(HttpParameter.PASSWORD) String password,
            @Field(HttpParameter.DEVICE) String device);


    /**
     * 获取店铺信息
     */
    @GET("/v1/store/profile/{store_id}")
    Observable<HttpResult<JSONObject>> storeProfile(
            @Path(HttpParameter.STORE_ID) String storeId,
            @Query(HttpParameter.TOKEN) String token
    );

}




