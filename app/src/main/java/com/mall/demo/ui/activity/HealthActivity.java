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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.application.MyApp;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.MallBo;
import com.mall.demo.bean.PlanBo;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.dao.MallDao;
import com.mall.demo.utils.InfoUtil;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HealthActivity extends BaseActivity {

    @BindView(R.id.rcOrderList)
    RecyclerView rcOrderList;

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private Context mContext;
    private final List<PlanBo> dataList = new ArrayList();
    private OrderListAdapter orderAdapter;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, HealthActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_health;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
        for (int i = 0; i < 4; i++) {
            PlanBo bo = new PlanBo();
            if (i == 0) {
                bo.name = "身高";
                bo.value = "1.80m";
            } else if (i == 1) {
                bo.name = "体重";
                bo.value = "60公斤";
            } else if (i == 2) {
                bo.name = "血压";
                bo.value = "68mmHg";
            } else if (i == 3) {
                bo.name = "心跳";
                bo.value = "76/分钟";
            }
            dataList.add(bo);
        }
        setInfo();
    }

    @Override
    protected void initPresenter() {

    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("健康数据");
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
        rcOrderList.setVisibility(View.VISIBLE);
        mLayoutError.setVisibility(View.GONE);
        rcOrderList.setLayoutManager(new LinearLayoutManager(activity));
        orderAdapter = new OrderListAdapter(R.layout.item_health_list, dataList);
        View footView = getLayoutInflater().inflate(R.layout.common_footview, null);
        orderAdapter.addFooterView(footView);
        rcOrderList.setAdapter(orderAdapter);
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
