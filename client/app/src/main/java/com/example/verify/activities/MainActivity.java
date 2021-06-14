package com.example.verify.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.verify.R;
import com.example.verify.components.ApartmentProfile;
import com.example.verify.components.ApartmentProfileEnriched;
import com.example.verify.components.ApartmentReview;
import com.example.verify.fragments.AddApartmentFragment;
import com.example.verify.fragments.ApartmentProfileFragment;
import com.example.verify.fragments.ApartmentReviewContainerFragment;
import com.example.verify.fragments.BaseFragment;
import com.example.verify.fragments.DummySearchFragment;
import com.example.verify.fragments.IntroductionFragment;
import com.example.verify.fragments.SearchFragment;
import com.example.verify.fragments.SurveyFragment;
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
        IntroductionFragment.IntroductionFragmentListener,
        ApartmentProfileFragment.ApartmentProfileFragmentListener {

    private ActionBarWrapper mActionBar;
    //private ActionBar mActionBar;
    private ImageView mAddApr;
    private ImageView mSearchApr;
    private Fragment mDummySearchFragment,
            mAddApartmentFragment;
    private SearchFragment mSearchFragment;
    private ApartmentProfileFragment mApartmentProfileFragment;
    private IntroductionFragment mIntroductionFragment;
    private ApartmentReviewContainerFragment mApartmentReviewContainerFragment;
    private SurveyFragment mSurveyFragment;
    //private LottieAnimationView mLottieAnimationView;


    private MainActivityStateManager mMainActivityStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.custom_blue_1));
        }
        setContentView(R.layout.activity_main);
        // Set up action bar
        mActionBar = new ActionBarWrapper(this, getSupportActionBar());
        mActionBar.setUpActionBar();

        mDummySearchFragment = new DummySearchFragment();
        mSearchFragment = new SearchFragment();
        mAddApartmentFragment = new AddApartmentFragment();
        mApartmentProfileFragment = new ApartmentProfileFragment();
        mIntroductionFragment = new IntroductionFragment();
        mApartmentReviewContainerFragment = new ApartmentReviewContainerFragment();
        mSurveyFragment = new SurveyFragment();

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
    public void onBackPressed() {
        try{
            FragmentState foundState = mMainActivityStateManager.popFragmentState();
            getSupportFragmentManager().
                    beginTransaction()
                    .replace(R.id.container_fragment, getFragmentFromFragmentState(foundState))
                    .commit();
        }
        catch(UnsupportedOperationException e){
            //super.onBackPressed();
        }

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
        // TODO: Enrich profile from database
        ApartmentProfileEnriched enrichedProfile = ApartmentProfileEnriched.fromApartmentProfile(profile);

        mApartmentProfileFragment.setProfile(enrichedProfile);
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
        mMainActivityStateManager.pushFragmentState(FragmentState.Searching);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mSearchFragment)
                .commit();
    }

    @Override
    public void onAddApartmentButtonClick() {
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.verify_form_url)));
        //startActivity(browserIntent);
        mMainActivityStateManager.pushFragmentState(FragmentState.Survey);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mSurveyFragment)
                .commit();
        mSurveyFragment.loadWebView();
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

    @Override
    public void onReviewClick(String headerText, ApartmentReview review) {
        mMainActivityStateManager.pushFragmentState(FragmentState.Review);
        mApartmentReviewContainerFragment.setReview(review);
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.container_fragment, mApartmentReviewContainerFragment)
                .commit();
        mActionBar.setUpReviewText(headerText);

    }

    private class MainActivityStateManager {
        private final ImageView mAddApr;
        private final ImageView mSearchApr;
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
            else if(fragmentState == FragmentState.Review){
                mActionBarWrapper.setUpReviewMode();
                mNavbar.setVisibility(View.INVISIBLE);
            }
            else if(fragmentState == FragmentState.Survey){
                mActionBarWrapper.setActionBarVisibility(true);
                mNavbar.setVisibility(View.INVISIBLE);
            }
            if(mFragmentState == FragmentState.Review){
                mActionBarWrapper.setUpRegularMode();
            }

            mFragmentState = fragmentState;
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
            case Review:
                return mApartmentReviewContainerFragment;
            case Survey:
                return mSurveyFragment;
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
        Found,
        Review,
        Survey
    }
}