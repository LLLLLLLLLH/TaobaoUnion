package com.example.taobaounion.ui.fragment;
import com.example.taobaounion.base.BaseApplication;
import com.example.taobaounion.ui.activity.ScanQrCodeActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.presenter.IHomePresenter;
import com.example.taobaounion.ui.activity.SearchActivity;
import com.example.taobaounion.ui.adapter.HomePagerAdapter;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.view.IHomeCallBack;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallBack {

    private IHomePresenter mHomePresenter;

    @BindView(R.id.home_indicator)
    public TabLayout mTabLayout;

    @BindView(R.id.home_pager)
    public ViewPager mViewPager;

    @BindView(R.id.home_scan_code)
    public View mScanBtn;
    private HomePagerAdapter mHomePagerAdapter;

    @BindView(R.id.base_home_fragment_layout_search_edit)
    public EditText Search;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {

        mTabLayout.setupWithViewPager(mViewPager);
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mHomePagerAdapter);
    }

    //创建presenter
    @Override
    protected void initPresenter() {
        mHomePresenter = PresenterManager.getInstance().getHomePresenter();
        mHomePresenter.registerViewCallBack(this);
    }

    //加载数据
    @Override
    protected void loadDate() {
        mHomePresenter.getCategories();
    }


    @Override
    protected View getRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout,container,false);
    }

    //加载服务器传来的数据
    @Override
    public void onCategoriesLoad(Categories categories) {
        setState(STATE.SUCCESS);
        if (mHomePagerAdapter != null) {
            mHomePagerAdapter.setCategories(categories);
        }
    }

    @Override
    public void onError() {
        setState(STATE.ERROR);
    }

    @Override
    public void onLoading() {setState(STATE.LOADING);}

    @Override
    public void onEmpty() {
        setState(STATE.EMPTY);
    }

    //关闭连接
    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unregisterViewCallBack(this);
        }
    }

    //点击页面重新加载
    @Override
    protected void onRetryClick() {
        if (mHomePresenter != null) {
            mHomePresenter.getCategories();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        Search.setOnClickListener(v -> startActivity(new Intent(getContext(), SearchActivity.class)));
        mScanBtn.setOnClickListener(v -> {
            startActivity(new Intent(BaseApplication.getAppContext(), ScanQrCodeActivity.class));
        });
    }
}
