package com.example.badgemanageogoodigital;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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

import com.example.badgemanageogoodigital.Adapter.ListAdapter;
import com.example.badgemanageogoodigital.Adapter.PagerAdapter;
import com.example.badgemanageogoodigital.Adapter.SpinnerAdapter;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.ViewModel.MainActivityViewModel;
import com.viewpagerindicator.PageIndicator;

import java.util.List;


public class MainActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    public  PageIndicator mIndicator;
    private ViewPager awesomePager;
    private PagerAdapter pagerAdapter;
    private SpinnerAdapter spinnerAdapter;
    private Button averageText;
    private TextView number;
    private RatingBar ratingBar;
    private Spinner spinner;
    boolean first=true;
    private MainActivityViewModel mActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initDataView();
        //badge için spinner
        spinnerGetList();
        //\\\\\\\\\\\\\\\\\\
        initRcyclerViewData();
        arrayWithFourGroup();


    }

    private void initRcyclerViewData() {
        //list-data için setlayout
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new ListAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void initDataView() {

        mActivityViewModel.getDataListObserve().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(@Nullable List<Data> data) {
                adapter.setListItems(data);
            }
        });
    }

    //4 lü şekilde listeyi getirme işlemini mvvm e taşıdım
    private void arrayWithFourGroup() {
        mActivityViewModel.getBadgeDataListObserve().observe(this, new Observer<List<BadgeData>>() {
            @Override
            public void onChanged(@Nullable List<BadgeData> badgeDatalist) {
                pagerAdapter=new PagerAdapter(getSupportFragmentManager(),mActivityViewModel.getFourBadgeList());
                awesomePager.setAdapter(pagerAdapter);
                mIndicator.setViewPager(awesomePager);
                int listDataSize=mActivityViewModel.dataSize();
                number.setText(listDataSize + " adet");
                float rating=mActivityViewModel.dataListAverage();
                String strDouble = String.format("%.1f", rating);
                ratingBar.setRating(rating);
                averageText.setText(""+strDouble);
            }
        });

    }

    //alttaki liste için
    //item selected için iki kere girmesi engelleniyor
    public void setAdapterBottomList(List<Data> listData){
        if(first){
            first=false;
        }
        else {
            adapter.setListItems(listData);
        }


    }


    public void spinnerGetList(){
        mActivityViewModel.getBadgeAddedDataListForSpinner();
        mActivityViewModel.getBadgeDataListForSpinnerObserve().observe(this, new Observer<List<BadgeData>>() {
            @Override
            public void onChanged(@Nullable List<BadgeData> badgeData) {
                spinnerAdapter=new SpinnerAdapter(MainActivity.this,badgeData);
                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    //item listener
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        BadgeData spinnerSelected=(BadgeData) parent.getItemAtPosition(position);
                        //çekilen itemlerin titlelarını yollayarak arama yapıyorum
                        mActivityViewModel.getDataComingBadgeTitleList(
                                spinnerSelected.getBadgeTitle());
                        mActivityViewModel.getDataComingBadgeTitleObserve().observe(MainActivity.this, new Observer<List<Data>>() {
                            @Override
                            public void onChanged(@Nullable List<Data> data) {
                                setAdapterBottomList(data);
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }




    private  void initView(){
        recyclerView=findViewById(R.id.list_data_recycler);
        averageText=findViewById(R.id.avarage_text);
        spinner=findViewById(R.id.spinner);
        awesomePager=(ViewPager) findViewById(R.id.view_pager);
        mIndicator=(PageIndicator) findViewById(R.id.pagerIndicator);
        number=findViewById(R.id.adet);
        ratingBar=findViewById(R.id.ratingBar3);
        mActivityViewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mActivityViewModel.init(this);
        //list-data ve badge-data-listi başta getiriyorum
        mActivityViewModel.getDataList();
        mActivityViewModel.getDataBadgeList();

    }


}
