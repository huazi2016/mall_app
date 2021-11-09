package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;

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
public class GoodsActivity extends BaseActivity {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tvGoodImg)
    ImageView ivGoodImg;

    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;

    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;

    @BindView(R.id.tvGoodsContent)
    TextView tvGoodsContent;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    private Context mContext;
    private GoodsBo infoBo = null;
    private String goodId = "";
    private MainPresenter mPresenter;

    public static void launchActivity(Activity activity, GoodsBo itemBo) {
        Intent intent = new Intent(activity, GoodsActivity.class);
        intent.putExtra("mall_info", itemBo);
        activity.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
        if (getIntent() != null) {
            infoBo = (GoodsBo) getIntent().getSerializableExtra("mall_info");
            goodId = infoBo.goodsId;
            if (mPresenter == null) {
                mPresenter = new MainPresenter(new DataManager());
            }
            mPresenter.postGetGoods(goodId, new NetCallBack<GoodsBo>() {
                @Override
                public void onLoadSuccess(GoodsBo data) {
                    if (data != null) {
                        //InfoUtil.setImg(data.img, tvGoodImg);
                        Glide.with(activity).load(data.bigImg).into(ivGoodImg);
                        tvGoodsName.setText(data.title);
                        tvGoodsPrice.setText("¥" + data.price);
                        tvGoodsContent.setText(data.content);
                    }
                }

                @Override
                public void onLoadFailed(String errMsg) {
                    ToastUtils.showShort("网络异常");
                }
            });
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(new DataManager());
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("商品详情");
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


    @OnClick(R.id.tvBuyBtn)
    public void buy() {
        new XPopup.Builder(activity).asConfirm("", "确认支付吗?", "取消", "支付",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mPresenter.postPayGoods(goodId, new NetCallBack<GoodsBo>() {
                            @Override
                            public void onLoadSuccess(GoodsBo data) {
                                ToastUtils.showShort("支付完成, 请查看订单");
                                OrderActivity.launchActivity(activity);
                            }

                            @Override
                            public void onLoadFailed(String errMsg) {
                                ToastUtils.showShort("支付失败, 服务异常");
                            }
                        });
                    }
                }, new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                }, false).show();
    }

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }

}
