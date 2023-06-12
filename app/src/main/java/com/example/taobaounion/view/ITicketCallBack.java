package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallBack;
import com.example.taobaounion.model.bean.TicketResult;

public interface ITicketCallBack extends IBaseCallBack {
    void onTicketLoaded(String cover, TicketResult result);
}
