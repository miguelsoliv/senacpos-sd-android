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

package com.miguel.trabalhosd.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.adapter.CartPagerAdapter;
import com.miguel.trabalhosd.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private int page = 0;
    private ImageView[] indicators;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_my_cart);

        final ImageButton imageBtNext = findViewById(R.id.imageBtNext);
        final ImageButton imageBtBack = findViewById(R.id.imageBtBack);
        final Button btFinish = findViewById(R.id.btFinish);

        CartPagerAdapter carrinhoPagerAdapter = new CartPagerAdapter(getSupportFragmentManager());
        indicators = new ImageView[]{findViewById(R.id.indicator0),
                findViewById(R.id.indicator1), findViewById(R.id.indicator2)};

        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(carrinhoPagerAdapter);

        viewPager.setCurrentItem(page);
        updateIndicators(page);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);

                imageBtBack.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                imageBtNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                btFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        imageBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                viewPager.setCurrentItem(page, true);
            }
        });

        imageBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page -= 1;
                viewPager.setCurrentItem(page, true);
            }
        });

        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.editName);
                EditText editCel = findViewById(R.id.editCel);
                EditText editAddress = findViewById(R.id.editAddress);
                EditText editNumber = findViewById(R.id.editNumber);
                EditText editComplem = findViewById(R.id.editComplem);
                RadioGroup radioGroupPay = findViewById(R.id.radioGroupPay);

                if (MainActivity.getCartList().size() == 0) {
                    return;
                }

                if (editName.getText() == null || editCel.getText() == null) {
                    return;
                }

                if (editAddress.getText() == null || editNumber.getText() == null) {
                    return;
                }

                if (radioGroupPay.getCheckedRadioButtonId() == -1) {
                    return;
                }

                DatabaseReference orderDBReference = FirebaseDatabase.getInstance().getReference("pedidos");
                String id = orderDBReference.push().getKey();

                StringBuilder pedido = new StringBuilder();
                for (int i = 0; i < MainActivity.getCartList().size(); i++) {
                    pedido.append(MainActivity.getCartList().get(i).getQuant());
                    pedido.append(" x ");
                    pedido.append(MainActivity.getCartList().get(i).getNome());
                    pedido.append(" | ");
                }

                StringBuilder endereco = new StringBuilder();
                endereco.append(editAddress.getText().toString());
                endereco.append(" ");
                endereco.append(editNumber.getText().toString());
                endereco.append(" ");
                endereco.append(editComplem.getText().toString());

                RadioButton radioButton = findViewById(radioGroupPay.getCheckedRadioButtonId());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
                String date = sdf.format(new Date());

                Order order = new Order(editName.getText().toString(), pedido.toString(), endereco.toString(),
                        radioButton.getText().toString(), date, editCel.getText().toString(), MainActivity.getSubTotal());

                orderDBReference.child(id).setValue(order);
                MainActivity.checkout();
                finish();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_login_register:
                startActivity(new Intent(this, LoginRegisterActivity.class));
                finish();
                break;
            case R.id.nav_menu:
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }
}