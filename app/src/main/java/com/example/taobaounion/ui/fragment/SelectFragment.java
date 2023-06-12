package com.example.taobaounion.ui.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.presenter.ISelectPagePresenter;
import com.example.taobaounion.ui.adapter.SelectContentAdapter;
import com.example.taobaounion.ui.adapter.SelectItemAdapter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.view.ISelectPageCallBack;

import java.util.List;

import butterknife.BindView;

public class SelectFragment extends BaseFragment implements ISelectPageCallBack {

    private ISelectPagePresenter mSelectPagePresenter;

    @BindView(R.id.select_fragment_classification)
    public RecyclerView mSelectItem;

    @BindView(R.id.select_fragment_selectedContent)
    public RecyclerView mSelectContent;




    private SelectItemAdapter mItemAdapter;
    private SelectContentAdapter mSelectContentAdapter;
    private int CategoryId = 0;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mSelectPagePresenter = PresenterManager.getInstance().getSelectPagePresenter();
        mSelectPagePresenter.registerViewCallBack(this);
    }

    @Override
    protected void release() {
        super.release();
        if (mSelectPagePresenter != null) {
            mSelectPagePresenter.unregisterViewCallBack(this);
        }
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_select;
    }

    @Override
    protected void initView(View rootView) {
        setState(STATE.SUCCESS);
        mItemAdapter = new SelectItemAdapter();
        mSelectContentAdapter = new SelectContentAdapter();

        mSelectContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mSelectContent.setAdapter(mSelectContentAdapter);

        mSelectItem.setLayoutManager(new LinearLayoutManager(getContext()));
        mSelectItem.setAdapter(mItemAdapter);

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
    public int getCategoryId() {
        return CategoryId;
    }

    @Override
    public void onCategoriesLoaded(Categories categories) {
        setState(STATE.SUCCESS);
        //精选分类
        mItemAdapter.setDate(categories);
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> content) {
        //精选内容
        LogUtils.d(this, "content ------>" + content.size());
        setState(STATE.SUCCESS);
        mSelectContentAdapter.setDate(content);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mItemAdapter.setItemClickListener(item -> {
            CategoryId = item.getId();
            mSelectPagePresenter.getContentCategories(getCategoryId());
        });

        mSelectContentAdapter.setItemClickListener(item -> {
            TicketUtil.toTicketPage(getContext(),item);
        });
    }

    @Override
    protected void loadDate() {
        super.loadDate();
        mSelectPagePresenter.getContentCategories(getCategoryId());
        mSelectPagePresenter.getCategories();
    }


    @Override
    public void retry() {
        super.retry();
        if (mSelectPagePresenter != null) {
            mSelectPagePresenter.reloadContent();
        }
    }
}
