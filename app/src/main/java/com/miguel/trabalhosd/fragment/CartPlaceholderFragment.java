package com.miguel.trabalhosd.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguel.trabalhosd.R;

public class CartPlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CartPlaceholderFragment() {
    }

    public static CartPlaceholderFragment newInstance(int sectionNumber) {
        CartPlaceholderFragment fragment = new CartPlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;

        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_cart_1, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_cart_2, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_cart_3, container, false);
                    break;
            }
        }

        return rootView;
    }
}