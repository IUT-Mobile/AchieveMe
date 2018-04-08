package com.ozenix.achieveme;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ozenix.achieveme.tools.SliderAdapter;


public class Tutorial extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;

    private TextView[] mDots;

    private Button mPrevButton;
    private Button mNextButton;

    private int currentPage;

    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotsLayout = findViewById(R.id.dotsLayout);

        mPrevButton = findViewById(R.id.prevButton);
        mNextButton = findViewById(R.id.nextButton);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        setupMovementButtons();
    }

    private void setupMovementButtons() {
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentPage < mDots.length -1) {
                    mSlideViewPager.setCurrentItem(currentPage + 1);
                } else {
                    Intent intent = MainMenu.makeIntent(Tutorial.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(currentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotsLayout.removeAllViews();

        for (int i = 0;i<mDots.length; ++i){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;

            if (position == 0) {
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(false);
                mPrevButton.setVisibility(View.INVISIBLE);

            } else if (position == mDots.length - 1) {
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);

                mNextButton.setText("Terminer");

            } else {
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);

                mNextButton.setText("Suivant");

                mPrevButton.setVisibility(View. VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public static Intent makeIntent(Context context) {
        return new Intent(context, Tutorial.class);
    }

}