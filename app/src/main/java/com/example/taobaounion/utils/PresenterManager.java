package com.example.taobaounion.utils;

import com.example.taobaounion.presenter.impl.CategoryPagerPresenterImpl;
import com.example.taobaounion.presenter.impl.DiscountsPresenterImpl;
import com.example.taobaounion.presenter.impl.HomePresenterImpl;
import com.example.taobaounion.presenter.impl.LoginPresenterImpl;
import com.example.taobaounion.presenter.impl.RegainPresenterImpl;
import com.example.taobaounion.presenter.impl.RegisterPresenterImpl;
import com.example.taobaounion.presenter.impl.SearchPresenterImpl;
import com.example.taobaounion.presenter.impl.SelectPagePresenterImpl;
import com.example.taobaounion.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager instance = new PresenterManager();
    private final CategoryPagerPresenterImpl mCategoryPagerPresenter;
    private final HomePresenterImpl mHomePresenter;
    private final TicketPresenterImpl mTicketPresenter;
    private final SelectPagePresenterImpl mSelectPagePresenter;

    private final DiscountsPresenterImpl mDiscountsPresenter;
    private final SearchPresenterImpl mSearchPresenter;

    private final LoginPresenterImpl mLoginPresenter;

    private final RegisterPresenterImpl mRegisterPresenter;

    private final RegainPresenterImpl mRegainPresenter;

    public static PresenterManager getInstance() {
        return instance;
    }

    private PresenterManager() {
        mCategoryPagerPresenter = new CategoryPagerPresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();
        mSelectPagePresenter = new SelectPagePresenterImpl();
        mDiscountsPresenter = new DiscountsPresenterImpl();
        mSearchPresenter = new SearchPresenterImpl();
        mLoginPresenter = new LoginPresenterImpl();
        mRegisterPresenter = new RegisterPresenterImpl();
        mRegainPresenter = new RegainPresenterImpl();
    }

    public RegainPresenterImpl getRegainPresenter() {
        return mRegainPresenter;
    }

    public CategoryPagerPresenterImpl getCategoryPagerPresenter() {
        return mCategoryPagerPresenter;
    }

    public SearchPresenterImpl getSearchPresenter() {
        return mSearchPresenter;
    }

    public HomePresenterImpl getHomePresenter() {
        return mHomePresenter;
    }

    public TicketPresenterImpl getTicketPresenter() {
        return mTicketPresenter;
    }

    public SelectPagePresenterImpl getSelectPagePresenter() {
        return mSelectPagePresenter;
    }

    public DiscountsPresenterImpl getDiscountsPresenter() {
        return mDiscountsPresenter;
    }

    public LoginPresenterImpl getLoginPresenter() {
        return mLoginPresenter;
    }

    public RegisterPresenterImpl getRegisterPresenter() {
        return mRegisterPresenter;
    }
}
