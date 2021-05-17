package com.example.verify.utils;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.ActionBar;

import com.example.verify.R;

public class ActionBarWrapper {
    private ActionBar mActionBar;
    private ActionBarWrapperListener mListener;

    public ActionBarWrapper(Context context, ActionBar actionBar){
        if(context instanceof ActionBarWrapperListener){
            mListener = (ActionBarWrapperListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement ActionBarWrapperListener");
        }
        mActionBar = actionBar;
    }

    public void setUpActionBar(){
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.toolbar);
        mActionBar.setElevation(0);
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
