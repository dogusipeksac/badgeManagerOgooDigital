package com.example.badgemanageogoodigital;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.badgemanageogoodigital.Adapter.ListAdapter;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.Service.JsonService;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public PageIndicator mIndicator;
    private ViewPager awesomePager;
    private PagerAdapter pagerAdapter;
    private JsonService service;
    private List<Data> list;
    private List<BadgeData> badgeDataList;
    private Button avarageText;
    private TextView adet;
    private RatingBar ratingBar;
    ViewPager imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.list_data_recycler);
        imageContainer=findViewById(R.id.view_pager);
        //layout=findViewById(R.id.dots_container);
        avarageText=findViewById(R.id.avarage_text);
        awesomePager=(ViewPager) findViewById(R.id.view_pager);
        mIndicator=(PageIndicator) findViewById(R.id.pagerIndicator);
        adet=findViewById(R.id.adet);
        ratingBar=findViewById(R.id.ratingBar3);
        service=JsonService.get(this);
        setAdapterBottomList();
        List<BadgeData> list;
        list=service.getJsonFromLocalyBadge();
        Iterator<BadgeData> it=list.iterator();
        List<GridFragment> gridFragmentList=new ArrayList<>();
        int i=0;
        //burada sayafada 1 page de kaç tane göstermek istiyorsan o kadar döngüye sokuyorum
        while (it.hasNext()){
            ArrayList<BadgeData> imlst=new ArrayList<>();
            for(int y=1;y<5;y++){
                if(it.hasNext()){
                    BadgeData badgeData=it.next();
                    BadgeData itm1=new BadgeData(badgeData.getId(),badgeData.getTitle());
                    imlst.add(itm1);
                    i=i+1;
                }
            }
            BadgeData[] gp={};
            BadgeData[] gridPage=imlst.toArray(gp);
            gridFragmentList.add(new GridFragment(gridPage,MainActivity.this));
        }
        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),gridFragmentList);
        awesomePager.setAdapter(pagerAdapter);
        mIndicator.setViewPager(awesomePager);
        badgeDataList=JsonService.get(this).getJsonFromLocalyBadge();
        adet.setText(JsonService.sizeGeneral+" adet");
        float rating=JsonService.ratingAvarageGeneral;
        String strDouble = String.format("%.2f", rating);
        ratingBar.setRating(rating);
        avarageText.setText(""+strDouble);
    }
    //alttaki liste için
    public void setAdapterBottomList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=JsonService.get(this).getJsonFileFromLocally();
        adapter=new ListAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }
    private class PagerAdapter extends FragmentPagerAdapter {

        private List<GridFragment> fragments;

        public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }


}
