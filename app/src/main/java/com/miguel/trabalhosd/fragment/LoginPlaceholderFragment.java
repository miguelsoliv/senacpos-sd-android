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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.Utils;
import com.miguel.trabalhosd.activity.MainActivity;

public class LoginPlaceholderFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static LoginPlaceholderFragment newInstance(int sectionNumber) {
        LoginPlaceholderFragment fragment = new LoginPlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (!menuVisible) {
            try {
                if (getContext() != null && getView() != null) {
                    Utils.hideKeyboardFragment(getContext(), getView());
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() != null) {
            getActivity().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.white_background));
        }

        View rootView = null;

        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_login, container, false);
                    rootView.findViewById(R.id.btLogin).setOnClickListener(this);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_register, container, false);
                    rootView.findViewById(R.id.btRegister).setOnClickListener(this);

                    EditText editCpf = rootView.findViewById(R.id.editCpf);
                    editCpf.addTextChangedListener(Utils.applyMask(editCpf, "###.###.###-##"));
                    EditText editPhone = rootView.findViewById(R.id.editPhone);
                    editPhone.addTextChangedListener(Utils.applyMask(editPhone, "#####-####"));
                    break;
            }
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLogin:
                Toast.makeText(v.getContext(), "Usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btRegister:
                Toast.makeText(v.getContext(), "Usuário registrado com sucesso", Toast.LENGTH_SHORT).show();
                break;
        }

        startActivity(new Intent(v.getContext(), MainActivity.class));
    }
}