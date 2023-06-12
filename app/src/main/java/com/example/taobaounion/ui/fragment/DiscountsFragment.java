package com.example.taobaounion.ui.fragment;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.DiscountsContent;
import com.example.taobaounion.presenter.impl.DiscountsPresenterImpl;
import com.example.taobaounion.ui.adapter.DiscountsAdapter;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.view.IDiscountsCallBack;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class DiscountsFragment extends BaseFragment implements IDiscountsCallBack {

    private DiscountsPresenterImpl mDiscountsPresenter;

    @BindView(R.id.fragment_discounts_recycle)
    public RecyclerView mContentRv;

    @BindView(R.id.fragment_discounts_refresh)
    public SmartRefreshLayout mSmartRefreshLayout;
    private DiscountsAdapter mDiscountsAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_discounts;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mDiscountsPresenter = PresenterManager.getInstance().getDiscountsPresenter();
        mDiscountsPresenter.registerViewCallBack(this);
        mDiscountsPresenter.getDiscountContent();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setState(STATE.SUCCESS);
        mDiscountsAdapter = new DiscountsAdapter();
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mContentRv.setLayoutManager(mLayoutManager);
        mContentRv.setAdapter(mDiscountsAdapter);

        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setNormalColor(Color.RED));
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
    }

    @Override
    protected void release() {
        super.release();
        if (mDiscountsPresenter != null) {
            mDiscountsPresenter = null;
        }
    }

    @Override
    public void retry() {
        super.retry();
        mDiscountsPresenter.getDiscountContent();
    }

    @Override
    public void onError() {
        setState(STATE.ERROR);
    }

    @Override
    public void onLoading() {
        setState(STATE.LOADING);
    }

    @Override
    public void onEmpty() {
        setState(STATE.EMPTY);
    }

    @Override
    public void successfullyLoaded(DiscountsContent data) {
        setState(STATE.SUCCESS);
        if (data != null) {
            mDiscountsAdapter.setData(data);
        }
    }

    @Override
    public void onLoadMore(DiscountsContent data) {
        mDiscountsAdapter.addData(data);
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishLoadMore();
            ToastUtils.showToast("加载成功！！！");
        }
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.showToast("网络异常!!!");
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtils.showToast("没有更多数据了");
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDiscountsAdapter.setItemClickListener(item -> {
            TicketUtil.toTicketPage(getContext(),item);
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> mDiscountsPresenter.loadMore());
    }
}
