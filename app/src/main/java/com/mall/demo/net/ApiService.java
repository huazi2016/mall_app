package com.mall.demo.net;

import com.mall.demo.bean.ChatListBo;
import com.mall.demo.bean.LoginBo;

import java.util.List;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * api service
 */
public interface ApiService {

    public final String  BASE_URL = "http://8.211.180.248:8000/";

    @POST(BASE_URL + "rest/register")
    Observable<BaseResponse<LoginBo>> postRegister(@Body RequestBody body);

    @POST(BASE_URL + "rest/login")
    Observable<BaseResponse<LoginBo>> postLogin(@Body RequestBody body);

    @GET(BASE_URL + "rest/login")
    Observable<BaseResponse<List<ChatListBo>>> getChatList(@Query("account") String account);

}
