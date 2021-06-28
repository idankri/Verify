package com.example.verify.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.verify.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentReviewProsConsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentReviewProsConsFragment extends Fragment {

    private TabLayout mTabLayout;

    private ExpandableListView mExpandableListView;
    private List<String> mListGroup;
    private HashMap<String, List<String>> mProsListItems;
    private HashMap<String, List<String>> mConsListItems;
    private ProsConsListAdapter mAdapter;

    public ApartmentReviewProsConsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApartmentReviewProsConsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApartmentReviewProsConsFragment newInstance(String param1, String param2) {
        ApartmentReviewProsConsFragment fragment = new ApartmentReviewProsConsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        mListGroup = new ArrayList<>();
        mProsListItems = new HashMap<>();
        mConsListItems = new HashMap<>();
        initListData();
    }

    private void initListData() {
        mListGroup.add(getString(R.string.apartment_review_pros_cons_group_1_title));
        mListGroup.add(getString(R.string.apartment_review_pros_cons_group_2_title));
        mListGroup.add(getString(R.string.apartment_review_pros_cons_group_3_title));

        String[] curArray; //= getResources().getStringArray(R.array.apartment_review_pros_group_1_placeholder);
        List<String> currentItemList, currentConsItemsList;

        int[] prosArraysToFill = {R.array.apartment_review_pros_group_1_placeholder,
                R.array.apartment_review_pros_group_2_sub_1_placeholder,
                R.array.apartment_review_pros_group_3_placeholder};
        int[] consArraysToFill = {R.array.apartment_review_cons_group_1_placeholder,
                R.array.apartment_review_cons_group_2_sub_1_placeholder,
                R.array.apartment_review_cons_group_3_placeholder};

        int[] locationsToFill = {0, 2};
        //int locationsToFillIndex = 0;

        for(int currentLocation : locationsToFill){
            currentItemList = new ArrayList<>();
            for (String item: getResources().getStringArray(prosArraysToFill[currentLocation])){
                currentItemList.add(Html.fromHtml("&#183;") + " " + item);
            }
            mProsListItems.put(mListGroup.get(currentLocation), currentItemList);

            currentItemList = new ArrayList<>();
            for (String item: getResources().getStringArray(consArraysToFill[currentLocation])){
                currentItemList.add(Html.fromHtml("&#183;") + " " + item);
            }
            mConsListItems.put(mListGroup.get(currentLocation), currentItemList);
        }

        int[] secondGroupSubgroups = {
                R.string.apartment_review_pros_cons_group_2_subtitle_1,
                R.string.apartment_review_pros_cons_group_2_subtitle_2,
                R.string.apartment_review_pros_cons_group_2_subtitle_3};
        int[] prosSubGroups = {
                R.array.apartment_review_pros_group_2_sub_1_placeholder,
                R.array.apartment_review_pros_group_2_sub_2_placeholder,
                R.array.apartment_review_pros_group_2_sub_3_placeholder};
        int[] consSubGroups = {
                R.array.apartment_review_cons_group_2_sub_1_placeholder,
                R.array.apartment_review_cons_group_2_sub_2_placeholder,
                R.array.apartment_review_cons_group_2_sub_3_placeholder};
        currentItemList = new ArrayList<>();
        for (int i = 0; i < secondGroupSubgroups.length; i++) {

            currentItemList.add(getResources().getString(secondGroupSubgroups[i]));
            for (String item : getResources().getStringArray(prosSubGroups[i])) {
                currentItemList.add(Html.fromHtml("&#183;") + " " + item);
            }

        }
        mProsListItems.put(mListGroup.get(1), currentItemList);


        currentItemList = new ArrayList<>();
        for (int i = 0; i < secondGroupSubgroups.length; i++) {

            currentItemList.add(getResources().getString(secondGroupSubgroups[i]));
            for (String item : getResources().getStringArray(consSubGroups[i])) {
                currentItemList.add(Html.fromHtml("&#183;") + " " + item);
            }

        }
        mConsListItems.put(mListGroup.get(1), currentItemList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apartment_review_pros_cons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = Objects.requireNonNull(getView()).findViewById(R.id.apartment_review_pros_cons_tab_layout);
        mExpandableListView = Objects.requireNonNull(getView()).findViewById(R.id.apartment_review_pros_cons_list);
        mAdapter = new ProsConsListAdapter(
                this.getContext(),
                mListGroup,
                new Drawable[]{ResourcesCompat.getDrawable(getResources(), R.drawable.ic_apartment_holder_logo, null),
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hand_tools_logo, null),
                        ResourcesCompat.getDrawable(getResources(), R.drawable.ic_around_logo, null)},
                mProsListItems);
        mAdapter.notifyDataSetChanged();
        mExpandableListView.setAdapter(mAdapter);
        //mExpandableListView.indica

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setTextColor(Color.BLACK);

                tabTextView.setText(tab.getText());

                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }

            }

        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAdapter.updateListItems(tab.getPosition() == 0 ? mProsListItems: mConsListItems);
                mAdapter.notifyDataSetChanged();

                TextView text = (TextView) tab.getCustomView();

                if (text != null) {
                    text.setTypeface(null, Typeface.BOLD);
                }
                // Uncomment for colors underline in tabs
//                if(mTabLayout.getSelectedTabPosition() == 0){
//                    mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1AD769"));
//                }
//                else{
//                    mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#EB5757"));
//                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();

                if (text != null) {
                    text.setTypeface(null, Typeface.NORMAL);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private class ProsConsListAdapter extends BaseExpandableListAdapter{

        private Context mContext;
        private List<String> mListGroup;
        private HashMap<String, List<String>> mListItems;
        private Drawable[] mListGroupDrawings;

        public ProsConsListAdapter(
                Context context,
                List<String> listGroup,
                Drawable[] listGroupDrawings,
                HashMap<String, List<String>> listItems){
            mContext = context;
            mListGroup = listGroup;
            mListGroupDrawings = listGroupDrawings;
            mListItems = listItems;
        }

        public void updateListItems(HashMap<String, List<String>> items){
            mListItems = items;
        }

        @Override
        public int getGroupCount() {
            return mListGroup.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return Objects.requireNonNull(mListItems.get(mListGroup.get(groupPosition))).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mListGroup.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return Objects.requireNonNull(mListItems.get(mListGroup.get(groupPosition))).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String group = (String) getGroup((groupPosition));
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_group, null);
            }

            TextView textView = convertView.findViewById(R.id.list_parent);
            textView.setText(group);
            ImageView icon = convertView.findViewById(R.id.list_group_icon);
            icon.setImageDrawable(mListGroupDrawings[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String child = (String) getChild(groupPosition, childPosition);
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
            }

            TextView textView = convertView.findViewById(R.id.list_child);
            textView.setText(child);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}