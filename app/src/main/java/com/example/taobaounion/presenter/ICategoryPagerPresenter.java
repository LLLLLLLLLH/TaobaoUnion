package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ICategoryPagerCallBack;

public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallBack>{


    /*
    *  根据分类id去显示页面信息
    *
    * */
    void getContentByCategoryId(int categoryId);

    void loadMore(int categoryId);

    void reload(int categoryId);

}
