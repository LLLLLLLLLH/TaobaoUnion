package com.example.taobaounion.ui.activity;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.RegisterInfo;
import com.example.taobaounion.presenter.impl.RegisterPresenterImpl;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.TextCheck;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.utils.UserUtils;
import com.example.taobaounion.view.IRegisterCallBack;
import com.itheima.roundedimageview.RoundedImageView;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity implements IRegisterCallBack {


    @BindView(R.id.activity_register_back)
    public TextView mBack;

    @BindView(R.id.activity_register_name)
    public EditText mNIckName;

    @BindView(R.id.activity_register_phone)
    public EditText mPhone;

    @BindView(R.id.activity_register_get_phone_code)
    public Button mGetPhoneCode;

    @BindView(R.id.activity_register_phone_code)
    public EditText mPhoneCode;

    @BindView(R.id.activity_register_password)
    public EditText mPassword;

    @BindView(R.id.activity_register_code)
    public EditText mCode;

    @BindView(R.id.activity_register_show_code)
    public RoundedImageView mShowCode;

    @BindView(R.id.activity_register_register)
    public Button mRegister;
    private RegisterPresenterImpl mRegisterPresenter;
    private CountDownTimer mTimer;

    @Override
    protected void initPresenter() {
        mRegisterPresenter = PresenterManager.getInstance().getRegisterPresenter();
        mRegisterPresenter.registerViewCallBack(this);
    }

    @Override
    protected void initView() {
        UserUtils.showCode(mShowCode);
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEven() {
        mGetPhoneCode.setOnClickListener(v -> {
            String phone = mPhone.getText().toString().trim();
            String verify = mCode.getText().toString().trim();
            if (TextCheck.isPhoneValid(phone) && TextCheck.isVerifyCodeValid(verify)) {
                mRegisterPresenter.getPhoneVerifyCode(new RegisterInfo.Send(phone, verify));
                mTimer = UserUtils.getCountDownTimer(mGetPhoneCode);
                mTimer.start();
            } else {
                UserUtils.showCode(mShowCode);
            }
        });

        mRegister.setOnClickListener(v -> {
            String phone = mPhone.getText().toString().trim();
            String smsCode = mPhoneCode.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String nickName = mNIckName.getText().toString().trim();
            mRegisterPresenter.checkSmsCode(new RegisterInfo.Check(phone, smsCode));
            mRegisterPresenter.register(smsCode, new RegisterInfo.Register(phone, password, nickName));
        });

        mShowCode.setOnClickListener(v -> UserUtils.showCode(mShowCode));
        mBack.setOnClickListener(v -> finish());

    }

    @Override
    public void onSuccess(boolean isSuccess) {
        if (isSuccess) {
            ToastUtils.showToast("注册成功");
            finish();
        } else {
            UserUtils.showCode(mShowCode);
        }
    }


    @Override
    protected void release() {
        super.release();
        mRegisterPresenter.unregisterViewCallBack(null);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
