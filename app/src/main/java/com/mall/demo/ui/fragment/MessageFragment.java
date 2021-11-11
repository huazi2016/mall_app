package com.mall.demo.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.fragment.BaseFragment;
import com.mall.demo.bean.MsgListBo;
import com.mall.demo.bean.EventBo;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.net.NetCallBack;
import com.mall.demo.ui.activity.ChatActivity;
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
    @BindView(R.id.rcMsgList)
    RecyclerView rcMsgList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;
    @BindView(R.id.tvError)
    TextView tvError;

    private MainPresenter mPresenter;
    private MsgListAdapter msgAdapter;
    private final List<MsgListBo> dataList = new ArrayList();
    private String account = "";

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
        mPresenter.postMessageList(new NetCallBack<List<MsgListBo>>() {
            @Override
            public void onLoadSuccess(List<MsgListBo> data) {
                dataList.clear();
                dataList.addAll(data);
                if (CollectionUtils.isNotEmpty(dataList)) {
                    rcMsgList.setVisibility(View.VISIBLE);
                    mLayoutError.setVisibility(View.GONE);
                    rcMsgList.setLayoutManager(new LinearLayoutManager(activity));
                    msgAdapter = new MsgListAdapter(R.layout.item_msg_list, dataList);
                    View footView = getLayoutInflater().inflate(R.layout.common_footview, null);
                    msgAdapter.addFooterView(footView);
                    rcMsgList.setAdapter(msgAdapter);
                } else {
                    rcMsgList.setVisibility(View.GONE);
                    mLayoutError.setVisibility(View.VISIBLE);
                    tvError.setText("暂无消息");
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtils.showShort("网络异常");
                rcMsgList.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.VISIBLE);
                tvError.setText("暂无消息");
            }
        });
    }

    @Override
    protected void init() {
        initStatusBar();
        account = MMKV.defaultMMKV().decodeString(MyConstant.ACCOUNT);
        ivCommonBack.setVisibility(View.GONE);
        tvCommonTitle.setText("消息");
        refreshInfo();
    }

    private class MsgListAdapter extends BaseQuickAdapter<MsgListBo, BaseViewHolder> {

        public MsgListAdapter(int layoutResId, @Nullable List<MsgListBo> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, MsgListBo itemBo) {
            String name = itemBo.sendName;
            if (account.equalsIgnoreCase(itemBo.sendName)) {
                name = itemBo.receiveName;
            }
            holder.setText(R.id.tvMsgSender, name);
            holder.setText(R.id.tvMsgContent, itemBo.content);
            holder.setText(R.id.tvMsgTime, itemBo.createTime);
            String finalName = name;
            holder.itemView.setOnClickListener(v -> {
                //跳转聊天页
                ChatActivity.launchActivity(activity, itemBo.msgId, finalName);
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