package com.example.taobaounion.ui.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.DiscountsContent;
import com.example.taobaounion.utils.UrlUtils;
import com.itheima.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.ViewHolder> {

    public List<DiscountsContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mData = new ArrayList<>();
    private itemClickListener mItemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discounts_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mData.get(position));
        holder.itemView.setOnClickListener(v -> {
            mItemClickListener.itemClick(mData.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(DiscountsContent data) {
        if (data != null) {
            mData.clear();
            mData.addAll(getBeans(data));
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(itemClickListener listener)
    {
        mItemClickListener = listener;
    }

    public void addData(DiscountsContent data) {
        int oldSize = mData.size();
        mData.addAll(getBeans(data));
        notifyItemChanged(oldSize,getBeans(data).size());
    }

    private List<DiscountsContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> getBeans(DiscountsContent data) {
        List<DiscountsContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> beans = data.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        return beans;
    }

    public interface itemClickListener{
        void itemClick(DiscountsContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_discounts_image)
        public RoundedImageView mImageView;

        @BindView(R.id.item_discounts_title)
        public TextView mTitle;

        @BindView(R.id.item_discounts_oldPrice)
        public TextView mOldPrice;

        @BindView(R.id.item_discounts_nowPrice)
        public TextView mNowPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(DiscountsContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean mapDataBean) {

            //获取图片url
            String pict_url = mapDataBean.getPict_url();
            String coverPath = UrlUtils.getCoverPath(pict_url);
            Glide.with(itemView.getContext()).load(coverPath).into(mImageView);

            //原价
            float old = Float.parseFloat(mapDataBean.getZk_final_price());
            //卷值
            int amount = mapDataBean.getCoupon_amount();
            //卷后价
            float now = old - amount;

            //商品描述
            mTitle.setText(mapDataBean.getTitle());

            //原价
            mOldPrice.setText(String.format("￥"+itemView.getContext().getString(R.string.text_aftermarketPrice), old));
            mOldPrice.setPaintFlags(mOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            //卷后价
            mNowPrice.setText(String.format(itemView.getContext().getString(R.string.text_nowPrice), now));


        }
    }
}
