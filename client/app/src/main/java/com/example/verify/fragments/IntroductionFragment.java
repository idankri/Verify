package com.example.verify.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.verify.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroductionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroductionFragment extends BaseFragment {

    private LinearLayout mDotsLayout;
    private ViewPager2 mPager;
    private TextView[] mDots;
    private Button mButton;
    private Fragment[] mInternalFragments;
    private IntroductionFragmentListener mIntroductionFragmentListener;

    public IntroductionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroductionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroductionFragment newInstance(String param1, String param2) {
        IntroductionFragment fragment = new IntroductionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        mInternalFragments = new Fragment[] {new IntroductionFragmentInternal1(),
                new IntroductionFragmentInternal2(),
                new IntroductionFragmentInternal3()};


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDotsLayout = Objects.requireNonNull(getView()).findViewById(R.id.introduction_dots_container);
        mPager =  Objects.requireNonNull(getView()).findViewById(R.id.introduction_view_pager);
        mButton = Objects.requireNonNull(getView()).findViewById(R.id.lets_verify_button);
        mPager.setAdapter(new ScreenSlidePagerAdapter(getActivity(), mInternalFragments, mButton));
        mDots = new TextView[mInternalFragments.length];

        dotsIndicator();
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectedIndicator(position);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIntroductionFragmentListener != null){
                    mIntroductionFragmentListener.onLetsVerifyButtonClick();
                }
            }
        });

        View skipButtonView = Objects.requireNonNull(getView()).findViewById(R.id.introduction_fragment_skip_button);
        skipButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIntroductionFragmentListener != null){
                    mIntroductionFragmentListener.onLetsVerifyButtonClick();
                }
            }
        });
    }

    private void selectedIndicator(int position) {
        for(int i = 0; i < mDots.length; i++){
            if(i == mDots.length - 1 - position){
                mDots[i].setTextColor(getResources().getColor(R.color.custom_blue_1));
            }
            else{
                mDots[i].setTextColor(getResources().getColor(R.color.custom_grey_1));
            }
        }
        if(position == mDots.length - 1){
            mButton.setVisibility(View.VISIBLE);
        } else{
            mButton.setVisibility(View.INVISIBLE);
        }
    }

    private void dotsIndicator(){
        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this.getContext());
            mDots[i].setText(Html.fromHtml("&#9679;"));
            mDots[i].setTextSize(18);
            mDotsLayout.addView(mDots[i]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DummySearchFragment.DummySearchFragmentListener){
            mIntroductionFragmentListener = (IntroductionFragmentListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must imlement DummySearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIntroductionFragmentListener = null;
    }

    public interface IntroductionFragmentListener{
        void onLetsVerifyButtonClick();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final Fragment[] mFragments;
        private final Button mButton;

        public ScreenSlidePagerAdapter(FragmentActivity fa, Fragment[] fragments, Button button) {
            super(fa);
            mFragments = fragments;
            mButton = button;
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