package com.example.taobaounion.ui.activity;

import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.UserData;
import com.example.taobaounion.presenter.impl.LoginPresenterImpl;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.UserInfoUtils;
import com.example.taobaounion.utils.UserUtils;
import com.example.taobaounion.view.ILoginCallBack;
import com.hjq.shape.view.ShapeButton;
import com.itheima.roundedimageview.RoundedImageView;

import butterknife.BindView;

public class MineActivity extends BaseActivity implements ILoginCallBack {

    @BindView(R.id.activity_mine_toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.activity_mine_avatar)
    public RoundedImageView mAvatar;
    @BindView(R.id.activity_mine_nickname)
    public TextView mNickName;
    @BindView(R.id.activity_mine_isVip)
    public TextView mIsVip;
    @BindView(R.id.activity_mine_sobCoin)
    public TextView mSobCoin;
    @BindView(R.id.activity_mine_becomeVip)
    public ShapeButton mBecomeVip;
    @BindView(R.id.activity_mine_company)
    public RelativeLayout mCompany;
    @BindView(R.id.activity_mine_company_tv)
    public TextView mCompanyTv;
    @BindView(R.id.activity_mine_position)
    public RelativeLayout mPosition;
    @BindView(R.id.activity_mine_position_tv)
    public TextView mPositionTv;
    @BindView(R.id.activity_mine_skill)
    public RelativeLayout mSkill;
    @BindView(R.id.activity_mine_skill_tv)
    public TextView mSkillTv;
    @BindView(R.id.activity_mine_coordinate)
    public RelativeLayout mCoordinate;
    @BindView(R.id.activity_mine_coordinate_tv)
    public TextView mCoordinateTv;
    @BindView(R.id.activity_mine_phone)
    public RelativeLayout mPhone;
    @BindView(R.id.activity_mine_phone_tv)
    public TextView mPhoneTv;
    @BindView(R.id.activity_mine_email)
    public RelativeLayout mEmail;
    @BindView(R.id.activity_mine_email_tv)
    public TextView mEmailTv;
    @BindView(R.id.activity_mine_modifyPassword)
    public RelativeLayout mModifyPassword;
    @BindView(R.id.activity_mine_sign_tv)
    public TextView mSignTv;
    @BindView(R.id.activity_mine_sign)
    public RelativeLayout mSign;
    @BindView(R.id.activity_mine_logout)
    public ShapeButton mLogout;
    private LoginPresenterImpl mLoginPresenter;

    @Override
    protected void initPresenter() {
        mLoginPresenter = PresenterManager.getInstance().getLoginPresenter();
        mLoginPresenter.registerViewCallBack(this);
    }

    @Override
    protected void initView() {
        updateUi();
    }

    private void updateUi() {
        UserData data = UserInfoUtils.getInfo();
        mSignTv.setText(data.getSign());
        Glide.with(getBaseContext()).load(data.getAvatar()).into(mAvatar);
        mCompanyTv.setText(data.getCompany());
        mCoordinateTv.setText(data.getArea());
        mEmailTv.setText("");
        String phone = data.getPhone();
        mPhoneTv.setText(String.format("%s****%s", phone.substring(0, 3), phone.substring(7)));
        mPositionTv.setText(data.getPosition());
        mSkillTv.setText("");
        mIsVip.setText(data.isVip() ? "VIP会员" : "普通会员");
        mIsVip.setTextColor(Color.RED);
        mSobCoin.setText(String.format("SOB币:%s", data.getSob()));
        mNickName.setText(data.getNickname());
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_mine;
    }


    @Override
    protected void initEven() {
        super.initEven();
        mLogout.setOnClickListener(v -> mLoginPresenter.doLogout());

        mToolbar.setNavigationOnClickListener(v -> finish());

    }

    @Override
    public void onSuccess(boolean isSuccess) {
        if (isSuccess) {
            setResult(RESULT_OK);
            UserUtils.showLoginSuccessAnimation(mLogout, this);
        }
    }


    @Override
    protected void release() {
        super.release();
        mLoginPresenter.unregisterViewCallBack(null);
    }
}