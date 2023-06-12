package com.example.taobaounion.utils;

import android.content.Context;
import android.content.Intent;

import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.presenter.impl.TicketPresenterImpl;
import com.example.taobaounion.ui.activity.TicketActivity;

public class TicketUtil {
    public static void toTicketPage(Context context,IBaseInfo baseInfo)
    {
        TicketPresenterImpl ticketPresenter = PresenterManager.getInstance().getTicketPresenter();

        String title = baseInfo.getTitle();
        String pict_url = baseInfo.getCover();
        String click_url = baseInfo.getUrl();
        ticketPresenter.getTicket(title, click_url, pict_url);

        context.startActivity(new Intent(context, TicketActivity.class));

    }
}
