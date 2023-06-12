package com.example.taobaounion.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {

    private List<HomePagerContent.DataBean> mData = new ArrayList<>();
    private OnLoopItemClickListener mOnLoopItemClickListener = null;

    public void setData(List<HomePagerContent.DataBean> contents) {
        LogUtils.d(this, "---->" + contents.size());
        mData.clear();
        mData.addAll(contents);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        int realPosition = position % mData.size();
        HomePagerContent.DataBean dataBean = mData.get(realPosition);
        Context context = container.getContext();
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        int height = container.getMeasuredHeight();
        int width = container.getMeasuredWidth();
        int size = Math.max(height, width) / 2;
        String coverPath = UrlUtils.getCoverPath(dataBean.getPict_url(), size);
        Glide.with(context).load(coverPath).into(imageView);

        container.addView(imageView);

        imageView.setOnClickListener(v -> {
            if (mOnLoopItemClickListener != null) {
                HomePagerContent.DataBean bean = mData.get(realPosition);
                mOnLoopItemClickListener.onLoopItemClick(bean);
            }
        });


        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public int getDataSize() {
        return mData.size();
    }

    public void setOnLoopItemClickListener(OnLoopItemClickListener listener) {
        mOnLoopItemClickListener = listener;
    }


    public interface OnLoopItemClickListener {
        void onLoopItemClick(IBaseInfo bean);
    }
}
