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

    @POST(BASE_URL + "rest/logout")
    Observable<BaseResponse<LoginBo>> postLogout(@Body RequestBody body);

    @POST(BASE_URL + "rest/updateUser")
    Observable<BaseResponse<LoginBo>> postUpdateUser(@Body RequestBody body);

    @POST(BASE_URL + "rest/searchGoods")
    Observable<BaseResponse<LoginBo>> postSearchGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/getGoods")
    Observable<BaseResponse<LoginBo>> postGetGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/payGoods")
    Observable<BaseResponse<LoginBo>> postPayGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/publishGoods")
    Observable<BaseResponse<LoginBo>> posPublishGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/orderList")
    Observable<BaseResponse<LoginBo>> postOrderList(@Body RequestBody body);

    @POST(BASE_URL + "rest/getOrder")
    Observable<BaseResponse<LoginBo>> postGetOrder(@Body RequestBody body);

    @POST(BASE_URL + "rest/updateOrder")
    Observable<BaseResponse<LoginBo>> postUpdateOrder(@Body RequestBody body);

    @POST(BASE_URL + "rest/removeOrder")
    Observable<BaseResponse<LoginBo>> postRemoveOrder(@Body RequestBody body);

    @POST(BASE_URL + "rest/messageHistory")
    Observable<BaseResponse<LoginBo>> postMessageHistory(@Body RequestBody body);

    @POST(BASE_URL + "rest/messageList")
    Observable<BaseResponse<List<ChatListBo>>> postMessageList(@Body RequestBody body);

}
