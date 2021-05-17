package com.example.verify.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.verify.R;
import com.example.verify.fragments.AddApartmentFragment;
import com.example.verify.fragments.BaseFragment;
import com.example.verify.fragments.DummySearchFragment;
import com.example.verify.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements
        DummySearchFragment.DummySearchFragmentListener,
        BaseFragment.BaseFragmentListener {

    private ActionBar mActionBar;
    private Fragment mDummySearchFragment, mSearchFragment, mAddApartmentFragment;
    private LottieAnimationView mLottieAnimationView;
    private ImageView mAddApr;
    private ImageView mSearchApr;
    private TabState mTabState = TabState.Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up action bar
        mActionBar = getSupportActionBar();
        mDummySearchFragment = new DummySearchFragment();
        mSearchFragment = new SearchFragment();
        mAddApartmentFragment = new AddApartmentFragment();


        setUpNavBar();
        setUpActionBar();
        setUpAnimation();
    }

    private void setUpActionBar(){
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.toolbar);
        mActionBar.setElevation(0);
        mActionBar.hide();
        //View view = getSupportActionBar().getCustomView();
    }

    private void setUpAnimation(){
        LottieAnimationView lottieAnimationView = findViewById(R.id.init_animation_view);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.container_fragment, mDummySearchFragment)
                        .commit();
                mActionBar.show();
                findViewById(R.id.navigation_bar).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void setUpNavBar(){
        mAddApr = findViewById(R.id.add_apr_button);
        mSearchApr = findViewById(R.id.search_apr_button);

        mAddApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabState == TabState.Search) {
                    mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button_focused));
                    mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button));
                    mTabState = TabState.Add;
                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.container_fragment, mAddApartmentFragment)
                            .commit();
                    mActionBar.show();
                }
            }
        });

        findViewById(R.id.navigation_bar).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDummyButtonClick() {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mSearchFragment)
                .commit();
    }

    @Override
    public void onBaseFragmentCreated() {


        mSearchApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabState == TabState.Add) {
                    mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button));
                    mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button_focused));
                    mTabState = TabState.Search;
                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.container_fragment, mSearchFragment)
                            .commit();
                    mActionBar.show();
                }
            }
        });
    }

    private enum TabState{
        Search,
        Add
    }
}