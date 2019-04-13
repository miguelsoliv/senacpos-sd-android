package com.miguel.trabalhosd.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.miguel.trabalhosd.R;
import com.miguel.trabalhosd.adapter.CartPagerAdapter;

public class CartActivity extends AppCompatActivity {
    private int page = 0;
    private ImageButton btNext, btBack;
    private Button btFinish;
    private ImageView[] indicators;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btNext = findViewById(R.id.btNext);
        btBack = findViewById(R.id.btBack);
        btFinish = findViewById(R.id.btFinish);

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

                btBack.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                btNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                btFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                viewPager.setCurrentItem(page, true);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page -= 1;
                viewPager.setCurrentItem(page, true);
            }
        });

        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }
}