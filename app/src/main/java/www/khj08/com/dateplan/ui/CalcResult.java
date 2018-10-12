package www.khj08.com.dateplan.ui;

import android.database.Cursor;
//import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.list_adapter.CalcResultAdapter;

public class CalcResult extends BaseActivity {

    private SQLiteDBManager mSQLiteDBManager = null;
    private ListView listview01;
    private CalcResultAdapter listViewAdapter;

    private TextView font1;
    private TextView font2;
    private LinearLayout btn_main_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_calc_result01);
        init_autoscreen(1);
        listViewAdapter = new CalcResultAdapter();
        listview01 = (ListView) this.findViewById(R.id.content_calc_listview);
        listview01.setAdapter(listViewAdapter);
        font1 = (TextView)this.findViewById(R.id.fonts1);
        font2 = (TextView)this.findViewById(R.id.fonts2);
        btn_main_menu = (LinearLayout)findViewById(R.id.btn_main_menu);

        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");
//        font1.setTypeface(typeface);
//        font2.setTypeface(typeface);
        mSQLiteDBManager = SQLiteDBManager.getInstance(this);
        String[] columns = new String[]{"_id", "date", "starttime", "endtime", "title", "content", "resulthour", "manmoney", "womanmoney", "resultmoney"};
        Cursor c = mSQLiteDBManager.query(columns, null, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String mRefDate = c.getString(1);
                String mRefstart = c.getString(2);
                String mRefEnd = c.getString(3);
                String mRefTitle = c.getString(4);
                String mRefContent = c.getString(5);
                String mRefResulthour = c.getString(6);
                String mRefManmoney = c.getString(7);
                String mRefWomanmoney = c.getString(8);
                String mRefresultmoeny = c.getString(9);
                NumberFormat nf = NumberFormat.getNumberInstance();
                String numberstr;
                int numberResult = Integer.parseInt(mRefresultmoeny);
                numberstr = nf.format(numberResult);
                int ManMoney = Integer.parseInt(mRefManmoney);
                String ManMoneyStr = nf.format(ManMoney);
                int WomanMoney = Integer.parseInt(mRefWomanmoney);
                String WomanMoneyStr = nf.format(WomanMoney);

                listViewAdapter.addItem(mRefDate, ManMoneyStr, WomanMoneyStr, numberstr);
            }
            c.close();
        } else {
            Toast.makeText(this, "일기를 쓰지않아 가계부가 등록이 되어있지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
