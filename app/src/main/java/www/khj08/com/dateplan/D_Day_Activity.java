package www.khj08.com.dateplan;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class D_Day_Activity extends AppCompatActivity {

    private ListView listView;
    private D_Day_Adapter listViewAdapter;
    public static boolean yearDay = true;
    private boolean changeDay = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        D_Day_Adapter d_day_adapter = new D_Day_Adapter();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.yearBtn) {
            yearDay = false;
            changeDay = true;
            d_day_adapter.listViewItemList.clear();
            listViewAdapter.notifyDataSetChanged();
            DdayActivity();

        } else if (id == R.id.dayBtn) {
            yearDay = false;
            changeDay= false;
            d_day_adapter.listViewItemList.clear();
            listViewAdapter.notifyDataSetChanged();
            DdayActivity();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.d_day_layout);
        listViewAdapter = new D_Day_Adapter(this);
        listView = (ListView) this.findViewById(R.id.dday_listview);
        listView.setAdapter(listViewAdapter);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf");
        DdayActivity();
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
        Log.v("mylog", "getYear: " + MySharedPreferencesManager.getYear(this));
        Log.v("mylog", "getMonth: " + MySharedPreferencesManager.getMonth(this));
        Log.v("mylog", "getDay: " + MySharedPreferencesManager.getDay(this));
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
        Log.v("mylog", "오늘날짜시간: " + a);
        Log.v("mylog", "사용자 설정 날짜시간: " + b);
        if (a == tday) {
            Log.v("mylog", "d-day: " + (a - b));
        } else {
            Log.v("mylog", "dday:" + (tday - b));
        }
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

        if (!changeDay) {
            for (int dayPat = 100; dayPat <= 18200; dayPat += 100) {

                mdate3.setYear(HJYear - 1900);
                mdate3.setMonth(HJMonth - 1);
                mdate3.setDate(HJDay - dayPat);
                long tt3 = mdate3.getTime() / 86400000;
//            users.add(getDate((int)(tt-tt3)));
                listViewAdapter.addItem2(HJYear, HJMonth, HJDay + dayPat - 1, dayPat + " 일", getDate((int) (tday - b - (dayPat)) * -1), 0);
                Log.v("mylog","일");
            }
        } else {
            for (int yearPat = 1; yearPat <= 50; yearPat++) {

                mdate3.setYear(HJYear - 1900 + yearPat);
                mdate3.setMonth(HJMonth - 1);
                mdate3.setDate(HJDay);
                long tt3 = mdate3.getTime() / 86400000;
                calendar.set(year - yearPat, month + 1, day);
                tday = calendar.getTimeInMillis() / 86400000;
//            users.add(getDate((int)(tt-tt3)));
                listViewAdapter.addItem2(HJYear + yearPat, HJMonth, HJDay, yearPat + "주년", getDate((int) (tt - tt3) * -1), 0);
                Log.v("mylog","주년");
            }
        }
//        for(int mydday= 1; mydday <= 182; ++mydday){
//              listViewAdapter.addItem(mydday,(mydday3+=100),getDate((int)(tday - b - (mydday1 += 100)) *- 1),(tday - b) - (mydday2 += 100) + "");

          /*  switch (mydday){
                case 4:listViewAdapter.addItem(mydday,"1 주년",getDate((int)(tday - b - 366) *- 1),(tday - b) -  366 + "");break;
                case 8:listViewAdapter.addItem(mydday,"2 주년",getDate((int)(tday - b - 732) *- 1),(tday - b) - 732 + "");break;
                case 11:listViewAdapter.addItem(mydday,"3 주년",getDate((int)(tday - b - 1097) *- 1),(tday - b) - 1097 + "");break;
                case 15:listViewAdapter.addItem(mydday,"4 주년",getDate((int)(tday - b - 1462) *- 1),(tday - b) - 1462 + "");break;
                case 19:listViewAdapter.addItem(mydday,"5 주년",getDate((int)(tday - b - 1827) *- 1),(tday - b) - 1827 + "");break;
                case 22:listViewAdapter.addItem(mydday,"6 주년",getDate((int)(tday - b - 2193) *- 1),(tday - b) - 2193 + "");break;
                case 26:listViewAdapter.addItem(mydday,"7 주년",getDate((int)(tday - b - 2558) *- 1),(tday - b) - 2558 + "");break;
                case 30:listViewAdapter.addItem(mydday,"8 주년",getDate((int)(tday - b - 2923) *- 1),(tday - b) - 2923 + "");break;
                case 33:listViewAdapter.addItem(mydday,"9 주년",getDate((int)(tday - b - 3288) *- 1),(tday - b) - 3288 + "");break;
                case 37:listViewAdapter.addItem(mydday,"10 주년",getDate((int)(tday - b - 3654) *- 1),(tday - b) - 3654 + "");break;
                case 41:listViewAdapter.addItem(mydday,"11 주년",getDate((int)(tday - b - 4019) *- 1),(tday - b) - 4019 + "");break;
                case 45:listViewAdapter.addItem(mydday,"12 주년",getDate((int)(tday - b - 4384) *- 1),(tday - b) - 4384 + "");break;
                case 48:listViewAdapter.addItem(mydday,"13 주년",getDate((int)(tday - b - 4749) *- 1),(tday - b) - 4749 + "");break;
                case 52:listViewAdapter.addItem(mydday,"14 주년",getDate((int)(tday - b - 5115) *- 1),(tday - b) - 5115 + "");break;
                case 56:listViewAdapter.addItem(mydday,"15 주년",getDate((int)(tday - b - 5480) *- 1),(tday - b) - 5480 + "");break;
                case 59:listViewAdapter.addItem(mydday,"16 주년",getDate((int)(tday - b - 5845) *- 1),(tday - b) - 5845 + "");break;
                case 63:listViewAdapter.addItem(mydday,"17 주년",getDate((int)(tday - b - 6210) *- 1),(tday - b) - 6210 + "");break;
                case 67:listViewAdapter.addItem(mydday,"18 주년",getDate((int)(tday - b - 6576) *- 1),(tday - b) - 6576 + "");break;
                case 70:listViewAdapter.addItem(mydday,"19 주년",getDate((int)(tday - b - 6941) *- 1),(tday - b) - 6941 + "");break;
                case 74:listViewAdapter.addItem(mydday,"20 주년",getDate((int)(tday - b - 7306) *- 1),(tday - b) - 7306 + "");break;
                case 77:listViewAdapter.addItem(mydday,"21 주년",getDate((int)(tday - b - 7671) *- 1),(tday - b) - 7671 + "");break;
                case 81:listViewAdapter.addItem(mydday,"22 주년",getDate((int)(tday - b - 8037) *- 1),(tday - b) - 8037 + "");break;
                case 85:listViewAdapter.addItem(mydday,"23 주년",getDate((int)(tday - b - 8402) *- 1),(tday - b) - 8402 + "");break;
                case 88:listViewAdapter.addItem(mydday,"24 주년",getDate((int)(tday - b - 8767) *- 1),(tday - b) - 8767 + "");break;
                case 92:listViewAdapter.addItem(mydday,"25 주년",getDate((int)(tday - b - 9132) *- 1),(tday - b) - 9132 + "");break;
                case 96:listViewAdapter.addItem(mydday,"26 주년",getDate((int)(tday - b - 9498) *- 1),(tday - b) - 9498 + "");break;
                case 99:listViewAdapter.addItem(mydday,"27 주년",getDate((int)(tday - b - 9863) *- 1),(tday - b) - 9863 + "");break;
                case 103:listViewAdapter.addItem(mydday,"28 주년",getDate((int)(tday - b - 10228) *- 1),(tday - b) - 10228 + "");break;
                case 107:listViewAdapter.addItem(mydday,"29 주년",getDate((int)(tday - b - 10594) *- 1),(tday - b) - 10594 + "");break;
                case 110:listViewAdapter.addItem(mydday,"30 주년",getDate((int)(tday - b - 10959) *- 1),(tday - b) - 10959 + "");break;
                case 114:listViewAdapter.addItem(mydday,"31 주년",getDate((int)(tday - b - 11324) *- 1),(tday - b) - 11324 + "");break;
                case 118:listViewAdapter.addItem(mydday,"32 주년",getDate((int)(tday - b - 11689) *- 1),(tday - b) - 11689 + "");break;
                case 121:listViewAdapter.addItem(mydday,"33 주년",getDate((int)(tday - b - 12054) *- 1),(tday - b) - 12054 + "");break;
                case 125:listViewAdapter.addItem(mydday,"34 주년",getDate((int)(tday - b - 12420) *- 1),(tday - b) - 12420 + "");break;
                case 129:listViewAdapter.addItem(mydday,"35 주년",getDate((int)(tday - b - 12785) *- 1),(tday - b) - 12785 + "");break;
                case 132:listViewAdapter.addItem(mydday,"36 주년",getDate((int)(tday - b - 13150) *- 1),(tday - b) - 13150 + "");break;
                case 136:listViewAdapter.addItem(mydday,"37 주년",getDate((int)(tday - b - 13515) *- 1),(tday - b) - 13515 + "");break;
                case 140:listViewAdapter.addItem(mydday,"38 주년",getDate((int)(tday - b - 13881) *- 1),(tday - b) - 13881 + "");break;
                case 143:listViewAdapter.addItem(mydday,"39 주년",getDate((int)(tday - b - 14246) *- 1),(tday - b) - 14246 + "");break;
                case 147:listViewAdapter.addItem(mydday,"40 주년",getDate((int)(tday - b - 14611) *- 1),(tday - b) - 14611 + "");break;
                case 150:listViewAdapter.addItem(mydday,"41 주년",getDate((int)(tday - b - 14976) *- 1),(tday - b) - 14976 + "");break;
                case 154:listViewAdapter.addItem(mydday,"42 주년",getDate((int)(tday - b - 15342) *- 1),(tday - b) - 15342 + "");break;
                case 158:listViewAdapter.addItem(mydday,"43 주년",getDate((int)(tday - b - 15707) *- 1),(tday - b) - 15707 + "");break;
                case 161:listViewAdapter.addItem(mydday,"44 주년",getDate((int)(tday - b - 16072) *- 1),(tday - b) - 16072 + "");break;
                case 165:listViewAdapter.addItem(mydday,"45 주년",getDate((int)(tday - b - 16437) *- 1),(tday - b) - 16437 + "");break;
                case 169:listViewAdapter.addItem(mydday,"46 주년",getDate((int)(tday - b - 16803) *- 1),(tday - b) - 16803 + "");break;
                case 172:listViewAdapter.addItem(mydday,"47 주년",getDate((int)(tday - b - 17168) *- 1),(tday - b) - 17168 + "");break;
                case 176:listViewAdapter.addItem(mydday,"48 주년",getDate((int)(tday - b - 17533) *- 1),(tday - b) - 17533 + "");break;
                case 179:listViewAdapter.addItem(mydday,"49 주년",getDate((int)(tday - b - 17898) *- 1),(tday - b) - 17898 + "");break;
                case 182:listViewAdapter.addItem(mydday,"50 주년",getDate((int)(tday - b - 18264) *- 1),(tday - b) - 18264 + "");break;
            }*/
//        }
        //listViewAdapter.addItem(mydday,mydday2 + " 일",getDate((int)(tday - b - (mydday3 += 365)) *- 1),(tday - b) - (mydday4+= 365) + "");
        /*int dday_manager = D_Day_Adapter.integerdday.length;
        for (i = 100; i <= dday_manager; i += 100) {
            long resultCount = (tday - b) - i;
            long ddayresult = resultCount * -1;
            if (resultCount > 0) {
                listViewAdapter.addItem(i, i + " 일", getDate((int) ddayresult),  resultCount+"");//"D+" +
            } else {
                listViewAdapter.addItem(i, i + " 일", getDate((int) ddayresult), resultCount+"");// "D" +
            }
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dday, menu);
        return true;
    }
}
