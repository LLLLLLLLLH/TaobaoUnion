package com.example.taobaounion.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taobaounion.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextFlowLayout extends ViewGroup {

    public static final float DEFAULT_SPACE = 10;
    //水平间距
    private float mItemHorizontalSpace = DEFAULT_SPACE;
    //垂直间距
    private float mItemVerticalSpace = DEFAULT_SPACE;

    private Set<String> mTextList = new HashSet<>();
    private int mSelfWidth;
    private int mItemHeight;
    private OnFlowTextItemClickListener mItemClickListener = null;

    public int getContentSize() {
        return mTextList.size();
    }

    public float getItemHorizontalSpace() {
        return mItemHorizontalSpace;
    }

    public void setItemHorizontalSpace(float itemHorizontalSpace) {
        mItemHorizontalSpace = itemHorizontalSpace;
    }

    public float getItemVerticalSpace() {
        return mItemVerticalSpace;
    }

    public void setItemVerticalSpace(float itemVerticalSpace) {
        mItemVerticalSpace = itemVerticalSpace;
    }

    public TextFlowLayout(Context context) {

        this(context, null);
        //LogUtils.d(this,"TextFlowLayout(Context context)");
    }

    //调用的是第二个构造方法可能因为只是自定义属性
    public TextFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //LogUtils.d(this,"TextFlowLayout(Context context, AttributeSet attrs)");
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //LogUtils.d(this,"TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr)");
        //去拿到相关属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowTextStyle);
        //ta.getDimension第一个参数会先去检索布局里有没有这个属性，如果没有就返回第二个参数默认值
        mItemHorizontalSpace = ta.getDimension(R.styleable.FlowTextStyle_horizontalSpace, DEFAULT_SPACE);
        mItemVerticalSpace = ta.getDimension(R.styleable.FlowTextStyle_verticalSpace, DEFAULT_SPACE);
        //释放资源
        ta.recycle();
        //LogUtils.d(this,"mItemHorizontalSpace--->"+mItemHorizontalSpace);//是px
        //LogUtils.d(this,"mItemVerticalSpace--->"+mItemVerticalSpace);
    }


    public void setTextList(List<String> textList) {
        removeAllViews();
        mTextList.clear();
        mTextList.addAll(textList);
        //遍历内容
        for (String text : mTextList) {
            //添加子view(将TextFlowLayout布局增加R.layout.flow_text_view的view)
            //LayoutInflater.inflate(getContext()).inflate(R.layout.flow_text_view,this,true);
            //等价于
            TextView item = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view, this, false);
            item.setText(text);
            item.setOnClickListener(v -> {
                if (mItemClickListener != null) {
                    mItemClickListener.onFlowItemClick(text);
                }
            });
            addView(item);//以this当前view作为root根view，向其添加多个item
        }
    }


    //这个是描述所有的行
    private List<List<View>> lines = new ArrayList<>();


    /**
     * 判断当前行是否可以再继续添加新数据
     *
     * @param itemView
     * @param line
     */
    private boolean canBeAdd(View itemView, List<View> line) {
        //所有已经添加的子view宽度相加+(line.size()+1)*mItemHorizontalSpace+itemView.getMeasureWidth()
        //条件，如果小于/等于当前控件的宽度，则可以添加，否则不能添加
        int totalWith = itemView.getMeasuredWidth();
        for (View view : line) {
            //叠加所以已经添加控件的宽度
            totalWith += view.getMeasuredWidth();
        }
        //水平间距的宽度
        totalWith += mItemHorizontalSpace * (line.size() + 1);
        //LogUtils.d(this,"total -->"+totalWith);
        //如果小于/等于当前控件的宽度，则可以添加，否则不能添加
        return totalWith <= mSelfWidth;
    }


    //测量：设置子view大小和自己的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //LogUtils.d(this,"getChildCount()-->"+getChildCount());
        if (getChildCount() == 0) {//因为我们后面的推荐词是通过网络请求来的，可能来不及加进来就执行onMeasure了，会报空指针，所以判断一下
            return;
        }
        //因为会多次调用onMeasure,所以要对其清空，
        lines.clear();
        //这个是描述单行
        List<View> line = null;
        //MeasureSpec.getSize(widthMeasureSpec)取出值,打印出来应该是屏幕高度因为设置为machParent
        mSelfWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();//如果有PaddingLeft和PaddingRight的话，后面要判断能否继续添加的话要减去
        //LogUtils.d(this,"mSelfWidth-->"+mSelfWidth);
        //测量
        //LogUtils.d(this,"onMeasure -->"+getChildCount());
        //测量孩子
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //拿到每个子view(上面增加的TextView)
            View itemView = getChildAt(i);
            if (itemView.getVisibility() != VISIBLE) {
                continue;
            }
            //测量前
            //LogUtils.d(this,"before height -->"+itemView.getMeasuredHeight()+"before width -->"+itemView.getMeasuredWidth());
            //通过源码measureChild最后也是itemView执行onMeasure,所以会多次执行onMeasure方法
            measureChild(itemView, widthMeasureSpec, heightMeasureSpec);//因为子类View为宽高都为包裹内容，所以不用管之前传widthMeasureSpec和heightMeasureSpec
            //测量后
            //LogUtils.d(this,"after height -->"+itemView.getMeasuredHeight()+"after width -->"+itemView.getMeasuredWidth());//通过打印输出可知测量后的高度是94
            if (line == null) {
                //说明当前行为空，可以添加进来
                line = CreateNewLine(itemView);
            } else {
                //判断是否可以再继续添加
                if (canBeAdd(itemView, line)) {
                    //可以添加，添加去
                    line.add(itemView);
                } else {
                    //新创建一行
                    line = CreateNewLine(itemView);
                }
            }
        }
        mItemHeight = getChildAt(0).getMeasuredHeight();
        //自己的高度（也就是所有子itemView合在一起自己的高度）
        int selfHeight = (int) (lines.size() * mItemHeight + mItemVerticalSpace * (lines.size() + 1) + 0.5f);
        //LogUtils.d(this,"mSelfWidth-->"+mSelfWidth+"selfHeight-->"+selfHeight);多次执行测量measure方法结果都一样是mSelfWidth-->1440selfHeight-->362
        //测量自己(测出大概的布局)
        setMeasuredDimension(mSelfWidth, selfHeight);//也就是设置自己的测量大小
    }

    private List<View> CreateNewLine(View itemView) {
        List<View> line = new ArrayList<>();
        line.add(itemView);
        lines.add(line);
        return line;
    }


    /**
     * @param l Left position, relative to parent
     *          * @param t Top position, relative to parent
     *          * @param r Right position, relative to parent
     *          * @param b Bottom position, relative to parent
     **/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放孩子
        //LogUtils.d(this,"onLayout -->"+getChildCount());
//        View itemOne = getChildAt(0);
//        itemOne.layout(0,0,itemOne.getMeasuredWidth(),itemOne.getMeasuredHeight());
//        View itemTwo = getChildAt(1);
//        itemTwo.layout(itemOne.getRight()+(int) mItemHorizontalSpace,
//                0,
//                itemOne.getRight()+(int) mItemHorizontalSpace+itemTwo.getMeasuredWidth(),
//                itemTwo.getMeasuredHeight());
        int topOffset = (int) mItemVerticalSpace;
        //这个时候摆放就可以根据我们之前测量好的行数进行摆放
        for (List<View> views : lines) {
            //views是每一行
            int leftOffSet = (int) mItemHorizontalSpace;
            for (View view : views) {
                //每一行的每个item
                view.layout(leftOffSet, topOffset, leftOffSet + view.getMeasuredWidth(), topOffset + view.getMeasuredHeight());
                //
                leftOffSet += view.getMeasuredWidth() + mItemHorizontalSpace;
            }
            topOffset += mItemHeight + mItemHorizontalSpace;
        }
    }

    public void setOnFlowTextItemClickListener(OnFlowTextItemClickListener listener) {

        this.mItemClickListener = listener;
    }

    public interface OnFlowTextItemClickListener {
        void onFlowItemClick(String text);
    }


}