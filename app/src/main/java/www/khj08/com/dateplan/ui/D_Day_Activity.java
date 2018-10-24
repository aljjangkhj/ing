package www.khj08.com.dateplan.ui;

//import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.common.log;
import www.khj08.com.dateplan.ui.list_adapter.D_Day_Adapter;
import www.khj08.com.dateplan.ui.list_adapter.D_Day_Item;
import www.khj08.com.dateplan.utils.Util;

public class D_Day_Activity extends BaseActivity {

    private ListView listView;
    private D_Day_Adapter listViewAdapter;
    public static boolean yearDay = true;
    private boolean changeDay = true;
    private D_Day_Adapter d_day_adapter = new D_Day_Adapter();
    private LinearLayout btn_main_menu;

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.yearBtn) {
//            yearDay = false;
//            changeDay = true;
//            d_day_adapter.listViewItemList.clear();
//            listViewAdapter.notifyDataSetChanged();
//            DdayActivity();
//
//        } else if (id == R.id.dayBtn) {
//            yearDay = false;
//            changeDay= false;
//            d_day_adapter.listViewItemList.clear();
//            listViewAdapter.notifyDataSetChanged();
//            DdayActivity();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.d_day_layout);
        init_autoscreen(1);
        listViewAdapter = new D_Day_Adapter(this);
        listView = (ListView) this.findViewById(R.id.dday_listview);
        btn_main_menu = (LinearLayout)findViewById(R.id.btn_main_menu);
        listView.setAdapter(listViewAdapter);
        d_day_adapter.listViewItemList.clear();
        listViewAdapter.notifyDataSetChanged();
        DdayActivity();

        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        DdayActivity();
    }

    public String getDate(int iDay) {
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();
        final String[] DAY_OF_WEEK = {"", "일", "월", "화", "수", "목", "금", "토"};
        temp.add(Calendar.DAY_OF_MONTH, iDay);
        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);
        sbDate.append(nYear + "년 ");
        if (nMonth < 10) {
            sbDate.append("0");
        }
        sbDate.append(nMonth + "월 ");
        if (nDay < 10) {
            sbDate.append("0");
        }
        sbDate.append(nDay + "일 ");
        sbDate.append(DAY_OF_WEEK[temp.get(Calendar.DAY_OF_WEEK)] + "요일");
        return sbDate.toString();
    }

    public void DdayActivity() {
        // 요일은 1부터 시작하기 때문에, DAY_OF_WEEK[0]은 비워두었다.
        final String[] DAY_OF_WEEK = {"", "일", "월", "화", "수", "목", "금", "토"};
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        // month의 경우 0부터 시작하기 때문에 8월인 경우, 7로 지정해야한다.
        // date1.set(2005, Calendar.AUGUST, 15);와 같이 할 수도 있다.
        date1.set(MySharedPreferencesManager.getYear(this), MySharedPreferencesManager.getMonth(this), MySharedPreferencesManager.getDay(this)); // 2005년 8월 15일로 날짜를 설정한다.
        int myYear01 = date1.get(Calendar.YEAR);
        int myMonth01 = date1.get(Calendar.MONTH);
        int myDay01 = date1.get(Calendar.DAY_OF_MONTH);
        int myYear02 = date2.get(Calendar.YEAR);
        int myMonth02 = date2.get(Calendar.MONTH);
        int myDay02 = date2.get(Calendar.DAY_OF_MONTH);
        date2.set(myYear02, myMonth02 + 1, myDay02);
        // 두 날짜간의 차이를 얻으려면, getTimeInMillis()를 이용해서
        // 천분의 일초 단위로 변환해야한다.
        long difference = (date2.getTimeInMillis() - date1.getTimeInMillis()) / 1000;

        Date mdate = new Date();

        // Toast.makeText(this, "그 날(date1)부터 지금(date2)까지 " + difference + "초가 지났습니다", Toast.LENGTH_SHORT).show();
        // 1일 = 24 * 60 * 60
        long count = difference / 86400;
        if (myMonth01 == 2) {
            count = count - 2;
        }
        int i = 1;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month + 1, day);
        long tday = calendar.getTimeInMillis() / 86400000;
        long a = MySharedPreferencesManager.getToday(this);
        long b = MySharedPreferencesManager.getLoveDay(this);
//        int mydday1 = 0;
//        int mydday2 = 0;
//        int mydday3 = 0;
        int HJYear = MySharedPreferencesManager.getYear(this);
        int HJMonth = MySharedPreferencesManager.getMonth(this);
        int HJDay = MySharedPreferencesManager.getDay(this);


        Date mdate1 = new Date();
        Date mdate3 = new Date();
        long tt = mdate1.getTime() / 86400000;
//        List<String> users = new ArrayList<>();

//        if (!changeDay) {
            for (int dayPat = 100; dayPat <= 18200; dayPat += 100) {
                String today = Util.yyyyMMdd();
                long saveDateDday = Util.diffOfDate(MySharedPreferencesManager.getLoveStartDay(mContext),today) + 1;
                listViewAdapter.addItem2(HJYear, HJMonth, HJDay + dayPat - 1, dayPat + " 일", getDate((int) (saveDateDday - (dayPat)) * -1), 0);
            }
//        } else {
            for (int yearPat = 1; yearPat <= 50; yearPat++) {

                mdate3.setYear(HJYear - 1900 + yearPat);
                mdate3.setMonth(HJMonth - 1);
                mdate3.setDate(HJDay);
                long tt3 = mdate3.getTime() / 86400000;
                calendar.set(year - yearPat, month + 1, day);
                tday = calendar.getTimeInMillis() / 86400000;
//            users.add(getDate((int)(tt-tt3)));
                listViewAdapter.addItem2(HJYear + yearPat, HJMonth, HJDay, yearPat + "주년", getDate((int) (tt - tt3) * -1), 0);
            }
//        }
        Collections.sort(listViewAdapter.listViewItemList, new Comparator<D_Day_Item>() {
            @Override
            public int compare(D_Day_Item o1, D_Day_Item o2) {
                if (o1.getDesc() < o2.getDesc()) {
                    return 1;
                } else if (o1.getDesc() > o2.getDesc()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dday, menu);
        return true;
    }
}
