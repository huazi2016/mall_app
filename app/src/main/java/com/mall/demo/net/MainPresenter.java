package com.mall.demo.net;


import com.mall.demo.bean.ChatListBo;
import com.mall.demo.bean.LoginBo;

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

    public void loadChatList(String account, NetCallBack<List<ChatListBo>> callBack) {
        Observable<BaseResponse<List<ChatListBo>>> observable = dataManager.loadChatList(account);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ChatListBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<ChatListBo>> resultBo) {
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

    public void sendMsg(String role, NetCallBack<List<ChatListBo>> callBack) {
        Observable<BaseResponse<List<ChatListBo>>> observable = dataManager.loadChatList(role);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ChatListBo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NotNull BaseResponse<List<ChatListBo>> resultBo) {
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
