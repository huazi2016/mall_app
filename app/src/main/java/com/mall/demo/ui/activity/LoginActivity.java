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
import com.mall.demo.bean.UserBo;
import com.mall.demo.custom.CustomEditText;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.dao.UserDao;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isName = false;
                boolean isPwd = false;
                UserDao userDao = MyApp.room.userDao();
                List<UserBo> userList = userDao.getAll();
                if (CollectionUtils.isNotEmpty(userList)) {
                    for (int i = 0; i < userList.size(); i++) {
                        String user = userList.get(i).username;
                        String pwd = userList.get(i).pwd;
                        if (user.equalsIgnoreCase(userName)) {
                            isName = true;
                        }
                        if (passWord.equalsIgnoreCase(pwd)) {
                            isPwd = true;
                        }
                    }
                }
                final boolean is2Name = isName;
                final boolean is2Pwd = isPwd;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (is2Name && is2Pwd) {
                            MainActivity.launchActivity(activity);
                        } else if (!is2Name) {
                            ToastUtils.showShort("账号不存在");
                        } else {
                            ToastUtils.showShort("密码错误");
                        }
                    }
                });
            }
        }).start();
        stopAnim();
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
