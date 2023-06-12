package com.example.taobaounion.ui.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.RegainInfo;
import com.example.taobaounion.model.bean.RegisterInfo;
import com.example.taobaounion.presenter.impl.RegainPresenterImpl;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.TextCheck;
import com.example.taobaounion.utils.UserUtils;
import com.example.taobaounion.view.IRegainCallBack;
import com.itheima.roundedimageview.RoundedImageView;

import butterknife.BindView;

public class RegainActivity extends BaseActivity implements IRegainCallBack {
    @BindView(R.id.activity_regain_back)
    public View mBack;
    @BindView(R.id.activity_regain_code)
    public EditText mVerifyCode;
    @BindView(R.id.activity_regain_show_code)
    public RoundedImageView mGetVerify;
    @BindView(R.id.activity_regain_newPassword)
    public EditText mNewPassword;
    @BindView(R.id.activity_regain_require_password)
    public EditText mRequirePassword;
    @BindView(R.id.activity_regain_phone)
    public EditText mPhone;
    @BindView(R.id.activity_regain_get_phone_code)
    public Button mGetPhoneCode;
    @BindView(R.id.activity_regain_phone_code)
    public EditText mPhoneCode;
    @BindView(R.id.activity_regain_regain)
    public Button mRegain;
    @BindView(R.id.activity_regain_oldPassword)
    public EditText mOldPassword;
    private RegainPresenterImpl mRegainPresenter;
    private CountDownTimer mTimer;

    @Override
    protected void initPresenter() {
        mRegainPresenter = PresenterManager.getInstance().getRegainPresenter();
    }

    @Override
    protected void initView() {
        UserUtils.showCode(mGetVerify);
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_regain;
    }

    @Override
    protected void initEven() {
        super.initEven();
        mGetPhoneCode.setOnClickListener(v -> {
            String phone = mPhone.getText().toString().trim();
            String verify = mVerifyCode.getText().toString().trim();
            if (TextCheck.isPhoneValid(phone) && TextCheck.isVerifyCodeValid(verify)) {
                mRegainPresenter.getPhoneVerifyCode(new RegisterInfo.Send(phone, verify));
                mTimer = UserUtils.getCountDownTimer(mGetPhoneCode);
                mTimer.start();
            } else {
                UserUtils.showCode(mGetVerify);
            }
        });
        mGetVerify.setOnClickListener(v -> UserUtils.showCode(mGetVerify));
        mRegain.setOnClickListener(v -> {
            String phone = mPhone.getText().toString().trim();
            String smsCode = mPhoneCode.getText().toString().trim();
            mRegainPresenter.checkSmsCode(new RegisterInfo.Check(phone, smsCode));
        });
        mBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void release() {
        super.release();
        mRegainPresenter.unregisterViewCallBack(null);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void isCheck(boolean isCheck) {
        if (isCheck) {
            String phone = mPhone.getText().toString().trim();
            String password = mOldPassword.getText().toString().trim();
            String smsCode = mPhoneCode.getText().toString().trim();
            mRegainPresenter.getPasswordBySms(smsCode, new RegainInfo.forget(phone, password));
        }
    }

    @Override
    public void isGetPassword(boolean isGetPassword) {
        if (isGetPassword) {
            String oldPwd = mOldPassword.getText().toString().trim();
            String captcha = mVerifyCode.getText().toString().trim();
            String newPwd = mNewPassword.getText().toString().trim();
            mRegainPresenter.modify(new RegainInfo.modify(oldPwd, newPwd, captcha));
        }
    }

    @Override
    public void isModify(boolean isModify) {
        if (isModify) {
            finish();
        }
    }
}
