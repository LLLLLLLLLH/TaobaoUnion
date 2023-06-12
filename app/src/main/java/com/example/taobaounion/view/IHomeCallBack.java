package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallBack;
import com.example.taobaounion.model.bean.Categories;

public interface IHomeCallBack extends IBaseCallBack {
    void onCategoriesLoad(Categories categories);
}
