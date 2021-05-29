package com.example.verify.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.verify.R;

public class ActionBarWrapper {
    private ActionBar mActionBar;
    private ActionBarWrapperListener mListener;
    private Context mContext;

    public ActionBarWrapper(Context context, ActionBar actionBar){
        if(context instanceof ActionBarWrapperListener){
            mListener = (ActionBarWrapperListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement ActionBarWrapperListener");
        }
        mActionBar = actionBar;
        mContext = context;
    }

    public void setUpActionBar(){
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.toolbar);
        Toolbar toolbar=(Toolbar)mActionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.getContentInsetStart();
        toolbar.getContentInsetEnd();
        toolbar.setBackgroundColor(Color.WHITE);
        //toolbar.setElevation(10);
        mActionBar.setElevation(1);
        mActionBar.hide();
        //View view = getSupportActionBar().getCustomView();
        setActionBarUtilsVisibility(false);
        mActionBar.getCustomView().findViewById(R.id.back_arrow_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackArrowLogoClick();
            }
        });
    }

    public void setUpReviewMode(){
        mActionBar.setCustomView(R.layout.review_toolbar);
        Toolbar toolbar=(Toolbar)mActionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.getContentInsetStart();
        toolbar.getContentInsetEnd();
        toolbar.setBackgroundColor(mContext.getResources().getColor(R.color.custom_blue_1));
        mActionBar.setElevation(1);
        mActionBar.getCustomView().findViewById(R.id.back_arrow_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackArrowLogoClick();
            }
        });

    }

    public void setUpReviewText(String text){
        TextView textView = mActionBar.getCustomView().findViewById(R.id.apartment_address_header);
        textView.setText(text);
    }

    public void setUpRegularMode(){
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.toolbar);
        Toolbar toolbar=(Toolbar)mActionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.getContentInsetStart();
        toolbar.getContentInsetEnd();
        toolbar.setBackgroundColor(Color.WHITE);
        mActionBar.setElevation(1);
        mActionBar.getCustomView().findViewById(R.id.back_arrow_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackArrowLogoClick();
            }
        });
    }

    public void setActionBarUtilsVisibility(boolean visible){
        mActionBar.getCustomView().findViewById(R.id.back_arrow_logo).setVisibility(visible?
                View.VISIBLE: View.INVISIBLE);
        mActionBar.getCustomView().findViewById(R.id.search_logo).setVisibility(visible?
                View.VISIBLE: View.INVISIBLE);
    }

    public void setActionBarVisibility(boolean visible){
        if(visible)
            mActionBar.show();
        else
            mActionBar.hide();
    }

    public interface ActionBarWrapperListener{
        void onBackArrowLogoClick();
        void onSearchLogoClick();
    }
}
