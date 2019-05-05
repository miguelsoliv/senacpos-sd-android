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

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguel.trabalhosd.CircleAnimationUtil;
import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.adapter.ProductsListAdapter;
import com.miguel.trabalhosd.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static List<Product> cartList = new ArrayList<>();
    private static double subTotal = 0;
    private List<Product> productsList;
    private ProductsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.white_background));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);

        FloatingActionButton fab = findViewById(R.id.fabCart);
        fab.setOnClickListener(this);

        Animation fabAnimation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        fab.startAnimation(fabAnimation);

        productsList = new ArrayList<>();

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductsListAdapter(MainActivity.this, productsList);
        adapter.setActionListener(new ProductsListAdapter.ProductItemActionListener() {
            @Override
            public void onItemTap(Product prod, ImageView imageView) {
                if (imageView != null) {
                    makeFlyAnimation(imageView, prod);
                }
            }
        });

        recyclerView.setAdapter(adapter);

        DatabaseReference productsDBReference = FirebaseDatabase.getInstance().getReference("produtos");
        productsDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productsList.clear();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product prod = productSnapshot.getValue(Product.class);
                    prod.setId(productSnapshot.getKey());
                    productsList.add(prod);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabCart) {
            startActivity(new Intent(this, CartActivity.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_login_register:
                startActivity(new Intent(this, LoginRegisterActivity.class));
                break;
            case R.id.nav_my_cart:
                startActivity(new Intent(this, CartActivity.class));
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
            new AlertDialog.Builder(this)
                    .setTitle("Sair")
                    .setMessage("Deseja sair do aplicativo?")
                    .setNegativeButton("Não", null)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    }).create().show();
        }
    }

    private void makeFlyAnimation(ImageView targetView, final Product prod) {
        final FloatingActionButton destView = findViewById(R.id.fabCart);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(1000)
                .setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                boolean check = false;
                for (int i = 0; i < cartList.size(); i++) {
                    if (cartList.get(i).getId().equals(prod.getId())) {
                        cartList.get(i).setQuant(cartList.get(i).getQuant() + 1);
                        check = true;
                        break;
                    }
                }

                if (!check) {
                    prod.setQuant(1);
                    cartList.add(prod);
                }

                subTotal += prod.getPreco();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(MainActivity.this, "Produto adicionado no carrinho", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        }).startAnimation();
    }

    public static List<Product> getCartList() {
        return cartList;
    }

    public static double getSubTotal() {
        return subTotal;
    }

    public static void checkout() {
        cartList.clear();
        subTotal = 0;
    }
}