package com.mall.demo.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.mall.demo.base.utils.Constant;
import com.mall.demo.custom.loading.LoadingView;
import com.mall.demo.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mall.demo.base.utils.Utils;
import com.mall.demo.bean.EventBo;
import com.mall.demo.ui.fragment.HomeFragment;
import com.mall.demo.ui.fragment.MessageFragment;
import com.mall.demo.ui.fragment.MineFragment;
import com.mall.demo.ui.fragment.OrderFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    Unbinder mBinder;
    @BindView(R.id.navigation_bottom)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    private boolean isLoad = false;
    private boolean isLoadMsg = false;
    private static final int INDEX_HOMEPAGE = 0;
    private static final int INDEX_PROJECT = 1;
    private static final int INDEX_MESSAGE = 2;
    private static final int INDEX_SQUARE = 3;
    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();
    private Fragment mCurrentFragment;
    private Fragment mLastFragment;
    private int mLastIndex = -1;
    private long mExitTime = 0;
    private Context mContext;
    private boolean isCurFragment = false;

    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index");
        switchFragment(mLastIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 解决Fragment需要一个构造函数的问题
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mContext = getApplicationContext();
        mBinder = ButterKnife.bind(this);
        initBottomNavigation();
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            switchFragment(INDEX_HOMEPAGE);
        }
        mBottomNavigationView.setItemIconTintList(Utils.getColorStateList(mContext));
        mBottomNavigationView.setItemTextColor(Utils.getColorStateList(mContext));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            switchFragment(INDEX_HOMEPAGE);
                            return true;
                        case R.id.menu_project:
                            switchFragment(INDEX_PROJECT);
                            if (isLoad) {
                                OrderFragment fragment = (OrderFragment) getFragment(INDEX_PROJECT);
                                fragment.refreshInfo();
                            }
                            isLoad = true;
                            return true;
                        case R.id.menu_message:
                            switchFragment(INDEX_MESSAGE);
                            if (isLoadMsg) {
                                MessageFragment fragment = (MessageFragment) getFragment(INDEX_MESSAGE);
                                fragment.refreshInfo();
                            }
                            isLoadMsg = true;
                            return true;
                        case R.id.menu_square:
                            switchFragment(INDEX_SQUARE);
                            return true;
                        default:
                            return false;
                    }
                });
        showBadgeView(4, 6);
    }


    private void switchFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag("fragment" + index);
        mLastFragment = fragmentManager.findFragmentByTag("fragment" + mLastIndex);
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            } else {
                transaction.show(mCurrentFragment);
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            }
        }
        transaction.commit();
        mLastIndex = index;
    }

    private void switchFragment(int index, boolean isShow) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag("fragment" + index);
        mLastFragment = fragmentManager.findFragmentByTag("fragment" + mLastIndex);
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            mCurrentFragment = getFragment(index);
            transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            transaction.show(mCurrentFragment);
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index);
                transaction.add(R.id.container, mCurrentFragment, "fragment" + index);
            }
        }
        transaction.commit();
        mLastIndex = index;
    }

    private Fragment getFragment(int index) {
        Fragment fragment = mFragmentSparseArray.get(index);
        if (fragment == null) {
            switch (index) {
                case INDEX_HOMEPAGE:
                    fragment = HomeFragment.getInstance();
                    break;
                case INDEX_PROJECT:
                    fragment = OrderFragment.getInstance();
                    break;
                case INDEX_MESSAGE:
                    fragment = MessageFragment.getInstance();
                    break;
                case INDEX_SQUARE:
                    fragment = MineFragment.getInstance();
                    break;
                default:
                    break;
            }
            mFragmentSparseArray.put(index, fragment);
        }
        return fragment;
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - mExitTime > Constant.EXIT_TIME) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = curTime;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 设置显示的未读数字
     *
     * @param index      当前bottom index
     * @param showNumber 显示的未读数值
     */
    private void showBadgeView(int index, final int showNumber) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
        if (index < menuView.getChildCount()) {
            View view = menuView.getChildAt(index);
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            int iconWidth = icon.getWidth();
            int tabWidth = view.getWidth() / 2;
            int spaceWidth = tabWidth - iconWidth;
            final QBadgeView qBadgeView = new QBadgeView(this);

            qBadgeView.bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
            qBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    qBadgeView.clearAnimation();
                }
            });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBo event) {
        if (event.target == EventBo.TARGET_MAIN) {
            if (event.type == EventBo.TYPE_REFRESH_COLOR) {
                mBottomNavigationView.setItemIconTintList(Utils.getColorStateList(mContext));
                mBottomNavigationView.setItemTextColor(Utils.getColorStateList(mContext));
            } else if (event.type == EventBo.TYPE_CHANGE_DAY_NIGHT_MODE) {
                recreate();
            } else if (event.type == EventBo.TYPE_START_ANIMATION) {
                mLoadingView.setVisibility(View.VISIBLE);
                mLoadingView.startTranglesAnimation();
            } else if (event.type == EventBo.TYPE_STOP_ANIMATION) {
                mLoadingView.setVisibility(View.GONE);
            }
        }
    }
}
