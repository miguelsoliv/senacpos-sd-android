package com.miguel.trabalhosd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miguel.trabalhosd.fragment.CartPlaceholderFragment;

public class CartPagerAdapter extends FragmentPagerAdapter {
    public CartPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CartPlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Number of pages
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}