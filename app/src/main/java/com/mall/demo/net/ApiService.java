package com.mall.demo.net;

import com.mall.demo.bean.MsgListBo;
import com.mall.demo.bean.GoodsBo;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.OrderBo;
import com.mall.demo.bean.OrderDetailBo;

import java.util.List;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    Observable<BaseResponse<List<GoodsBo>>> postSearchGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/getGoods")
    Observable<BaseResponse<GoodsBo>> postGetGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/payGoods")
    Observable<BaseResponse<GoodsBo>> postPayGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/publishGoods")
    Observable<BaseResponse<GoodsBo>> postPublishGoods(@Body RequestBody body);

    @POST(BASE_URL + "rest/orderList")
    Observable<BaseResponse<List<OrderBo>>> postOrderList(@Body RequestBody body);

    @POST(BASE_URL + "rest/getOrder")
    Observable<BaseResponse<OrderDetailBo>> postGetOrder(@Body RequestBody body);

    @POST(BASE_URL + "rest/updateOrder")
    Observable<BaseResponse<OrderBo>> postUpdateOrder(@Body RequestBody body);

    @POST(BASE_URL + "rest/removeOrder")
    Observable<BaseResponse<OrderBo>> postRemoveOrder(@Body RequestBody body);

    @POST(BASE_URL + "rest/messageHistory")
    Observable<BaseResponse<List<MsgListBo>>> postMessageHistory(@Body RequestBody body);

    @POST(BASE_URL + "rest/messageList")
    Observable<BaseResponse<List<MsgListBo>>> postMessageList(@Body RequestBody body);

    @POST(BASE_URL + "rest/sendMessage")
    Observable<BaseResponse<MsgListBo>> postSendMessage(@Body RequestBody body);

}
