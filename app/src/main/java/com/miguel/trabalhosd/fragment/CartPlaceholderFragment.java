/*
 * MIT License
 *
 * Copyright (c) 2019 Miguel Soares de Oliveira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.miguel.trabalhosd.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.Utils;
import com.miguel.trabalhosd.activity.MainActivity;
import com.miguel.trabalhosd.adapter.CartListAdapter;

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

                    RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    CartListAdapter adapter = new CartListAdapter(getContext());
                    recyclerView.setAdapter(adapter);

                    TextView textSubTotal = rootView.findViewById(R.id.textSubTotal);

                    if (MainActivity.getSubTotal() == 0) {
                        textSubTotal.setText("R$ 0,00");
                    } else {
                        textSubTotal.setText(Utils.applyDecimalFormat(MainActivity.getSubTotal()));
                    }
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_cart_2, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_cart_3, container, false);

                    TextView textTotal = rootView.findViewById(R.id.textTotal);
                    textTotal.setText(Utils.applyDecimalFormat(MainActivity.getSubTotal() + 9.9));
                    break;
            }
        }

        return rootView;
    }
}