package com.example.verify.activities;

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
import com.example.verify.components.ApartmentProfile;
import com.example.verify.fragments.AddApartmentFragment;
import com.example.verify.fragments.ApartmentProfileFragment;
import com.example.verify.fragments.BaseFragment;
import com.example.verify.fragments.DummySearchFragment;
import com.example.verify.fragments.IntroductionFragment;
import com.example.verify.fragments.SearchFragment;
import com.example.verify.utils.ActionBarWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements
        DummySearchFragment.DummySearchFragmentListener,
        BaseFragment.BaseFragmentListener,
        SearchFragment.SearchFragmentListener,
        ActionBarWrapper.ActionBarWrapperListener,
        AddApartmentFragment.AddApartmentFragmentListener,
        IntroductionFragment.IntroductionFragmentListener {

    private ActionBarWrapper mActionBar;
    //private ActionBar mActionBar;
    private ImageView mAddApr;
    private ImageView mSearchApr;
    private Fragment mDummySearchFragment,
            mAddApartmentFragment;
    private SearchFragment mSearchFragment;
    private ApartmentProfileFragment mApartmentProfileFragment;
    private IntroductionFragment mIntroductionFragment;
    private LottieAnimationView mLottieAnimationView;


    private MainActivityStateManager mMainActivityStateManager;

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
        mIntroductionFragment = new IntroductionFragment();

        setUpNavBar();

        mMainActivityStateManager = new MainActivityStateManager(FragmentState.Animation,
                mActionBar,
                findViewById(R.id.navigation_bar),
                mAddApr,
                mSearchApr);
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
                        .replace(R.id.container_fragment, mIntroductionFragment)
                        .commit();
                mMainActivityStateManager.pushFragmentState(FragmentState.Introduction);
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
                if (mMainActivityStateManager.getFragmentState() == FragmentState.Searching ||
                        mMainActivityStateManager.getFragmentState() == FragmentState.DummySearch) {
                    mMainActivityStateManager.pushFragmentState(FragmentState.AddApartment);
                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.container_fragment, mAddApartmentFragment)
                            .commit();
                }
            }
        });

        mSearchApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMainActivityStateManager.getFragmentState() == FragmentState.AddApartment) {
                    mMainActivityStateManager.pushFragmentState(FragmentState.Searching);
                    getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.container_fragment, mSearchFragment)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onDummyButtonClick() {
        mMainActivityStateManager.pushFragmentState(FragmentState.Searching);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mSearchFragment)
                .commit();
        mDummySearchFragment = null; // delete pointer for garbage collection
    }

    @Override
    public void onBaseFragmentCreated() {

    }

    @Override
    public void onSearchButtonClick() {
        ApartmentProfile profile = mSearchFragment.collectApartmentProfile();
        mApartmentProfileFragment.setProfile(profile);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mApartmentProfileFragment)
                .commit();

        mMainActivityStateManager.pushFragmentState(FragmentState.Found);
    }

    @Override
    public void onBackArrowLogoClick() {
        FragmentState foundState = mMainActivityStateManager.popFragmentState();
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, getFragmentFromFragmentState(foundState))
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

    @Override
    public void onLetsVerifyButtonClick() {
        mMainActivityStateManager.pushFragmentState(FragmentState.DummySearch);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mDummySearchFragment)
                .commit();
        mIntroductionFragment = null; // delete pointer for garbage collection
    }

    private class MainActivityStateManager {
        private ImageView mAddApr;
        private ImageView mSearchApr;
        private FragmentState mFragmentState;
        private final ActionBarWrapper mActionBarWrapper;
        private final View mNavbar;
        private final Stack<FragmentState> mFragmentStateLifo;
        private final List<FragmentState> fragmentStatesNotInLifo = Arrays.asList(FragmentState.Animation,
                                                                                    FragmentState.DummySearch,
                                                                                    FragmentState.Introduction);

        public MainActivityStateManager(FragmentState fragmentState,
                                        ActionBarWrapper actionBarWrapper,
                                        View navbar,
                                        ImageView addApr,
                                        ImageView searchApr){
            mActionBarWrapper = actionBarWrapper;
            mNavbar = navbar;
            mAddApr = addApr;
            mSearchApr = searchApr;
            mFragmentStateLifo = new Stack<>();
            setFragmentState(fragmentState);

        }

        public FragmentState getFragmentState(){
            return mFragmentState;
        }

        private void setFragmentState(FragmentState fragmentState){
            mFragmentState = fragmentState;
            if(fragmentState == FragmentState.Found){
                mActionBarWrapper.setActionBarVisibility(true);
                mNavbar.setVisibility(View.INVISIBLE);
            }
            else if(fragmentState == FragmentState.AddApartment){
                mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button_focused));
                mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button));
                mActionBarWrapper.setActionBarVisibility(true);
                mNavbar.setVisibility(View.VISIBLE);
            }
            else if(fragmentState == FragmentState.Searching || fragmentState == FragmentState.DummySearch){
                mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button));
                mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button_focused));
                mActionBarWrapper.setActionBarVisibility(true);
                mNavbar.setVisibility(View.VISIBLE);
            }
            else if(fragmentState == FragmentState.Animation){
                mActionBarWrapper.setActionBarVisibility(false);
                mNavbar.setVisibility(View.INVISIBLE);
            }
            else if(fragmentState == FragmentState.Introduction){
                mActionBarWrapper.setActionBarVisibility(true);
                mNavbar.setVisibility(View.INVISIBLE);
            }
        }

        public void pushFragmentState(FragmentState fragmentState){
            if((mFragmentState==FragmentState.DummySearch &&
                    fragmentState==FragmentState.AddApartment)){
                mFragmentStateLifo.push(FragmentState.Searching);
            }
            if(!fragmentStatesNotInLifo.contains(mFragmentState)){
                mFragmentStateLifo.push(mFragmentState);
            }
            if(mFragmentStateLifo.isEmpty()){
                mActionBarWrapper.setActionBarUtilsVisibility(false);
            } else{
                mActionBarWrapper.setActionBarUtilsVisibility(true);
            }
            setFragmentState(fragmentState);
        }

        public FragmentState popFragmentState(){
            if(mFragmentStateLifo.isEmpty()){
                throw new UnsupportedOperationException();
            }
            FragmentState ret = mFragmentStateLifo.pop();
            if(mFragmentStateLifo.isEmpty()){
                mActionBar.setActionBarUtilsVisibility(false);
            }
            setFragmentState(ret);
            return ret;
        }
    }

    private Fragment getFragmentFromFragmentState(FragmentState fragmentState){
        switch(fragmentState){
            case Introduction:
                return mIntroductionFragment;
            case DummySearch:
                return mDummySearchFragment;
            case Searching:
                return mSearchFragment;
            case AddApartment:
                return mAddApartmentFragment;
            case Found:
                return mApartmentProfileFragment;
            default:
                throw new UnsupportedOperationException();

        }
    }

    private enum FragmentState {
        Animation,
        Introduction,
        DummySearch,
        Searching,
        AddApartment,
        Found
    }
}