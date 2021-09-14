package com.example.badgemanageogoodigital.ViewModel;


import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;


import com.example.badgemanageogoodigital.Fragment.GridFragment;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.Service.JsonService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
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
    //spinner için title ile data-list getirme
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

    //elimdeki badge-list çekiyorum
    //daha sonra spinner için yeniden
    // modify ediyorum en başına tüm rozetler ekliyorum
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

    //butun data-listi getirme
    public void getDataList(){
        List<Data> dataList= service.getJsonFileFromLocallyData();
        if(dataList.size()>0){
            mDataList.setValue(dataList);
        }
        else{
            mDataList.setValue(null);
        }
    }
    //butun badge-list getirme
    public void getDataBadgeList(){
      List<BadgeData> dataList=service.getJsonFromLocalyBadge();
        if(dataList.size()>0){
            mBadgeDataList.setValue(dataList);
        }
        else{
            mBadgeDataList.setValue(null);
        }
    }
    //4lü sınıflamak için listeyi yeniden düzenliyorum
    public List<GridFragment> getFourBadgeList(){
        List<BadgeData> dataList=mBadgeDataList.getValue();
        Iterator<BadgeData> it=dataList.iterator();
        List<GridFragment> gridFragmentList=new ArrayList<>();
        int i=0;
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
            gridFragmentList.add(new GridFragment(gridPage, (Activity) context));
        }
        return  gridFragmentList;
    }
    //butun data-list listenin uzunluğu
    public int dataSize(){
        int mDataListSize=service.sizeGeneral;

        return mDataListSize;
    }
    //butun liste data-list için average hesaplama
    public float dataListAverage(){
        float mDataListAverage=JsonService.ratingAverageGeneral;
        return mDataListAverage;
    }


}
