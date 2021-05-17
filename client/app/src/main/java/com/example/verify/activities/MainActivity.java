package com.example.verify.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.verify.R;
import com.example.verify.fragments.AddApartmentFragment;
import com.example.verify.fragments.ApartmentProfileFragment;
import com.example.verify.fragments.BaseFragment;
import com.example.verify.fragments.DummySearchFragment;
import com.example.verify.fragments.SearchFragment;
import com.example.verify.utils.ActionBarWrapper;

public class MainActivity extends AppCompatActivity implements
        DummySearchFragment.DummySearchFragmentListener,
        BaseFragment.BaseFragmentListener,
        SearchFragment.SearchFragmentListener,
        ActionBarWrapper.ActionBarWrapperListener,
        AddApartmentFragment.AddApartmentFragmentListener {

    private ActionBarWrapper mActionBar;
    //private ActionBar mActionBar;
    private Fragment mDummySearchFragment,
            mSearchFragment,
            mAddApartmentFragment,
            mApartmentProfileFragment;
    private LottieAnimationView mLottieAnimationView;
    private ImageView mAddApr;
    private ImageView mSearchApr;

    private MainActivityState mMainActivityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up action bar
        mActionBar = new ActionBarWrapper(this, getSupportActionBar());
        mActionBar.setUpActionBar();

        mDummySearchFragment = new DummySearchFragment();
        mSearchFragment = new SearchFragment();
        mAddApartmentFragment = new AddApartmentFragment();
        mApartmentProfileFragment = new ApartmentProfileFragment();

        setUpNavBar();
        mMainActivityState = new MainActivityState(TabState.Search,
                SearchState.Animation,
                mActionBar,
                findViewById(R.id.navigation_bar));

        setUpAnimation();
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
                mMainActivityState.setSearchState(SearchState.Searching);
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
                if (mMainActivityState.getTabState() == TabState.Search) {
                    mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button_focused));
                    mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button));
                    mMainActivityState.setTabState(TabState.Add);
                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.container_fragment, mAddApartmentFragment)
                            .commit();

                }
            }
        });
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
                if (mMainActivityState.getTabState() == TabState.Add) {
                    mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button));
                    mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button_focused));
                    mMainActivityState.setTabState(TabState.Search);
                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.container_fragment, mSearchFragment)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onSearchButtonClick() {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mApartmentProfileFragment)
                .commit();
        mMainActivityState.setSearchState(SearchState.Found);
    }

    @Override
    public void onBackArrowLogoClick() {
        mMainActivityState.setSearchState(SearchState.Searching);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mSearchFragment)
                .commit();
    }

    @Override
    public void onSearchLogoClick() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void onAddApartmentButtonClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.verify_form_url)));
        startActivity(browserIntent);
    }

    private class MainActivityState{
        private TabState mTabState;
        private SearchState mSearchState;
        private ActionBarWrapper mActionBarWrapper;
        private View mNavbar;

        public MainActivityState(TabState tabState,
                                 SearchState searchState,
                                 ActionBarWrapper actionBarWrapper,
                                 View navbar){
            mActionBarWrapper = actionBarWrapper;
            mNavbar = navbar;
            setTabState(tabState);
            setSearchState(searchState);


        }

        public TabState getTabState(){
            return mTabState;
        }

        public void setTabState(TabState tabState){
            mTabState = tabState;
            if(tabState == TabState.Search){
                mActionBar.setActionBarUtilsVisibility(false);
            }
            else{
                mActionBar.setActionBarUtilsVisibility(true);
            }
        }

        public SearchState getSearchState(){
            return mSearchState;
        }

        public void setSearchState(SearchState searchState){
            mSearchState = searchState;
            if(searchState == SearchState.Found){
                mActionBarWrapper.setActionBarVisibility(true);
                mActionBarWrapper.setActionBarUtilsVisibility(true);
                mNavbar.setVisibility(View.INVISIBLE);
            }
            else if(searchState == SearchState.Searching){
                mActionBarWrapper.setActionBarVisibility(true);
                mActionBarWrapper.setActionBarUtilsVisibility(false);
                mNavbar.setVisibility(View.VISIBLE);
            }
            else if(searchState == SearchState.Animation){
                mActionBarWrapper.setActionBarVisibility(false);
                mNavbar.setVisibility(View.INVISIBLE);
            }

        }
    }

    private enum TabState{
        Search,
        Add
    }

    private enum SearchState{
        Animation,
        Searching,
        Found
    }
}