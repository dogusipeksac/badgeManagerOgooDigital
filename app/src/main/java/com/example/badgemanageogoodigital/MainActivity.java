package com.example.badgemanageogoodigital;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badgemanageogoodigital.Adapter.ListAdapter;
import com.example.badgemanageogoodigital.Adapter.SpinnerAdapter;
import com.example.badgemanageogoodigital.Fragment.GridFragment;
import com.example.badgemanageogoodigital.Model.Author;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.Model.RelatedPerson;
import com.example.badgemanageogoodigital.Service.JsonService;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private List<Data> list;
    public PageIndicator mIndicator;
    private ViewPager awesomePager;
    private PagerAdapter pagerAdapter;
    private SpinnerAdapter spinnerAdapter;
    private JsonService service;

    private Button avarageText;
    private TextView adet;
    private List<BadgeData> listBadge;
    private RatingBar ratingBar;

    private Spinner spinner;
    boolean first=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        service=JsonService.get(getApplicationContext());
        list=JsonService.get(getApplicationContext()).getJsonFileFromLocallyData();

        //badge için
        List<BadgeData> newList=new ArrayList<>();
        BadgeData dataForZero=new BadgeData();
        dataForZero.setTitle("Tüm Rozetler");
        dataForZero.setBadgeId(2);
        newList.add(0,dataForZero);
        listBadge=service.getJsonFromLocalyBadge();
        newList.addAll(listBadge);
        //\\\\\\\\\\\\\\\\\\
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new ListAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);


        spinnerAdapter=new SpinnerAdapter(getApplicationContext(),newList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BadgeData spinnerSelected=(BadgeData) parent.getItemAtPosition(position);
                setAdapterBottomList(service
                        .getWithTitleList(spinnerSelected.getBadgeTitle()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayWithFourGroup();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void arrayWithFourGroup() {
        Iterator<BadgeData> it=listBadge.iterator();
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
            //burada çekilen verileri diziye atıyoruz
            BadgeData[] gp={};
            BadgeData[] gridPage=imlst.toArray(gp);
            //ve 4 er diziler şeklinde listeye ekliyoruz
            gridFragmentList.add(new GridFragment(gridPage,MainActivity.this));
        }
        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),gridFragmentList);
        awesomePager.setAdapter(pagerAdapter);
        mIndicator.setViewPager(awesomePager);
        adet.setText(JsonService.sizeGeneral+" adet");
        float rating=JsonService.ratingAvarageGeneral;
        String strDouble = String.format("%.1f", rating);
        ratingBar.setRating(rating);
        avarageText.setText(""+strDouble);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //alttaki liste için
    public void setAdapterBottomList(List<Data> listData){
        if(first){
            first=false;

        }
        else {
            adapter.setListItems(listData);
            adapter.notifyDataSetChanged();
            System.out.println("priny");
        }


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

    private  void initView(){
        recyclerView=findViewById(R.id.list_data_recycler);
        //layout=findViewById(R.id.dots_container);
        avarageText=findViewById(R.id.avarage_text);
        spinner=findViewById(R.id.spinner);
        awesomePager=(ViewPager) findViewById(R.id.view_pager);
        mIndicator=(PageIndicator) findViewById(R.id.pagerIndicator);
        adet=findViewById(R.id.adet);
        ratingBar=findViewById(R.id.ratingBar3);
    }
}
