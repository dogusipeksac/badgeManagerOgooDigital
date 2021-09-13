package com.example.badgemanageogoodigital;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.badgemanageogoodigital.Adapter.BadgeGridAdapter;
import com.example.badgemanageogoodigital.Adapter.ListAdapter;
import com.example.badgemanageogoodigital.Adapter.PagerAdapter;
import com.example.badgemanageogoodigital.Adapter.SpinnerAdapter;
import com.example.badgemanageogoodigital.Fragment.GridFragment;
import com.example.badgemanageogoodigital.Model.Author;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.Model.RelatedPerson;
import com.example.badgemanageogoodigital.Service.JsonService;
import com.example.badgemanageogoodigital.ViewModal.MainActivityViewModal;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    public  PageIndicator mIndicator;
    private ViewPager awesomePager;
    private PagerAdapter pagerAdapter;
    private SpinnerAdapter spinnerAdapter;

    private MainActivityViewModal mActivityViewModal;
    private Button avarageText;
    private TextView adet;

    private RatingBar ratingBar;
    private Spinner spinner;
    boolean first=true;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new ListAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void initDataView() {
        mActivityViewModal.getDataListObserve().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(@Nullable List<Data> data) {
                adapter.setListItems(data);
            }
        });
    }

    //4 lü şekilde listeyi getirme işlemini mvvm e taşıdım
    private void arrayWithFourGroup() {

        mActivityViewModal.getBadgeDataListObserve().observe(this, new Observer<List<BadgeData>>() {
            @Override
            public void onChanged(@Nullable List<BadgeData> badgeDatalist) {
                Iterator<BadgeData> it=badgeDatalist.iterator();
                List<GridFragment> gridFragmentList=new ArrayList<>();
                int i=0;
                //burada sayafada 1 page de kaç tane göstermek istiyorsan o kadar döngüye sokuyorum
                while (it.hasNext()){
                    ArrayList<BadgeData> imlst=new ArrayList<>();
                    for(int y=1;y<5;y++){
                        if(it.hasNext()){
                            BadgeData badgeData=it.next();
                            BadgeData itm1=new BadgeData(badgeData.getId(),badgeData.getBadgeTitle());
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
        });

    }

    //alttaki liste için
    public void setAdapterBottomList(List<Data> listData){
        if(first){
            first=false;
        }
        else {
            adapter.setListItems(listData);
        }


    }


    public void spinnerGetList(){
        mActivityViewModal.getBadgeAddedDataListForSpinner();
        mActivityViewModal.getBadgeDataListForSpinnerObserve().observe(this, new Observer<List<BadgeData>>() {
            @Override
            public void onChanged(@Nullable List<BadgeData> badgeData) {
                spinnerAdapter=new SpinnerAdapter(MainActivity.this,badgeData);
                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        BadgeData spinnerSelected=(BadgeData) parent.getItemAtPosition(position);
                        mActivityViewModal.getDataComingBadgeTitleList(
                                spinnerSelected.getBadgeTitle());
                        mActivityViewModal.getDataComingBadgeTitleObserve().observe(MainActivity.this, new Observer<List<Data>>() {
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
        avarageText=findViewById(R.id.avarage_text);
        spinner=findViewById(R.id.spinner);
        awesomePager=(ViewPager) findViewById(R.id.view_pager);
        mIndicator=(PageIndicator) findViewById(R.id.pagerIndicator);
        adet=findViewById(R.id.adet);
        ratingBar=findViewById(R.id.ratingBar3);
        mActivityViewModal=ViewModelProviders.of(this).get(MainActivityViewModal.class);
        mActivityViewModal.init(this);
        mActivityViewModal.getDataList();
        mActivityViewModal.getDataBadgeList();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
