package com.example.verify.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.verify.R;
import com.example.verify.fragments.DummySearchFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setElevation(0);
        actionBar.hide();
        //View view = getSupportActionBar().getCustomView();

        setContentView(R.layout.activity_main);
        // Set up animation
        LottieAnimationView lottieAnimationView = findViewById(R.id.init_animation_view);
        Fragment dummySearchFragment = new DummySearchFragment();
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.container_fragment, dummySearchFragment)
                        .commit();
                actionBar.show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }
}