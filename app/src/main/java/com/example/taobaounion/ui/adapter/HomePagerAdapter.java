package com.example.taobaounion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    List<Categories.DataBean> mDate = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return HomePagerFragment.newInstance(mDate.get(position));
    }

    @Override
    public int getCount() {
        return mDate.size() == 0 ? 0 : mDate.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDate.get(position).getTitle();
    }

    public void setCategories(Categories categories) {
        mDate.clear();
        mDate.addAll(categories.getData());
        notifyDataSetChanged();
    }
}
