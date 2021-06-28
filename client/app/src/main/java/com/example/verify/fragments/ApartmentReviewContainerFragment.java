package com.example.verify.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.verify.R;
import com.example.verify.components.ApartmentReview;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentReviewContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentReviewContainerFragment extends Fragment {
    private ApartmentReview mReview;

    private ViewPager2 mPager;
    private ApartmentReviewOverviewFragment mOverviewFragment;
    private Fragment[] mInternalFragments;
    private TabLayout mTabLayout;

    public ApartmentReviewContainerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApartmentReviewContainerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApartmentReviewContainerFragment newInstance(String param1, String param2) {
        ApartmentReviewContainerFragment fragment = new ApartmentReviewContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


        mOverviewFragment = new ApartmentReviewOverviewFragment();
        mInternalFragments = new Fragment[] {
                mOverviewFragment,
                new ApartmentReviewProsConsFragment(),
                new ApartmentReviewOpinionFragment(),
                new ApartmentReviewBottomLineFragment()};
    }

    public void setReview(ApartmentReview review){
        mReview = review;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_apartment_review_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager =  Objects.requireNonNull(getView()).findViewById(R.id.apartment_review_view_pager);
        mTabLayout = Objects.requireNonNull(getView()).findViewById(R.id.apartment_review_tab_layout);
        mPager.setAdapter(new ScreenSlidePagerAdapter(getActivity(), mInternalFragments));
        mPager.setOffscreenPageLimit(20);
        mPager.setClipToPadding(true);
        // Uncomment to change tab swipe animations
        //mPager.setPadding(0,0,0,-200);
        //mPager.setPageTransformer(new Transformer);
        //mPager.setPageTransformer(((page, position) -> {
         //   page.setTranslationY(position * -(2 * 50 + 50));
        //}));
       /*mPager.setPageTransformer((page, position) -> {

            float myOffset = position * -(2 * 200 + 100);
            if (position < -1) {
                page.setTranslationY(-myOffset);
            } else if (position <= 1) {
                //float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
                page.setTranslationY(myOffset);
                //page.setScaleX(scaleFactor);
                //page.setAlpha(scaleFactor);
            } else {
                //page.setAlpha(0);
                page.setTranslationY(myOffset);
            }
        });*/

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
    createFromReview(mReview);
    }

    private void createFromReview(ApartmentReview review){

        mOverviewFragment.setReview(review);

    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final Fragment[] mFragments;

        public ScreenSlidePagerAdapter(FragmentActivity fa, Fragment[] fragments) {
            super(fa);
            mFragments = fragments;
        }

        @Override
        public Fragment createFragment(int position) {
            return mFragments[position];
        }

        @Override
        public int getItemCount() {
            return mFragments.length;
        }
    }
}
