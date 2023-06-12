package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ITicketCallBack;

public interface ITicketPresenter extends IBasePresenter<ITicketCallBack> {
    void getTicket(String title,String url,String cover);
}
