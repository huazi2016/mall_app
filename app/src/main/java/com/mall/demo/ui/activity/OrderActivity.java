package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.OrderBo;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.rcMsgList)
    RecyclerView rcOrderList;

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private Context mContext;
    private final List<OrderBo> dataList = new ArrayList();
    private OrderListAdapter orderAdapter;
    private MainPresenter mPresenter;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, OrderActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_order;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("订单列表");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        if (mPresenter == null) {
            mPresenter = new MainPresenter(new DataManager());
        }
        startAnim();
        mPresenter.postOrderList(new NetCallBack<List<OrderBo>>() {
            @Override
            public void onLoadSuccess(List<OrderBo> data) {
                stopAnim();
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
                stopAnim();
                ToastUtils.showShort("网络异常");
                rcOrderList.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                // TODO: 11/9/21 补充订单详情
                //itemBo.orderId;
            });
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
