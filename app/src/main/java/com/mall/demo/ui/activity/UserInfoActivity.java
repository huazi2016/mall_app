package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.PlanBo;
import com.mall.demo.bean.UserEventBo;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;
import com.mall.demo.utils.MyConstant;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.etHeadUrl)
    EditText etHeadUrl;

    @BindView(R.id.btnUserInfo)
    Button btnUserInfo;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    private Context mContext;
    private MainPresenter mPresenter;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, UserInfoActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
        btnUserInfo.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("个人资料");
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

    @OnClick({R.id.btnUserInfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUserInfo: {
                String userName = etUserName.getText().toString().trim();
                String headUrl = etHeadUrl.getText().toString().trim();
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(headUrl)) {
                    ToastUtils.showShort("请完善信息");
                    return;
                }
                startAnim();
                mPresenter.postUpdateUser(userName, headUrl, new NetCallBack<LoginBo>() {
                    @Override
                    public void onLoadSuccess(LoginBo data) {
                        stopAnim();
                        ToastUtils.showShort("修改成功");
                        MMKV.defaultMMKV().encode(MyConstant.HEADURL, data.headUrl);
                        MMKV.defaultMMKV().encode(MyConstant.USERNAME, data.username);
                        EventBus.getDefault().post(new UserEventBo(data.username, data.headUrl));
                        finish();
                    }

                    @Override
                    public void onLoadFailed(String errMsg) {
                        stopAnim();
                        ToastUtils.showShort("修改失败");
                    }
                });
                break;
            }
            default: {
                break;
            }
        }
    }

    private class OrderListAdapter extends BaseQuickAdapter<PlanBo, BaseViewHolder> {

        public OrderListAdapter(int layoutResId, @Nullable List<PlanBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, PlanBo itemBo) {
            holder.setText(R.id.tvPlanName, itemBo.name);
            holder.setText(R.id.tvPlanValue, itemBo.value);
        }
    }

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }

}
