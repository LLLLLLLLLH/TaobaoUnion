package com.example.taobaounion.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.utils.UrlUtils;
import com.itheima.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectContentAdapter extends RecyclerView.Adapter<SelectContentAdapter.ViewHolder> {


    private List<HomePagerContent.DataBean> mData = new ArrayList<>();
    private ItemClickListener mClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_select_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.LoadDate(mData.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.itemClick(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setDate(List<HomePagerContent.DataBean> content) {
        if (content != null) {
            mData.clear();
            mData.addAll(content);
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        mClickListener = listener;
    }


    public interface ItemClickListener{
        void itemClick(IBaseInfo item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.select_content_image)
        public RoundedImageView contentImage;

        @BindView(R.id.select_content_tips)
        public TextView contentTips;

        @BindView(R.id.select_content_originalPrice)
        public TextView contentPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        private void LoadDate(HomePagerContent.DataBean bean) {
            Context context = itemView.getContext();

            String coverPath = UrlUtils.getCoverPath(bean.getPict_url());

            String title = bean.getTitle();

            String price = bean.getZk_final_price();

            Glide.with(context).load(coverPath).into(contentImage);
            contentTips.setText(title);
            contentPrice.setText(String.format(context.getString(R.string.text_oldPrice),price));
        }
    }
}
