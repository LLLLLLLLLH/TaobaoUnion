package com.example.taobaounion.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.presenter.impl.LoginPresenterImpl;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.utils.TextCheck;
import com.example.taobaounion.utils.UserUtils;
import com.example.taobaounion.view.ILoginCallBack;
import com.google.android.material.textfield.TextInputEditText;
import com.itheima.roundedimageview.RoundedImageView;

import java.util.Objects;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements ILoginCallBack {

    @BindView(R.id.activity_login_avatar)
    public RoundedImageView mAvatar;
    @BindView(R.id.activity_login_register)
    public Button mCreate;

    @BindView(R.id.activity_login_login)
    public Button mLogin;

    @BindView(R.id.activity_login_phone)
    public EditText mLoginPhone;

    @BindView(R.id.activity_login_password)
    public TextInputEditText mLoginPassword;

    @BindView(R.id.activity_login_code)
    public EditText mCode;

    @BindView(R.id.activity_login_show_code)
    public ImageView mShowCode;

    @BindView(R.id.activity_login_forest)
    public TextView mForest;

    @BindView(R.id.activity_login_back)
    public TextView mBack;
    private LoginPresenterImpl mLoginPresenter;
    private String mPhone;


    @Override
    protected void initPresenter() {
        mLoginPresenter = PresenterManager.getInstance().getLoginPresenter();
        mLoginPresenter.registerViewCallBack(this);
        // mLoginPresenter.getCode();
    }

    @Override
    protected void initView() {
        UserUtils.showCode(mShowCode);
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initEven() {
        mCreate.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        mShowCode.setOnClickListener(v -> {
//            mLoginPresenter.getCode();
            UserUtils.showCode(mShowCode);
        });

        mLogin.setOnClickListener(v -> {
            mPhone = mLoginPhone.getText().toString().trim();
            String password = Objects.requireNonNull(mLoginPassword.getText()).toString().trim();
            String verifyCode = mCode.getText().toString().trim();
            if (TextCheck.isRight(mPhone, password, verifyCode)) {
                mLoginPresenter.doLogin(mPhone, password, verifyCode);
            }

        });

        mForest.setOnClickListener(v ->
                startActivity(new Intent(this, RegainActivity.class)));

        mLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11)
                    mLoginPresenter.setUserAvatar(s.toString());
                else
                    mAvatar.setImageResource(R.mipmap.default_avatar);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCreate.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });

        mBack.setOnClickListener(v -> finish());
    }

    //登录成功就关闭页面
    @Override
    public void onSuccess(boolean isSuccess) {
        if (isSuccess) {
            //登录成功返回界面更新ui
            setResult(RESULT_OK);
            UserUtils.showLoginSuccessAnimation(mLogin, this);
        } else {
            //未登录成功重新,重新获取验证码
            // mLoginPresenter.getCode();
            UserUtils.showCode(mShowCode);
            mCode.setText("");
        }
    }

    @Override
    public void showAvatar(String avatarUrl) {
        Glide.with(getBaseContext()).load(avatarUrl).into(mAvatar);
    }

    @Override
    protected void release() {
        super.release();
        mLoginPresenter.unregisterViewCallBack(null);
    }
}