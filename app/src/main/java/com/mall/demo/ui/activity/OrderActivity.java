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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.application.MyApp;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.EventBo;
import com.mall.demo.bean.MallBo;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.dao.MallDao;
import com.mall.demo.utils.InfoUtil;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.rcOrderList)
    RecyclerView rcOrderList;

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private Context mContext;
    private final List<MallBo> dataList = new ArrayList();
    private OrderListAdapter orderAdapter;

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
        setInfo();
    }

    @Override
    protected void initPresenter() {

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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setInfo() {
        String userName = MMKV.defaultMMKV().decodeString("user_name");
        dataList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MallDao mallDao = MyApp.room.mallDao();
                List<MallBo> mallList = mallDao.getAllInfo();
                if (CollectionUtils.isNotEmpty(mallList)) {
                    for (MallBo mallBo : mallList) {
                        if (mallBo.order == 1 && userName.equalsIgnoreCase(mallBo.username)) {
                            dataList.add(mallBo);
                        }
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                });
            }
        }).start();
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

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }

}
