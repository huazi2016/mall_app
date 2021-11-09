package com.mall.demo.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mall.demo.R;
import com.mall.demo.base.fragment.BaseFragment;
import com.mall.demo.bean.LoginBo;
import com.mall.demo.bean.UserEventBo;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.ui.activity.UserInfoActivity;
import com.mall.demo.ui.activity.LoginActivity;
import com.mall.demo.utils.MyConstant;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Suppress;


public class MineFragment extends BaseFragment {

    @BindView(R.id.tvMineName)
    AppCompatTextView tvMineName;
    @BindView(R.id.ivMineHead)
    AppCompatImageView ivMineHead;

    private MainPresenter mPresenter;

    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUsrInfo(UserEventBo eventBo) {
        tvMineName.setText(eventBo.username);
        Glide.with(activity).load(eventBo.headUrl).into(ivMineHead);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        String name = MMKV.defaultMMKV().decodeString(MyConstant.USERNAME);
        String headurl = MMKV.defaultMMKV().decodeString(MyConstant.HEADURL);
        tvMineName.setText(name);
        Glide.with(activity).load(headurl).into(ivMineHead);
    }

    @OnClick({R.id.clMineItem02, R.id.clMineItem03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clMineItem02: {
                new XPopup.Builder(activity).asConfirm("", "确认要退出登录吗?", "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                mPresenter.postLogout();
                                LoginActivity.launchActivity(activity);
                                MMKV.defaultMMKV().encode(MyConstant.ACCOUNT, "");
                                MMKV.defaultMMKV().encode(MyConstant.USERNAME, "");
                                MMKV.defaultMMKV().encode(MyConstant.HEADURL, "");
                                activity.finish();
                            }
                        }, new OnCancelListener() {
                            @Override
                            public void onCancel() {

                            }
                        }, false).show();
                break;
            }
            case R.id.clMineItem03: {
                UserInfoActivity.launchActivity(activity);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}