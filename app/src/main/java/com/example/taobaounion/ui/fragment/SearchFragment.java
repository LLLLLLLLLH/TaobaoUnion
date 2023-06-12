package com.example.taobaounion.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.Histories;
import com.example.taobaounion.model.bean.SearchRecommend;
import com.example.taobaounion.model.bean.SearchResult;
import com.example.taobaounion.presenter.ISearchPresenter;
import com.example.taobaounion.ui.activity.SearchActivity;
import com.example.taobaounion.ui.adapter.HomePagerContentAdapter;
import com.example.taobaounion.ui.custom.TextFlowLayout;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.view.ISearchCallBack;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchFragment extends BaseFragment implements ISearchCallBack {

    @BindView(R.id.fragment_search_flow_histories)
    public TextFlowLayout mFlowHistories;
    @BindView(R.id.fragment_search_flow_recommendation)
    public TextFlowLayout mFlowRecommendation;
    @BindView(R.id.fragment_search_histories_container)
    public View mHistoriesContainer;
    @BindView(R.id.fragment_search_recommendation_container)
    public View mRecommendationContainer;
    @BindView(R.id.fragment_search_del_histories)
    public View mDelete;
    @BindView(R.id.fragment_search_result)
    public RecyclerView mSearchResult;
    @BindView(R.id.fragment_search_refresh)
    public SmartRefreshLayout mRefreshLayout;
    public ISearchPresenter mSearchPresenter;
    private HomePagerContentAdapter mSearchResultAdapter;
    private SearchActivity mSearchActivity;
    private View mRemove;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mSearchPresenter = PresenterManager.getInstance().getSearchPresenter();
        mSearchPresenter.registerViewCallBack(this);
        mSearchPresenter.getRecommendation();
        mSearchPresenter.getSearchHistory();
    }

    @Override
    protected void loadDate() {
    }

    @Override
    protected void initView(View rootView) {
        mSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchResultAdapter = new HomePagerContentAdapter();
        mSearchResult.setAdapter(mSearchResultAdapter);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(requireContext()).setNormalColor(Color.RED));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(requireContext()));

        mSearchResult.addItemDecoration(new RecyclerView.ItemDecoration() {
            private final int mDividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
            private final Paint mDividerPaint = new Paint();

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int childCount = parent.getChildCount();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                mDividerPaint.setColor(ContextCompat.getColor(requireContext(), R.color.divisionLineColor));

                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);

                    int top = child.getBottom();
                    int bottom = top + mDividerHeight;

                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }
            }
        });
        setState(STATE.SUCCESS);
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
    public void loadHistory(Histories histories) {
        if (histories == null || histories.getHistories().size() == 0) {
            mHistoriesContainer.setVisibility(View.GONE);
        } else {
            mHistoriesContainer.setVisibility(View.VISIBLE);
            mFlowHistories.setTextList(histories.getHistories());
        }
    }

    @Override
    public void delHistory() {
        if (mSearchPresenter != null) {
            mSearchPresenter.getSearchHistory();
        }
    }

    @Override
    public void searchResult(SearchResult result) {
        setState(STATE.SUCCESS);
        //显示结果界面
        mRecommendationContainer.setVisibility(View.GONE);
        mHistoriesContainer.setVisibility(View.GONE);
        mSearchResult.setVisibility(View.VISIBLE);
        List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> data = result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data();
        mSearchResultAdapter.setDate(data);


    }

    @Override
    public void MoreLoaded(SearchResult result) {
        mRefreshLayout.finishLoadMore();
        List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> data = result.getData().
                getTbk_dg_material_optional_response().
                getResult_list().
                getMap_data();
        mSearchResultAdapter.addData(data);
    }

    @Override
    public void MoreLoadedError() {
        mRefreshLayout.finishLoadMore();
        setState(STATE.ERROR);
    }

    @Override
    public void MoreLoadedEmpty() {
        mRefreshLayout.finishLoadMore();
        setState(STATE.EMPTY);
    }

    @Override
    public void getRecommendationWords(List<SearchRecommend.DataBean> words) {
        List<String> data = new ArrayList<>();
        for (SearchRecommend.DataBean word : words) {
            data.add(word.getKeyword());
        }
        if (words == null || words.size() == 0) {
            mRecommendationContainer.setVisibility(View.GONE);
        } else {
            mFlowRecommendation.setTextList(data);
            mRecommendationContainer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
        mSearchActivity = (SearchActivity) getActivity();
        //点击删除历史搜索记录
        mDelete.setOnClickListener(v -> {
            mSearchPresenter.deleteSearchHistory();
        });
        //历史搜索点击
        mFlowHistories.setOnFlowTextItemClickListener(text -> {
            LogUtils.d(this,"histories--------->"+text);
            mSearchPresenter.doSearch(text);
            requireWord(text);
        });
        //跳转到领劵页面
        mSearchResultAdapter.setOnListItemClickListener(bean -> {
            TicketUtil.toTicketPage(requireContext(), bean);
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (mSearchPresenter != null) {
                mSearchPresenter.LoadMore();
            }
        });
        //推荐搜索点击
        mFlowRecommendation.setOnFlowTextItemClickListener(text -> {
            mSearchPresenter.doSearch(text);
            requireWord(text);
        });
    }

    private void requireWord(String text) {
        if (mSearchActivity != null) {
            EditText searchWord = mSearchActivity.findViewById(R.id.activity_search_edit);
            searchWord.setFocusable(true);
            searchWord.setText(text);
            searchWord.requestFocus();
            searchWord.setSelection(text.length());

        }
    }

    @Override
    protected void release() {
        super.release();
    }

    @Override
    public void retry() {
        mSearchPresenter.reSearch();
    }


    public void setSearchWord(String word){
        //接受要搜索的值
        mSearchPresenter.doSearch(word);
        mSearchResult.scrollToPosition(0);
    }

    //点击了清楚按钮显示的界面设置
    public void clearResult()
    {
        mSearchResult.setVisibility(View.GONE);
        mHistoriesContainer.setVisibility(View.VISIBLE);
        mRecommendationContainer.setVisibility(View.VISIBLE);
    }
}
