package com.example.taobaounion.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.ui.adapter.HomePagerContentAdapter;
import com.example.taobaounion.ui.adapter.LooperPagerAdapter;
import com.example.taobaounion.ui.custom.AutoLooperViewPager;
import com.example.taobaounion.ui.custom.TbNestedScrollView;
import com.example.taobaounion.utils.Constants;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.SizeUtils;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.view.ICategoryPagerCallBack;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallBack, HomePagerContentAdapter.OnListItemClickListener, LooperPagerAdapter.OnLoopItemClickListener {


    static {
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
        ClassicsHeader.REFRESH_HEADER_SECONDARY = "释放进入二楼";
    }

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int mId;

    @BindView(R.id.looper_pager)
    public AutoLooperViewPager mViewPager;

    @BindView(R.id.include_home_pager_title)
    public TextView categoriesTitle;

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mRecyclerView;

    @BindView(R.id.home_pager_refresh)
    public SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.looper_point_container)
    public LinearLayout looperPointContainer;

    @BindView(R.id.home_pager_container)
    public LinearLayout home_pager;

    @BindView(R.id.home_pager_nested_scroll)
    public TbNestedScrollView mNestedScrollView;


    @BindView(R.id.home_pager_header_container)
    public LinearLayout headerContainer;

    private HomePagerContentAdapter mHomePagerContentAdapter;
    private LooperPagerAdapter mLooperPagerAdapter;

    public static HomePagerFragment newInstance(Categories.DataBean category) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_ID, category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        mViewPager.startLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPager.stopLoop();
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        setState(STATE.SUCCESS);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomePagerContentAdapter = new HomePagerContentAdapter();
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        mRecyclerView.setAdapter(mHomePagerContentAdapter);
        //轮播图设置
        mLooperPagerAdapter = new LooperPagerAdapter();
        mViewPager.setAdapter(mLooperPagerAdapter);
        mViewPager.setDuration(2000);
        //设置refresh属性
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setNormalColor(Color.RED));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));

    }

    @Override
    protected void loadDate() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mId = arguments.getInt(Constants.KEY_HOME_PAGER_ID);
            if (categoriesTitle != null) {
                categoriesTitle.setText(arguments.getString(Constants.KEY_HOME_PAGER_TITLE));
            }
        }
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.getContentByCategoryId(mId);
        }

    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = PresenterManager.getInstance().getCategoryPagerPresenter();
        mCategoryPagerPresenter.registerViewCallBack(this);
    }


    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents) {
        mHomePagerContentAdapter.setDate(contents);
        setState(STATE.SUCCESS);
    }

    @Override
    public int getCategoryId() {
        return mId;
    }

    @Override
    public void onLoading() {
        setState(STATE.LOADING);
    }

    @Override
    public void onError() {
        setState(STATE.ERROR);
    }

    @Override
    public void onEmpty() {
        setState(STATE.EMPTY);
    }

    @Override
    public void onLoadMoreError() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents) {
        mHomePagerContentAdapter.addData(contents);
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadMore();
        }
    }

    //加载轮播图数据
    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        mLooperPagerAdapter.setData(contents);
        looperPointContainer.removeAllViews();
        mViewPager.setCurrentItem(contents.size() * 100000);
        for (int i = 0; i < contents.size(); i++) {
            createPointView(i);
        }
    }

    private void createPointView(int i) {
        View point = new View(getContext());
        int size = SizeUtils.dip2px(getContext(), 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        int rightMargin = SizeUtils.dip2px(getContext(), 5);
        int leftMargin = SizeUtils.dip2px(getContext(), 5);
        params.setMargins(leftMargin, 0, rightMargin, 0);
        point.setLayoutParams(params);
        if (i == 0)
            point.setBackgroundResource(R.drawable.shape_indicator_point_select);
        else
            point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
        looperPointContainer.addView(point);
    }

    @Override
    protected void initListener() {
        mHomePagerContentAdapter.setOnListItemClickListener(this);
        mLooperPagerAdapter.setOnLoopItemClickListener(this);
        home_pager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (headerContainer == null)
                    return;
                int headerHeight = headerContainer.getMeasuredHeight();
                mNestedScrollView.setHeaderHeight(headerHeight);
                int measuredHeight = home_pager.getMeasuredHeight();
                ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
                params.height = measuredHeight;
                mRecyclerView.setLayoutParams(params);
                if (measuredHeight != 0) {
                    home_pager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int dataSize = mLooperPagerAdapter.getDataSize();
                if (dataSize == 0)
                    return;
                int targetPosition = position % dataSize;
                updateLooperIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            ToastUtils.showToast("已执行");
            boolean bottom = mHomePagerContentAdapter.isBottom();
            if (mCategoryPagerPresenter != null && !bottom) {
                mCategoryPagerPresenter.loadMore(mId);
            }
        });

        mRefreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.finishRefresh());
    }

    private void updateLooperIndicator(int targetPosition) {
        for (int i = 0; i < looperPointContainer.getChildCount(); i++) {
            View point = looperPointContainer.getChildAt(i);
            if (i == targetPosition)
                point.setBackgroundResource(R.drawable.shape_indicator_point_select);
            else
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
        }
    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter != null) {
            mCategoryPagerPresenter.unregisterViewCallBack(this);
        }
    }


    @Override
    public void retry() {
        super.retry();
        loadDate();
    }

    @Override
    public void onItemClick(IBaseInfo bean) {
        itemClick(bean);
    }

    @Override
    public void onLoopItemClick(IBaseInfo bean) {
        itemClick(bean);
    }

    private void itemClick(IBaseInfo bean)
    {
        TicketUtil.toTicketPage(getContext(),bean);
    }
}
