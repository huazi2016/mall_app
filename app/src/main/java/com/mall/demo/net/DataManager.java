package com.mall.demo.net;

import com.google.gson.Gson;
import com.mall.demo.bean.MsgListBo;
import com.mall.demo.bean.GoodsBo;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.OrderBo;
import com.mall.demo.utils.LogUtils;
import com.mall.demo.utils.MyConstant;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DataManager {

    private final ApiService apiService;

    public DataManager() {
        this.apiService = RetrofitUtils.get().retrofit().create(ApiService.class);
    }

    public Observable<BaseResponse<LoginBo>> postRegister(String name, String pwd, String role) {
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", name);
        jsonMap.put("pwd", pwd);
        jsonMap.put("role", role);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postRegister(body);
    }

    public Observable<BaseResponse<LoginBo>> postLogin(String name, String pwd) {
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", name);
        jsonMap.put("pwd", pwd);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postLogin(body);
    }

    public Observable<BaseResponse<LoginBo>> postLogout() {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postLogin(body);
    }

    public Observable<BaseResponse<List<MsgListBo>>> postMessageList() {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postMessageList(body);
    }

    public Observable<BaseResponse<LoginBo>> postUpdateUser(String name, String url) {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        jsonMap.put("username", name);
        jsonMap.put("headurl", url);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postUpdateUser(body);
    }

    public Observable<BaseResponse<List<GoodsBo>>> postSearchGoods(String searchKey) {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        jsonMap.put("searchKey", searchKey);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postSearchGoods(body);
    }

    public Observable<BaseResponse<GoodsBo>> postGetGoods(String goodsId) {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        jsonMap.put("goodsId", goodsId);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postGetGoods(body);
    }

    public Observable<BaseResponse<GoodsBo>> postPayGoods(String goodsId) {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        jsonMap.put("goodsId", goodsId);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postPayGoods(body);
    }

    public Observable<BaseResponse<List<OrderBo>>> postOrderList() {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postOrderList(body);
    }

    public Observable<BaseResponse<GoodsBo>> postPublishGoods(String title, String desc, String content, String img, String price) {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        HashMap<String, String> jsonMap = new HashMap();
        jsonMap.put("account", account);
        jsonMap.put("title", title);
        jsonMap.put("desc", desc);
        jsonMap.put("content", content);
        jsonMap.put("img", img);
        jsonMap.put("price", price);
        String json = new Gson().toJson(jsonMap);
        LogUtils.d("okhttp:==" + json);
        String contentType = "application/json;charset=UTF-8";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return apiService.postPublishGoods(body);
    }
}
