package com.mall.demo.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.fragment.BaseFragment;
import com.mall.demo.bean.EventBo;
import com.mall.demo.bean.OrderBo;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class OrderFragment extends BaseFragment {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.rcOrderList)
    RecyclerView rcOrderList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter mPresenter;
    private OrderListAdapter orderAdapter;
    private final List<OrderBo> dataList = new ArrayList();

    public static OrderFragment getInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.order_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
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
        if (mPresenter == null) {
            mPresenter = new MainPresenter(new DataManager());
        }
        mPresenter.postOrderList(new NetCallBack<List<OrderBo>>() {
            @Override
            public void onLoadSuccess(List<OrderBo> data) {
                dataList.clear();
                dataList.addAll(data);
                if (CollectionUtils.isNotEmpty(dataList)) {
                    rcOrderList.setVisibility(View.VISIBLE);
                    mLayoutError.setVisibility(View.GONE);
                    rcOrderList.setLayoutManager(new LinearLayoutManager(activity));
                    orderAdapter = new OrderListAdapter(R.layout.item_order_list, dataList);
                    View footView = getLayoutInflater().inflate(R.layout.common_footview, null);
                    orderAdapter.addFooterView(footView);
                    rcOrderList.setAdapter(orderAdapter);
                } else {
                    rcOrderList.setVisibility(View.GONE);
                    mLayoutError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtils.showShort("网络异常");
                rcOrderList.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.VISIBLE);
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

    private class OrderListAdapter extends BaseQuickAdapter<OrderBo, BaseViewHolder> {

        public OrderListAdapter(int layoutResId, @Nullable List<OrderBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, OrderBo itemBo) {
            ImageView ivImg = holder.getView(R.id.tvImg);
            //InfoUtil.setImg(itemBo.img, ivImg);
            Glide.with(activity).load(itemBo.img).into(ivImg);
            holder.setText(R.id.tvName, itemBo.title);
            holder.setText(R.id.tvStatus, itemBo.status);
            holder.setText(R.id.tvFinalPrice, "成交价: " + itemBo.price);
            holder.itemView.setOnClickListener(v -> {
                //跳转
                // TODO: 11/9/21 补充跳转
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