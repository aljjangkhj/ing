package www.khj08.com.dateplan;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017-07-10.
 */

public class D_Day_Adapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<D_Day_Item> listViewItemList = new ArrayList<D_Day_Item>() ;


    private static TextView ddayTextView;
    private static TextView titleTextView;
    private static TextView descTextView;
    private Context mRefContext;

    // ListViewAdapter의 생성자
    public D_Day_Adapter() {

    }

    public D_Day_Adapter(Context RefContext) {
        this.mRefContext = RefContext;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String [] DdayMap = new String[listViewItemList.size()];
        for(int i = 0; i < listViewItemList.size(); i++){
            DdayMap[i] = listViewItemList.get(i).toString();
        }
        Arrays.sort(DdayMap);

        final int pos = position;
        final Context context = parent.getContext();
        //Log.v("mylog","position: "+pos);
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.d_day_layout02, parent, false);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ddayTextView = (TextView) convertView.findViewById(R.id.dday_text01) ;
        titleTextView = (TextView) convertView.findViewById(R.id.dday_text02) ;
        descTextView = (TextView) convertView.findViewById(R.id.dday_text03) ;

       // int DdayPreferences01 = MySharedPreferencesManager.getDdaySaveint(mRefContext);
       // Log.v("mylog","Ddaypreferences01: "+ DdayPreferences01);

       /* for(int i = 100; i <= 18200; i += 100 ) {
            int resultCount = DdayPreferences01 - i;
            Log.v("mylog", "resultCount: " + resultCount);
            Log.v("mylog", "integerdday[i]: " + i);

            if(resultCount > 0){
                // this.addItem(i, i + " 일", "", "D+"+resultCount);
                ddayTextView.setTextColor(Color.parseColor("#D5D5D5"));
                titleTextView.setTextColor(Color.parseColor("#D5D5D5"));
                descTextView.setTextColor(Color.parseColor("#D5D5D5"));
            }
            else {
            *//*ddayTextView.setTextColor(Color.parseColor("#000"));
            titleTextView.setTextColor(Color.parseColor("#D5D5D5"));
            descTextView.setTextColor(Color.parseColor("#000"));*//*
            }
        }*/
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        D_Day_Item listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        ddayTextView.setText(listViewItem.getDday());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(String.valueOf(listViewItem.getDesc()));
        String ddaylist = descTextView.getText().toString();
        try {
            int intlist = Integer.parseInt(ddaylist);
            //Log.v("mylog","intlist: "+intlist);
            if (intlist > 0){
                descTextView.setTextColor(Color.parseColor("#EAEAEA"));
                ddayTextView.setTextColor(Color.parseColor("#EAEAEA"));
                titleTextView.setTextColor(Color.parseColor("#EAEAEA"));
                descTextView.setText("D+"+ddaylist);
            }
            else if(intlist == 0){
                ddayTextView.setTextColor(Color.parseColor("#FFB2D9"));
                titleTextView.setTextColor(Color.parseColor("#FFB2D9"));
                descTextView.setTextColor(Color.RED);
                descTextView.setText("♥D-DAY♥");
            }
            else{
                descTextView.setTextColor(Color.BLACK);
                descTextView.setText("D"+ddaylist);
                ddayTextView.setTextColor(Color.BLACK);
                titleTextView.setTextColor(Color.BLACK);
            }
        } catch(NumberFormatException nfe) {
            Log.v("mylog","Could not parse " + nfe);
        }
        /*int resultCount = DdayPreferences01 - integerdday[position];

            if (resultCount > 0) {
                ddayTextView.setTextColor(Color.BLUE);
                titleTextView.setTextColor(Color.GRAY);
                descTextView.setTextColor(Color.GREEN);
            } else {
           *//*ddayTextView.setTextColor(Color.parseColor("#000"));
            titleTextView.setTextColor(Color.parseColor("#D5D5D5"));
            descTextView.setTextColor(Color.parseColor("#000"));*//*

        }*/
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int no, String dday, String title, long desc) {
        D_Day_Item item = new D_Day_Item();

        item.setDday(dday);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    public void addItem2(int year, int month, int day, String dday, String title, long desc) {
        D_Day_Item item = new D_Day_Item();

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year,month,day);
//        long mdday = calendar.getTimeInMillis() / 86400000;
        Date mdate = new Date();
        long tt = mdate.getTime() / 86400000;
        Date mdate2 = new Date();
        mdate2.setYear(year - 1900);
        mdate2.setMonth(month - 1);
        mdate2.setDate(day);
        long tt2 = mdate2.getTime() / 86400000;

        desc = tt - tt2;
        item.setYEAR(year);
        item.setMONTH(month);
        item.setDAY(day);
        item.setDday(dday);
        item.setTitle(title);
        item.setDesc(desc);
//        if(!D_Day_Activity.yearDay){
//            listViewItemList.clear();
//        }
        listViewItemList.add(item);

    }
}
