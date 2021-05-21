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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mall.demo.R;
import com.mall.demo.base.fragment.BaseFragment;
import com.mall.demo.bean.EventBo;
import com.mall.demo.net.DataManager;
import com.mall.demo.net.MainContract;
import com.mall.demo.net.MainPresenter;
import com.mall.demo.ui.activity.GoodsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment implements MainContract.View {

    @BindView(R.id.ivCommonBack)
    AppCompatImageView ivCommonBack;
    @BindView(R.id.tvCommonTitle)
    AppCompatTextView tvCommonTitle;
    @BindView(R.id.rcHomeList)
    RecyclerView rcHomeList;
    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private MainPresenter loginPresenter;
    private HomeListAdapter homeAdapter;
    private final List<String> dataList = new ArrayList();

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        loginPresenter = new MainPresenter(new DataManager());
        loginPresenter.attachView(this);
    }

    @Override
    public void showCategoryList(List<String> dataList) {
        Toast.makeText(activity, dataList.get(0) + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        initStatusBar();
        ivCommonBack.setVisibility(View.GONE);
        tvCommonTitle.setText("商品");

        for (int i = 0; i < 10; i++) {
            dataList.add("商品" + i);
        }

        rcHomeList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeListAdapter(R.layout.item_home_list, dataList);
        View footView = getLayoutInflater().inflate(R.layout.common_footview, null);
        homeAdapter.addFooterView(footView);
        rcHomeList.setAdapter(homeAdapter);

    }

    private class HomeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public HomeListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, String name) {
            ImageView tvImg = holder.getView(R.id.tvImg);
            tvImg.setImageResource(R.drawable.img01);
            holder.setText(R.id.tvName, name);
            //holder.setText(R.id.tvHomeTime, searchBo.time);
            //holder.setText(R.id.tvHomeTitle, searchBo.title);
            //holder.setText(R.id.tvHomeContent, searchBo.content);
            //holder.setText(R.id.tvHomeCategory, "所属分类：" + searchBo.category);
            holder.itemView.setOnClickListener(v -> {
                GoodsActivity.launchActivity(activity, holder.getLayoutPosition() + "");
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

    @OnClick(R.id.layout_error)
    public void onReTry() {
        //setNetWorkError(true);
        //mPresenter.loadBanner();
        //mPresenter.loadArticle(0);
    }

    //private void setNetWorkError(boolean isSuccess) {
    //    if (isSuccess) {
    //        mNormalView.setVisibility(View.VISIBLE);
    //        mLayoutError.setVisibility(View.GONE);
    //    } else {
    //        mNormalView.setVisibility(View.GONE);
    //        mLayoutError.setVisibility(View.VISIBLE);
    //    }
    //}

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBo event) {
        if (event.target == EventBo.TARGET_HOME) {

        }
    }
}