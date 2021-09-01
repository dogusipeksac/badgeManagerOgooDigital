package com.example.badgemanageogoodigital.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.badgemanageogoodigital.Adapter.BadgeGridAdapter;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.R;

@SuppressLint("ValidFragment")
public class GridFragment extends Fragment {
    private GridView mGridView;
    private BadgeGridAdapter mBadgeGridAdapter;
    BadgeData[] gridItems={};
    private Activity activity;
    //çekilen 4lü listeler buraya geliyor gridItems ile
    @SuppressLint("ValidFragment")
    public GridFragment(BadgeData[] gridItems, Activity activity) {
        this.gridItems = gridItems;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        //burada grid oluşturuluyor
        View view;
        view=inflater.inflate(R.layout.grid,container,false);
        mGridView=(GridView) view.findViewById(R.id.gridView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (activity!=null){
            mBadgeGridAdapter =new BadgeGridAdapter(activity,gridItems);
            //oluşturulan gridi adapter ile set ediyoruz
            if (mGridView!=null){
                mGridView.setAdapter(mBadgeGridAdapter);
            }
        }
    }


}
