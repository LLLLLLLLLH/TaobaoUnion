package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallBack;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.model.bean.HomePagerContent;

import java.util.List;

public interface ISelectPageCallBack extends IBaseCallBack {
    void onCategoriesLoaded(Categories category);

    int getCategoryId();

    void onContentLoaded(List<HomePagerContent.DataBean> content);
}
