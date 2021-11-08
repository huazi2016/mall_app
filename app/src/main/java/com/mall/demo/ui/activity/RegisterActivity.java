package com.mall.demo.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.application.MyApp;
import com.mall.demo.base.utils.LoginUtils;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.UserBo;
import com.mall.demo.custom.CustomEditText;
import com.mall.demo.custom.loading.LoadingView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.mall.demo.dao.UserDao;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainContract;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/26
 * Time: 15:26
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.repassword)
    CustomEditText mRePassword;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.register)
    Button mRegisterButton;

    @BindView(R.id.rgRoleGroup)
    RadioGroup rgRoleGroup;
    @BindView(R.id.rbRole01)
    RadioButton rbRole01;
    @BindView(R.id.rbRole02)
    RadioButton rbRole02;

    private Context mContext;
    private MainPresenter mPresenter;
    private String role = "买家";


    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
        mRegisterButton.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
        rgRoleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int viewId) {
                if (viewId == R.id.rbRole01) {
                    role = rbRole01.getText().toString().trim();
                } else if  (viewId == R.id.rbRole02) {
                    role = rbRole02.getText().toString().trim();
                }
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
        //mPresenter.attachView(this);
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle(R.string.register);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @OnClick(R.id.register)
    public void register() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText()) ||
                TextUtils.isEmpty(mRePassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        String userName = mUsername.getText().toString();
        String passWord = mPassword.getText().toString();
        String rePassword = mRePassword.getText().toString();
        if (!passWord.equalsIgnoreCase(rePassword)) {
            ToastUtils.showShort("密码不一致");
            return;
        }
        startAnim();
        mPresenter.postRegister(userName, passWord, role, new NetCallBack<LoginBo>() {
            @Override
            public void onLoadSuccess(LoginBo data) {
                stopAnim();
                ToastUtils.showShort("注册成功");
                finish();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                stopAnim();
                ToastUtils.showShort(errMsg);
                LogUtils.d("login111:==" + errMsg);
            }
        });
    }

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }

}
