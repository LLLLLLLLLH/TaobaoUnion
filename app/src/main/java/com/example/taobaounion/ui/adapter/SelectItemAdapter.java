package com.example.taobaounion.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.Categories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.ViewHolder> {

    private List<Categories.DataBean> date = new ArrayList<>();

    private int oldPosition = 0;
    private ItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_classification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (holder.getAdapterPosition() == oldPosition) {
            holder.classification.setBackgroundColor(Color.parseColor("#EFEEEE"));
        } else {
            holder.classification.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.classification.setText(date.get(position).getTitle());
        holder.classification.setOnClickListener(v -> {
            if (mItemClickListener != null && oldPosition != position) {
                oldPosition = holder.getAdapterPosition();
                mItemClickListener.ItemClick(date.get(oldPosition));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public void setDate(Categories categories) {
        List<Categories.DataBean> item = categories.getData();
        if (item != null) {
            date.clear();
            date.addAll(item);
            notifyDataSetChanged();
        }
        if (date.size() > 0)
            mItemClickListener.ItemClick(date.get(oldPosition));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.select_classification_tv)
        public TextView classification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface ItemClickListener {
        void ItemClick(Categories.DataBean item);
    }
}
