package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.application.MyApp;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.UserBo;
import com.mall.demo.custom.CustomEditText;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.dao.UserDao;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;
import com.mall.demo.utils.MyConstant;
import com.tencent.mmkv.MMKV;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.login)
    Button mLoginButton;

    @BindView(R.id.go_register)
    TextView mRegister;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;

    private Context mContext;

    private String mRegisterName;
    private MainPresenter mPresenter;
    private String mRegisterPassword;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        try {
            mRegisterName = getIntent().getExtras().getString("name");
            mRegisterPassword = getIntent().getExtras().getString("pwd");
            mUsername.setText(mRegisterName);
            mPassword.setText(mRegisterPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolbar();
        mLoginButton.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("欢迎登录");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.login)
    public void login() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        startAnim();
        String userName = mUsername.getText().toString();
        String passWord = mPassword.getText().toString();
        mPresenter.postLogin(userName, passWord, new NetCallBack<LoginBo>() {
            @Override
            public void onLoadSuccess(LoginBo data) {
                stopAnim();
                MMKV.defaultMMKV().encode(MyConstant.ACCOUNT, data.account);
                MMKV.defaultMMKV().encode(MyConstant.HEADURL, data.headUrl);
                MMKV.defaultMMKV().encode(MyConstant.USERNAME, data.username);
                MainActivity.launchActivity(activity);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                stopAnim();
                ToastUtils.showShort(errMsg);
            }
        });
    }

    @OnClick(R.id.go_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }
}
