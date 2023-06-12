package com.example.taobaounion.ui.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.ui.fragment.DiscountsFragment;
import com.example.taobaounion.ui.fragment.HomeFragment;
import com.example.taobaounion.ui.fragment.MineFragment;
import com.example.taobaounion.ui.fragment.SelectFragment;
import com.example.taobaounion.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity{

    @BindView(R.id.main_navigation)
    protected BottomNavigationView NavigationView;
    private HomeFragment mHomeFragment;
    private SelectFragment mSelectFragment;
    private DiscountsFragment mDiscountsFragment;
    private MineFragment mMineFragment;
    private FragmentManager mFm;
    private FragmentTransaction mTransaction;


    @Override
    protected void initView() {
        initFragment();
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initEven() {
        super.initEven();
        initListener();
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mSelectFragment = new SelectFragment();
        mDiscountsFragment = new DiscountsFragment();
        mMineFragment = new MineFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);
    }

    private void initListener() {
        NavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu__home:
                    LogUtils.d(MainActivity.class, "首页");
                    switchFragment(mHomeFragment);
                    return true;
                case R.id.menu_select:
                    LogUtils.d(MainActivity.class, "精选");
                    switchFragment(mSelectFragment);
                    return true;
                case R.id.menu_discounts:
                    LogUtils.d(MainActivity.class, "特惠");
                    switchFragment(mDiscountsFragment);
                    return true;
                case R.id.menu_search:
                    LogUtils.d(MainActivity.class, "搜索");
                    switchFragment(mMineFragment);
                    return true;
            }
            return false;
        });
    }

    //上一次显示的fragment
    BaseFragment lastOneFragment = null;

    private void switchFragment(BaseFragment fragment) {
        //每次切换一次fragment都需要开启一次开启事务和提交事务，不然会报错
        //开启事务
        //修改成add和hide的方式来控制Fragment的切换（避免每次切换都要重新加载界面）
        mTransaction = mFm.beginTransaction();
        if(!fragment.isAdded()){
            mTransaction.add(R.id.main_page_container,fragment);//只增加的话如果不隐藏的话所有fragment会出现在同一个界面
        }else {
            mTransaction.show(fragment);
        }
        if(lastOneFragment!=null&&lastOneFragment!=fragment){//避免重复点击一个导航栏会进行隐藏
            mTransaction.hide(lastOneFragment);
        }
        lastOneFragment=fragment;

        //切换fragment
        //mTransaction.replace(R.id.main_page_container,fragment);
        //提交事务(不提交无法看到Fragment)
        mTransaction.commit();
    }

}