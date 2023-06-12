package com.example.taobaounion.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseApplication;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.UserData;
import com.example.taobaounion.ui.activity.LoginActivity;
import com.example.taobaounion.ui.activity.MineActivity;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.UserInfoUtils;
import com.itheima.roundedimageview.RoundedImageView;

import butterknife.BindView;

public class MineFragment extends BaseFragment {

    @BindView(R.id.fragment_mine_login)
    public View mLoginLayout;

    @BindView(R.id.fragment_mine_cover)
    public RoundedImageView mCover;
    @BindView(R.id.fragment_mine_nickname)
    public TextView mNickName;
    @BindView(R.id.fragment_mine_sign)
    public TextView mSign;

    @BindView(R.id.fragment_mine_fans)
    public TextView mFans;

    @BindView(R.id.fragment_mine_moment)
    public TextView mMoment;

    @BindView(R.id.fragment_mine_follow)
    public TextView mFollow;
    private Intent mIntent;
    private ActivityResultLauncher<Intent> mLauncher;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View rootView) {
        updateUi(UserInfoUtils.getInfo());
        createLauncher();
        setState(STATE.SUCCESS);
    }

    private void createLauncher() {
        mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK)
                    updateUi(UserInfoUtils.getInfo());
            }
        });
    }

    private void updateUi(UserData userInfo) {
        if (userInfo.isLogin()) {
            LogUtils.d(this,"登录成功执行更新------->");
            Glide.with(BaseApplication.getAppContext()).load(userInfo.getAvatar()).
                    into(mCover);
            mSign.setText(userInfo.getSign());
            mNickName.setText(userInfo.getNickname());
            mFollow.setText(userInfo.getFollowCount());
            mFans.setText(userInfo.getFansCount());
            mMoment.setText(userInfo.getMomentCount());
            mIntent = new Intent(this.getContext(), MineActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        else
        {
            LogUtils.d(this,"退出登录执行更新------->");
            mCover.setImageResource(R.mipmap.default_avatar);
            mSign.setText(userInfo.getSign());
            mNickName.setText(userInfo.getNickname());
            mFollow.setText(userInfo.getFollowCount());
            mFans.setText(userInfo.getFansCount());
            mMoment.setText(userInfo.getMomentCount());
            mIntent = new Intent(this.getContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLoginLayout.setOnClickListener(v -> {
            mLauncher.launch(mIntent);
        });
    }

    @Override
    public void onResume() {
        UserData info = UserInfoUtils.getInfo();
        updateUi(info);
        super.onResume();
        LogUtils.d(this, "mineFragment显示----------------------->" + info.isLogin());
    }
}
