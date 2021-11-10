package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.GoodsBo;
import com.mall.demo.bean.OrderBo;
import com.mall.demo.bean.OrderDetailBo;
import com.mall.demo.bean.OrderEventBo;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tvGoodImg)
    ImageView ivGoodImg;

    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;

    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;

    @BindView(R.id.tvOrderAddress)
    TextView tvOrderAddress;

    @BindView(R.id.tvOrderPhone)
    TextView tvOrderPhone;

    @BindView(R.id.tvOrderNum)
    TextView tvOrderNum;

    @BindView(R.id.tvOrderStatus)
    TextView tvOrderStatus;

    @BindView(R.id.etOrderAddress)
    AppCompatEditText etOrderAddress;

    @BindView(R.id.etOrderPhone)
    AppCompatEditText etOrderPhone;

    @BindView(R.id.etOrderNum)
    AppCompatEditText etOrderNum;

    @BindView(R.id.tvOrderDelete)
    AppCompatTextView tvOrderDelete;

    @BindView(R.id.tvOrderEdit)
    AppCompatTextView tvOrderEdit;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    private Context mContext;
    private GoodsBo infoBo = null;
    private String orderId = "";
    private MainPresenter mPresenter;

    public static void launchActivity(Activity activity, String orderId) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("order_id", orderId);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
        if (getIntent() != null) {
            orderId = getIntent().getStringExtra("order_id");
            postGetOrder();
        }
    }

    private void postGetOrder() {
        if (mPresenter == null) {
            mPresenter = new MainPresenter(new DataManager());
        }
        mPresenter.postGetOrder(orderId, new NetCallBack<OrderDetailBo>() {
            @Override
            public void onLoadSuccess(OrderDetailBo data) {
                if (data != null) {
                    Glide.with(activity)
                            .load(data.bigImg)
                            .placeholder(R.drawable.icon_goods)
                            .error(R.drawable.icon_goods)
                            .into(ivGoodImg);
                    tvGoodsName.setText(data.title);
                    tvGoodsPrice.setText("¥" + data.price);
                    tvOrderAddress.setText("收货地址：" + data.address);
                    tvOrderPhone.setText("手机号：" + data.phone);
                    tvOrderNum.setText("数量：" + data.num);
                    tvOrderStatus.setText("数量：" + data.status);
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                //ToastUtils.showShort("网络异常");
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("订单详情");
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

    @OnClick({R.id.tvOrderDelete, R.id.tvOrderEdit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvOrderDelete:
                mPresenter.postRemoveOrder(orderId, new NetCallBack<OrderBo>() {
                    @Override
                    public void onLoadSuccess(OrderBo data) {
                        ToastUtils.showShort("删除成功");
                        EventBus.getDefault().post(new OrderEventBo());
                        finish();
                    }

                    @Override
                    public void onLoadFailed(String errMsg) {
                        ToastUtils.showShort("删除失败, 请稍后重试!");
                    }
                });
                break;
            case R.id.tvOrderEdit:
                String address = etOrderAddress.getText().toString().trim();
                String phone = etOrderPhone.getText().toString().trim();
                String num = etOrderNum.getText().toString().trim();
                if (TextUtils.isEmpty(address) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(num)) {
                    ToastUtils.showShort("请完善修改信息");
                    return;
                }
                mPresenter.postUpdateOrder(orderId, address, phone, num, new NetCallBack<OrderBo>() {
                    @Override
                    public void onLoadSuccess(OrderBo data) {
                        ToastUtils.showShort("修改成功");
                        EventBus.getDefault().post(new OrderEventBo());
                        finish();
                    }

                    @Override
                    public void onLoadFailed(String errMsg) {
                        ToastUtils.showShort("修改失败, 请稍后重试!");
                    }
                });
                break;
            default:
                break;
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
