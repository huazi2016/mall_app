package com.mall.demo.net;


import com.mall.demo.bean.MsgListBo;
import com.mall.demo.bean.GoodsBo;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.OrderBo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> {

    private Disposable disposable;
    private DataManager dataManager;

    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void postRegister(String name, String pwd, String role, NetCallBack<LoginBo> callBack) {
        Observable<BaseResponse<LoginBo>> observable = dataManager.postRegister(name, pwd, role);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<LoginBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postLogin(String name, String pwd, NetCallBack<LoginBo> callBack) {
        Observable<BaseResponse<LoginBo>> observable = dataManager.postLogin(name, pwd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<LoginBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postSearchGoods(String key, NetCallBack<List<GoodsBo>> callBack) {
        Observable<BaseResponse<List<GoodsBo>>> observable = dataManager.postSearchGoods(key);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<GoodsBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<GoodsBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postGetGoods(String goodsId, NetCallBack<GoodsBo> callBack) {
        Observable<BaseResponse<GoodsBo>> observable = dataManager.postGetGoods(goodsId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<GoodsBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<GoodsBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postPayGoods(String goodsId, NetCallBack<GoodsBo> callBack) {
        Observable<BaseResponse<GoodsBo>> observable = dataManager.postPayGoods(goodsId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<GoodsBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<GoodsBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postOrderList(NetCallBack<List<OrderBo>> callBack) {
        Observable<BaseResponse<List<OrderBo>>> observable = dataManager.postOrderList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<OrderBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<OrderBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postUpdateUser(String name, String url, NetCallBack<LoginBo> callBack) {
        Observable<BaseResponse<LoginBo>> observable = dataManager.postUpdateUser(name, url);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<LoginBo> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postLogout() {
        Observable<BaseResponse<LoginBo>> observable = dataManager.postLogout();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginBo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<LoginBo> resultBo) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postMessageList(NetCallBack<List<MsgListBo>> callBack) {
        Observable<BaseResponse<List<MsgListBo>>> observable = dataManager.postMessageList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<MsgListBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<MsgListBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void sendMsg(String role, NetCallBack<List<MsgListBo>> callBack) {
        Observable<BaseResponse<List<MsgListBo>>> observable = dataManager.postMessageList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<MsgListBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<MsgListBo>> resultBo) {
                        if (resultBo.getErrorCode() != 0) {
                            callBack.onLoadFailed(resultBo.getErrorMsg());
                            return;
                        }
                        callBack.onLoadSuccess(resultBo.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onLoadFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
