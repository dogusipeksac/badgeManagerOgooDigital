package com.example.badgemanageogoodigital.ViewModal;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;


import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.Service.JsonService;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModal extends ViewModel {
    private MutableLiveData<List<Data>> mDataList;
    private MutableLiveData<List<BadgeData>> mBadgeDataListForSpinner;
    private MutableLiveData<List<BadgeData>> mBadgeDataList;
    private MutableLiveData<List<Data>> mDataComingBadgeTitle;

    private JsonService service;


    private Context context;

    public MutableLiveData<List<Data>> getDataListObserve(){
        return mDataList;
    }
    public MutableLiveData<List<BadgeData>> getBadgeDataListForSpinnerObserve(){
        return mBadgeDataListForSpinner; }

    public MutableLiveData<List<BadgeData>> getBadgeDataListObserve(){
        return mBadgeDataList;
    }

    public MutableLiveData<List<Data>> getDataComingBadgeTitleObserve(){
        return mDataComingBadgeTitle;
    }

    public void init(Context context){
        this.context=context;
        service=JsonService.get(context);
        mDataList=new MutableLiveData<>();
        mBadgeDataListForSpinner=new MutableLiveData<>();
        mBadgeDataList=new MutableLiveData<>();
        mDataComingBadgeTitle=new MutableLiveData<>();
    }

    public void getDataComingBadgeTitleList(String title){

        if(title.equalsIgnoreCase("Tüm Rozetler")){
            List<Data> dataList=mDataList.getValue();
            mDataComingBadgeTitle.setValue(dataList);
        }
        else{
            List<Data> newList;
            newList= service.getWithTitleList(title);
            mDataComingBadgeTitle.setValue(newList);
        }

    }


    public void getBadgeAddedDataListForSpinner(){

        List<BadgeData> dataList=mBadgeDataList.getValue();
        List<BadgeData> newList=new ArrayList<>();
        BadgeData dataForZero=new BadgeData();
        dataForZero.setBadgeTitle("Tüm Rozetler");
        dataForZero.setBadgeId(2);
        newList.add(0,dataForZero);
        assert dataList != null;
        newList.addAll(dataList);
        mBadgeDataListForSpinner.setValue(newList);
    }


    public void getDataList(){
        List<Data> dataList= service.getJsonFileFromLocallyData();
        if(dataList.size()>0){
            mDataList.setValue(dataList);
        }
        else{
            mDataList.setValue(null);
        }
    }
    public void getDataBadgeList(){
      List<BadgeData> dataList=service.getJsonFromLocalyBadge();
        if(dataList.size()>0){
            mBadgeDataList.setValue(dataList);
        }
        else{
            mBadgeDataList.setValue(null);
        }
    }








}
