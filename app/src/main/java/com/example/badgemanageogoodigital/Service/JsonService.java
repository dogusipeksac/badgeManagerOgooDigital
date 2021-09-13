package com.example.badgemanageogoodigital.Service;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.badgemanageogoodigital.Model.Author;
import com.example.badgemanageogoodigital.Model.BadgeData;
import com.example.badgemanageogoodigital.Model.Data;
import com.example.badgemanageogoodigital.Model.RelatedPerson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JsonService {

    private static Context context;
    List<Data> list;
    List<BadgeData> badgeList;
    private static JsonService jsonService;
    public static float ratingAverageGeneral;
    public static int sizeGeneral;
    public static Map<Integer, Bitmap> mapImages;

    //singleton
    public static JsonService get(Context context){
        if (jsonService==null){
            jsonService=new JsonService(context);
        }
        return jsonService;
    }

    private JsonService(Context context) {
        this.context = context;
        list=new ArrayList<>();
        badgeList=new ArrayList<>();
        mapImages=new HashMap<>();
    }
    //local json için yol
    public static String loadJSONFromAsset(String path) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    //list-datanın verilerini localden getirme
    public List<Data> getJsonFileFromLocallyData() {
        try {
            JSONObject obj =(JSONObject) new JSONObject(loadJSONFromAsset("list-data.json"));
            JSONArray jsonArrayList= obj.getJSONArray("Row");
            int ratingSum=0;
            ratingAverageGeneral=0;
            for(int i=0;i<jsonArrayList.length();i++){
                Data item=new Data(new BadgeData(),new Author(),new RelatedPerson());
                JSONObject object=jsonArrayList.getJSONObject(i);
                String relatedPersonTitle="";
                JSONArray objectArrayRelated=object.getJSONArray("RelatedPerson");
                JSONObject objectRelated= objectArrayRelated.getJSONObject(0);
                relatedPersonTitle=objectRelated.getString("title");
                String badgelookupValue="";
                int idForBadge=0;
                JSONArray objectArrayBadge=object.getJSONArray("Badge");
                JSONObject objectBadge= objectArrayBadge.getJSONObject(0);
                badgelookupValue=objectBadge.getString("lookupValue");
                idForBadge=objectBadge.getInt("lookupId");
                //bu kullanılmıyor şu an
                JSONArray objectArrayAuthor=object.getJSONArray("Author");
                //
                String cratedDate="";
                cratedDate=object.getString("Created.");
                cratedDate=converterDate(cratedDate);
                String message="";
                message=object.getString("Message");
                int ratingScrore=0;
                ratingScrore=object.getInt("PraiseRating");
                ratingSum=ratingSum+ratingScrore;
                item.getRelated_person().setTitle(relatedPersonTitle);
                item.setCreted_date(cratedDate);
                item.setMessage(message);
                item.getBadgeData().setBadgeTitle(badgelookupValue);
                item.setPraiseRating(ratingScrore);
                item.getBadgeData().setId(idForBadge);
                list.add(item);
            }
            ratingAverage(ratingSum,jsonArrayList.length());
            sizeGeneral=list.size();
            // for
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }




    //badge-data verilerini localden getirme
    public List<BadgeData> getJsonFromLocalyBadge(){

        try {
            JSONObject obj =(JSONObject) new JSONObject(loadJSONFromAsset("badge-data.json"));
            JSONArray jsonArrayList= obj.getJSONArray("value");
            for (int i=0;i<jsonArrayList.length();i++){

                JSONObject object=jsonArrayList.getJSONObject(i);
                String title="";
                title=object.getString("Title");
                int id=0;
                id=object.getInt("Id");
                //list-data da olmayan bir rozeti getirmemesi için
                //yapılan kontrol
                if(calculateSize(id)!=0){
                    BadgeData item=new BadgeData();
                    addImages(id);
                    item.setId(id);
                    item.setBadgeTitle(title);
                    badgeList.add(item);

                }

            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("kaç kere girildi");
     return badgeList;
    }

    //resimleri yüklemek için
    private void addImages(int key) throws IOException {
        InputStream ims = null;
        try {
            ims = context.getAssets().open("resource/image"+key+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  Drawable dan image çekmek
        Bitmap bitmap = BitmapFactory.decodeStream(ims);
        assert ims != null;
        ims.close();

       mapImages.put(key,bitmap);


    }


    //hangi rozetten kaç tane var onu bulan fonksiyon
    //id sine göre sayıyorum
    public int calculateSize(int id){
        int size=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBadgeData().getId()==id){
                size++;
            }
        }
        return size;
    }

    //rozetlerin sayısını ratinge bölen fonksiyon average hesaplıyor
    public float calculateAverage(int id){
        float avarage=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getBadgeData().getId()==id){
                avarage=avarage+list.get(i).getPraiseRating();
            }
        }
        return avarage/calculateSize(id);
    }
    //butun rozetlerin toplamını alıp listenin sayısına bölen fonsiyon
    public float ratingAverage(int ratingSum,int size){

        ratingAverageGeneral=(float)ratingSum/(float) size;

         return ratingAverageGeneral;
    }
    //istenilen zamana çeviriyoruz
    public String converterDate(String newDate){
        String string =newDate;
        //2021-08-06T20:26:56Z
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");
        try {
            Date date = format.parse(string);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM HH:mm",
                    new Locale("tr"));
            formatter.format(date);
            return formatter.format(date);
        }
        catch(ParseException pe) {

            return null;

        }
    }

    //spinner nesnesindeki title göre getiriyor
    public List<Data> getWithTitleList(String title){

        List<Data> getSelectedForItemList=new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(title.equalsIgnoreCase(list.get(i).getBadgeData().getBadgeTitle())){
                    getSelectedForItemList.add(list.get(i));
                }
        }
        return getSelectedForItemList;
    }

}
