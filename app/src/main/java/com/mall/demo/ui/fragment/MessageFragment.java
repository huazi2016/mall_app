package com.mall.demo.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.fragment.BaseFragment;
import com.mall.demo.bean.ChatListBo;
import com.mall.demo.bean.EventBo;
import com.mall.demo.bean.MallBo;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainContract;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;
import com.mall.demo.utils.InfoUtil;
import com.mall.demo.utils.MyConstant;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MessageFragment extends BaseFragment {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.rcOrderList)
    RecyclerView rcOrderList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter orderPresenter;
    private OrderListAdapter orderAdapter;
    private final List<MallBo> dataList = new ArrayList();

    public static MessageFragment getInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.message_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        orderPresenter = new MainPresenter(new DataManager());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void refreshInfo() {
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        if (orderPresenter == null) {
            orderPresenter = new MainPresenter(new DataManager());
        }
        orderPresenter.loadChatList(account, new NetCallBack<List<ChatListBo>>() {
            @Override
            public void onLoadSuccess(List<ChatListBo> data) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    @Override
    protected void init() {
        initStatusBar();
        ivCommonBack.setVisibility(View.GONE);
        tvCommonTitle.setText("订单");
        refreshInfo();
    }

    private class OrderListAdapter extends BaseQuickAdapter<MallBo, BaseViewHolder> {

        public OrderListAdapter(int layoutResId, @Nullable List<MallBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, MallBo itemBo) {
            ImageView tvImg = holder.getView(R.id.tvImg);
            InfoUtil.setImg(itemBo.img, tvImg);
            holder.setText(R.id.tvName, itemBo.name);
            holder.setText(R.id.tvStatus, "发货状态: 已发货");
            holder.setText(R.id.tvFinalPrice, "成交价: " + itemBo.price);
            holder.itemView.setOnClickListener(v -> {
                //跳转
            });
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

    public void startLoadingView() {
        EventBo e = new EventBo();
        e.target = EventBo.TARGET_MAIN;
        e.type = EventBo.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        EventBo e = new EventBo();
        e.target = EventBo.TARGET_MAIN;
        e.type = EventBo.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }
}