package com.mall.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ToastUtils;
import com.mall.demo.R;
import com.mall.demo.base.activity.BaseActivity;
import com.mall.demo.bean.GoodsBo;
import com.mall.demo.bean.GoodsEventBo;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;
import com.mall.demo.utils.LogUtils;
import com.mall.demo.utils.MyConstant;
import com.mall.demo.utils.TimeUtil;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {

    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.tvPbName)
    AppCompatTextView tvPbName;
    @BindView(R.id.etFbContent)
    AppCompatEditText etFbContent;
    @BindView(R.id.etFbTitle)
    AppCompatEditText etFbTitle;
    @BindView(R.id.etFbDesc)
    AppCompatEditText etFbDesc;
    @BindView(R.id.etFbImg)
    AppCompatEditText etFbImg;
    @BindView(R.id.etFbPrice)
    AppCompatEditText etFbPrice;
    @BindView(R.id.tvPbSubmit)
    AppCompatTextView tvPbSubmit;

    private MainPresenter editPresenter;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, EditActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvCommonTitle.setText("发布商品");
        String account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        String name = "发布者: " + account;
        String time = "      日期: " + TimeUtil.getNowDate();
        tvPbName.setText(name + time);
    }

    @Override
    protected void initPresenter() {
        editPresenter = new MainPresenter(new DataManager());
    }

    @OnClick({R.id.ivCommonBack, R.id.tvPbSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCommonBack:
                finish();
                break;
            case R.id.tvPbSubmit:
                String title = etFbTitle.getText().toString().trim();
                String desc = etFbDesc.getText().toString().trim();
                String content = etFbContent.getText().toString().trim();
                String img = etFbImg.getText().toString().trim();
                String price = etFbPrice.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showShort("标题不能为空");
                    return;
                }
                if (TextUtils.isEmpty(desc)) {
                    ToastUtils.showShort("副标题不能为空");
                    return;
                }
                if (TextUtils.isEmpty(img)) {
                    ToastUtils.showShort("图片链接不能为空");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort("价格不能为空");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort("内容不能为空");
                    return;
                }
                postPublishGoods(title, desc, content, img, price);
                break;
            default:
                break;
        }
    }

    private void postPublishGoods(String title, String desc, String content, String img, String price) {
        editPresenter.postPublishGoods(title, desc, content, img, price, new NetCallBack<GoodsBo>() {
            @Override
            public void onLoadSuccess(GoodsBo dataBo) {
                ToastUtils.showShort("发布成功");
                EventBus.getDefault().post(new GoodsEventBo());
                finish();
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtils.showShort("发布失败，请重试!");
                LogUtils.e("postPublishGoods==" + errMsg);
            }
        });
    }
}
