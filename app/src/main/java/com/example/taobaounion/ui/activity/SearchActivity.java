package com.example.taobaounion.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taobaounion.R;
import com.example.taobaounion.ui.fragment.SearchFragment;
import com.example.taobaounion.utils.keyboardUtil;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {

    private SearchFragment mSearchFragment;


    @BindView(R.id.activity_search_edit)
    public EditText mSearchWord;

    @BindView(R.id.activity_search_btn)
    public View mSearchBtn;

    @BindView(R.id.activity_search_remove)
    public View mSearchRemove;


    public SearchActivity() {

    }

    @Override
    protected void initPresenter() {

    }


    private static final SearchActivity instance = new SearchActivity();

    public static SearchActivity getInstance() {
        return instance;
    }

    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment() {
        mSearchFragment = new SearchFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!mSearchFragment.isAdded())
            transaction.add(R.id.activity_search_container, mSearchFragment);
        else
            transaction.show(mSearchFragment);
        transaction.commit();
    }


    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_search;
    }


    @Override
    protected void initEven() {
        super.initEven();
        mSearchRemove.setVisibility(View.GONE);
        //点击了搜索按钮
        mSearchBtn.setOnClickListener(v -> {
            getSearchWord();
        });

        //如果输入完按了回车就隐藏键盘
        mSearchWord.setOnEditorActionListener((v, actionId, event) -> {
            keyboardUtil.hide(getApplicationContext(),v);
            getSearchWord();
            return true;
        });

        //有输入就显示清空按钮
        mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchRemove.setVisibility(View.VISIBLE);
                mSearchRemove.setOnClickListener(v -> {
                    mSearchWord.setText("");
                    mSearchFragment.clearResult();
                    mSearchFragment.mSearchPresenter.getSearchHistory();
                    mSearchRemove.setVisibility(View.GONE);
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getSearchWord() {
        String word = mSearchWord.getText().toString();
        if (!TextUtils.isEmpty(word))
            mSearchFragment.setSearchWord(word);
    }
}