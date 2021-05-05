package com.example.verify.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.verify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView mAddApr;
    private ImageView mSearchApr;
    private TabState mTabState = TabState.Search;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddApr = this.getView().findViewById(R.id.add_apr_button);
        mSearchApr = this.getView().findViewById(R.id.search_apr_button);

        mAddApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTabState == TabState.Search){
                    mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button_focused));
                    mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button));
                    mTabState = TabState.Add;
                }

            }
        });

        mSearchApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTabState == TabState.Add){
                    mAddApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_apr_button));
                    mSearchApr.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_apr_button_focused));
                    mTabState = TabState.Search;
                }
            }
        });
    }

    private enum TabState{
        Search,
        Add
    }
}