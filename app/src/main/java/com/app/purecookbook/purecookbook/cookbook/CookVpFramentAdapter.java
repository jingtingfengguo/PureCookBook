package com.app.purecookbook.purecookbook.cookbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CookVpFramentAdapter extends FragmentPagerAdapter {
    List<CookFragment>list;
    List<String>title;


    public CookVpFramentAdapter(FragmentManager fm,List<CookFragment>list,List<String>title) {
        super(fm);
        this.list=list;
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }


}
