package com.example.taobaounion.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.bean.ILinaerItemInfo;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.UrlUtils;
import com.itheima.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePagerContentAdapter extends RecyclerView.Adapter<HomePagerContentAdapter.viewHolder> {

    List<ILinaerItemInfo> mData = new ArrayList<>();

    private OnListItemClickListener mOnListItemClickListener;

    int i;

    @NonNull
    @Override
    public HomePagerContentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        i++;
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerContentAdapter.viewHolder holder, int position) {
        ILinaerItemInfo bean = mData.get(position);
        holder.setDate(bean);
        holder.itemView.setOnClickListener(v -> {
            if (mOnListItemClickListener != null) {
                mOnListItemClickListener.onItemClick(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setDate(List<? extends ILinaerItemInfo> contents) {
        mData.clear();
        isEmpty(contents);
        notifyDataSetChanged();
    }

    public void addData(List<? extends ILinaerItemInfo> contents) {
        //拿到添加前的size
        int oldSize = mData.size();
        isEmpty(contents);
        notifyItemChanged(oldSize, mData.size());
    }

    private void isEmpty(List<? extends ILinaerItemInfo> contents) {
        for (ILinaerItemInfo content : contents) {
            if (content.getHasCoupon())
                mData.add(content);
        }
    }

    public boolean isBottom() {
        return i == getItemCount();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        public static Map<Integer, String> USER_TYPE_MAP;

        static {
            USER_TYPE_MAP = new HashMap<>();
            USER_TYPE_MAP.put(0, "淘宝");
            USER_TYPE_MAP.put(1, "天猫");
            USER_TYPE_MAP.put(2, "特价");
        }


        //图片
        @BindView(R.id.item_home_goods_cover)
        public RoundedImageView mImageView;

        //标题
        @BindView(R.id.item_home_pager_good_title)
        public TextView title;

        //劵后价
        @BindView(R.id.item_home_pager_aftermarketPrice)
        public TextView aftermarketPrice;

        //卖家类型
        @BindView(R.id.item_home_pager_user_type)
        public TextView userType;

        //劵值
        @BindView(R.id.item_home_pager_securities)
        public TextView securities;

        //原价
        @BindView(R.id.item_home_pager_originalPrice)
        public TextView originalPrice;

        //已售出
        @BindView(R.id.item_home_pager_volume)
        public TextView volume;
        private float Zk_final_price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setDate(ILinaerItemInfo dataBean) {
            //原价
            Zk_final_price = Float.parseFloat(dataBean.getFinalPrice());
            //劵值
            long coupon_amount = Long.parseLong(dataBean.getCouponAmount());
            //劵后价
            float resultPrise = Zk_final_price - coupon_amount;
            //卖家类型
            int type = dataBean.getType();

            long sold = dataBean.getVolume();
            String formattedVolume = formatVolume(sold);
            Context context = itemView.getContext();

            //动态设置图片大小
/*            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
            int height = params.height;
            int width = params.width;
            int size = Math.max(height, width) / 2;*/
            String coverPath = UrlUtils.getCoverPath(dataBean.getCover());


            LogUtils.d(this, coverPath);

            title.setText(dataBean.getTitle());
            mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(context).load(coverPath).into(mImageView);
            securities.setText(String.format(context.getString(R.string.text_securities),coupon_amount));
            aftermarketPrice.setText(String.format(context.getString(R.string.text_aftermarketPrice),resultPrise));
            originalPrice.setText(String.format(context.getString(R.string.text_aftermarketPrice), Zk_final_price));
            originalPrice.setPaintFlags(originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            userType.setText(USER_TYPE_MAP.get(type));
            volume.setText(String.format(context.getString(R.string.text_volume), formattedVolume));


        }

        private String formatVolume(long volume) {
            if (volume < 1000) {
                return String.valueOf(volume);
            } else if (volume < 10000) {
                volume = volume / 1000;
                return volume + "千";
            } else {
                volume = volume / 10000;
                return volume + "万";
            }
        }

    }

    public void setOnListItemClickListener(OnListItemClickListener listener) {
        mOnListItemClickListener = listener;
    }

    public interface OnListItemClickListener {
        void onItemClick(IBaseInfo bean);
    }
}