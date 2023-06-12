package com.example.taobaounion.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.TicketResult;
import com.example.taobaounion.presenter.impl.TicketPresenterImpl;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ITicketCallBack;

import java.util.List;

import butterknife.BindView;

public class TicketActivity extends BaseActivity implements ITicketCallBack {

    private TicketPresenterImpl mTicketPresenter;

    @BindView(R.id.activity_ticket_image)
    public ImageView mCover;

    @BindView(R.id.activity_ticket_show_ticket)
    public TextView mTicketCode;

    @BindView(R.id.activity_ticket_get_ticket)
    public Button mGetTicket;

    @BindView(R.id.activity_ticket_toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.activity_ticket_image_loading)
    public View mLoadingView;

    @BindView(R.id.activity_ticket_image_retry)
    public TextView mTvRetry;
    private boolean mHasTaoBaoApp = true;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initPresenter() {
        mTicketPresenter = PresenterManager.getInstance().getTicketPresenter();
        if (mTicketPresenter != null) {
            mTicketPresenter.registerViewCallBack(this);
        }

        //act=android.intent.action.MAIN
        // cat=[android.intent.category.LAUNCHER]
        // flg=0x10200000
        // cmp=com.taobao.taobao/com.taobao.tao.welcome.Welcome
        // bnds=[63,144][215,296] (has extras)} from uid 10078 from pid 2361 callingPackage com.miui.home
        PackageManager pm = getPackageManager();
        try {
            List<PackageInfo> packages = pm.getInstalledPackages(0);
            for (PackageInfo aPackage : packages) {
               if ("com.taobao.taobao".equals(aPackage.packageName))
                   mHasTaoBaoApp = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHasTaoBaoApp = false;
        }

        LogUtils.d(this,mHasTaoBaoApp);
        mGetTicket.setText(mHasTaoBaoApp ? "打开淘宝领劵" : "复制口令");
    }

    @Override
    protected void release() {
        if (mTicketPresenter != null) {
            mTicketPresenter.unregisterViewCallBack(this);
        }
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_ticket;
    }

    @Override
    public void onError() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mTvRetry != null) {
            mTvRetry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoading() {
        if (mTvRetry != null) {
            mTvRetry.setVisibility(View.GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onTicketLoaded(String cover, TicketResult result) {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mCover != null || !TextUtils.isEmpty(cover))
        {
            String coverPath = UrlUtils.getCoverPath(cover);
            Glide.with(this).load(coverPath).into(mCover);
        }
        if (result != null && result.getData().getTbk_tpwd_create_response() != null)
        {
            mTicketCode.setText(result.getData().getTbk_tpwd_create_response().getData().getModel());
        }
    }

    @Override
    protected void initEven() {
        mGetTicket.setOnClickListener(v -> {
            String code = mTicketCode.getText().toString().trim();
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData ticket_code = ClipData.newPlainText("ticket_code", code);
            cm.setPrimaryClip(ticket_code);

            if (mHasTaoBaoApp)
            {
                Intent taoBaoIntent = new Intent();
                taoBaoIntent.setAction("android.intent.action.MAIN");
                taoBaoIntent.addCategory("android.intent.category.LAUNCHER");
                ComponentName componentName = new ComponentName("com.taobao.taobao", "com.taobao.tao.TBMainActivity");
                taoBaoIntent.setComponent(componentName);
                taoBaoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(taoBaoIntent);
            }
            else {
                ToastUtils.showToast("已经复制,粘贴分享,或打开淘宝");
            }
        });
    }
}
