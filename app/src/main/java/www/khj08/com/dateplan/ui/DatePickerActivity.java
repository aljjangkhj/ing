package www.khj08.com.dateplan.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.list_adapter.SQLiteDBListView;

public class DatePickerActivity extends BaseActivity {
    //날짜 정보를 가져올때 사용할 변수 선언: 달력 변수
    private Calendar calendar;
    private Uri mImageCaptureUri;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String result="";

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;

    private Bitmap myBitmap;
    //날짜 정보를 저장할 전역변수 선언: 년도 / 월 / 일
    private int year;
    private int month;
    private int day;
    private int ihour;
    private int iminute;
    private int ihour2;
    private int iminute2;
    private int resultHour;
    private int resultMinute;
    private int iaddmoney;
    private int FinalresultTime;

    private String img01;
    private String DateValue;
    private String TimeValue01;
    private String TimeValue02;
    private String TitleValue;
    private String ContentValue;
    private String strManMoney;
    private String strWomanMoney;
    private String numberstr;
    private String strResulthour;

    //화면에 날짜 창을 출력할 때 사용할 DatepickerDialog 클래스를 사용하는 변수선언
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog01;
    private TimePickerDialog timePickerDialog02;

    //화면에 날짜(연도, 월, 일)을 보여주는 텍스트 뷰의 주소 보관 변수 선언
    private TextView datePickerTxt;
    private TextView timeHelloText;
    private TextView timeByeText;
    private TextView resultHour01;
    private TextView addMoney;
    private TextView starttimeText;
    private TextView endtimeText;
    private TextView resetTimeText;

    private EditText editText_title;
    private EditText editText_content;
    private EditText manMoney;
    private EditText womanMoney;

    //화면에 날짜 선택 또는 변경 창을 출력해주는 버튼의 주소 보관 변수 선언
    private LinearLayout datePickerBtn;
    private Button save_Btn;
//    private Button timeResetBtn;
    private LinearLayout timeHelloBtn;
    private LinearLayout timeByeBtn;
    private Button list_Btn;
//    private Button addmoneyBtn;

    private ImageButton imageButton;
    private ImageView imageView;

    private ListView dateListView;
    private SQLiteDBListView listViewAdapter;

    private TextView firstName,secondName;

    //데이터베이스 함수
    public SQLiteDBManager mSQLiteDBManager = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.date_picker_layout2);
        init_autoscreen(1);
        listViewAdapter = new SQLiteDBListView();

        Log.v("mylog", "SQLiteDBListview");

        //데이터베이스 테이블 생성 SQL문
        mSQLiteDBManager = SQLiteDBManager.getInstance(this);

        Log.v("mylog", "SQLite023456");

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        this.imageButton = (ImageButton) this.findViewById(R.id.dateplan_imagebtn);
        this.imageView = (ImageView) this.findViewById(R.id.dateplan_imageView);
        this.datePickerBtn = (LinearLayout) this.findViewById(R.id.date_picker_btn);
        this.timeHelloBtn = (LinearLayout) this.findViewById(R.id.time_Hello_btn);
        this.timeByeBtn = (LinearLayout) this.findViewById(R.id.time_Bye_btn);
//        this.timeResetBtn = (Button) this.findViewById(R.id.time_reset_btn);
        // this.save_Btn = (Button) this.findViewById(R.id.save_diaryBtn);
        // this.list_Btn = (Button) this.findViewById(R.id.diary_list);
//        this.addmoneyBtn = (Button) this.findViewById(R.id.addmoneybtn);
        this.datePickerTxt = (TextView) this.findViewById(R.id.date_picker_text);
        this.timeHelloText = (TextView) this.findViewById(R.id.hello_time_text);
        this.timeByeText = (TextView) this.findViewById(R.id.bye_time_text);
        this.resultHour01 = (TextView) this.findViewById(R.id.resulthour);
        this.addMoney = (TextView) this.findViewById(R.id.add_money);
        this.starttimeText = (TextView) this.findViewById(R.id.starttimeText);
        this.endtimeText = (TextView) this.findViewById(R.id.endtimeText);
        this.resetTimeText = (TextView) this.findViewById(R.id.timeresetText);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");
//        this.datePickerTxt.setTypeface(typeface);
//        this.timeHelloText.setTypeface(typeface);
//        this.timeByeText.setTypeface(typeface);
//        this.resultHour01.setTypeface(typeface);
//        this.addMoney.setTypeface(typeface);
//        this.starttimeText.setTypeface(typeface);
//        this.endtimeText.setTypeface(typeface);
//        this.resetTimeText.setTypeface(typeface);
//        TextView font01 = (TextView) this.findViewById(R.id.font01);
//        TextView font02 = (TextView) this.findViewById(R.id.font02);
//        TextView font03 = (TextView) this.findViewById(R.id.font03);
//        TextView font04 = (TextView) this.findViewById(R.id.font04);
//        TextView font05 = (TextView) this.findViewById(R.id.font05);
//        TextView font06 = (TextView) this.findViewById(R.id.font06);
//        TextView font07 = (TextView) this.findViewById(R.id.font07);
//        TextView font08 = (TextView) this.findViewById(R.id.font08);
//        font01.setTypeface(typeface);
//        font02.setTypeface(typeface);
//        font03.setTypeface(typeface);
//        font04.setTypeface(typeface);
//        font05.setTypeface(typeface);
//        font06.setTypeface(typeface);
//        font07.setTypeface(typeface);
//        font08.setTypeface(typeface);
        // this.editText_title.setTypeface(typeface);
        //  this.editText_content.setTypeface(typeface);
        //  this.manMoney.setTypeface(typeface);
        //  this.womanMoney.setTypeface(typeface);

        this.editText_title = (EditText) this.findViewById(R.id.edit_text_title);
        this.editText_content = (EditText) this.findViewById(R.id.edit_text_content);
        this.manMoney = (EditText) this.findViewById(R.id.man_money);
        this.womanMoney = (EditText) this.findViewById(R.id.woman_money);
        firstName = (TextView)findViewById(R.id.first_name_datepicker);
        secondName = (TextView)findViewById(R.id.second_name_datepicker);
        firstName.setText(MySharedPreferencesManager.getPic01(mContext)+"의 지출");
        secondName.setText(MySharedPreferencesManager.getPic02(mContext)+"의 지출");

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)){
                    result = decimalFormat.format(Double.parseDouble(s.toString().replaceAll(",","")));
                    manMoney.setText(result);
                    manMoney.setSelection(manMoney.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        TextWatcher watcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)){
                    result = decimalFormat.format(Double.parseDouble(s.toString().replaceAll(",","")));
                    womanMoney.setText(result);
                    womanMoney.setSelection(womanMoney.getText().length());
                    int imanMoney = Integer.parseInt(manMoney.getText().toString().replaceAll(",",""));
                    int iwomanMoney = Integer.parseInt(womanMoney.getText().toString().replaceAll(",",""));
                    int iiaddmoney = imanMoney + iwomanMoney;
                    String sAddMoney = String.valueOf(imanMoney + iwomanMoney);
                    if (iiaddmoney >= 100000) {
                        addMoney.setText(sAddMoney);
                        addMoney.setTextColor(Color.RED);
                    }else if (iiaddmoney <= 50000){
                        addMoney.setText(sAddMoney);
                        addMoney.setTextColor(Color.GREEN);
                    }else{
                        addMoney.setText(sAddMoney);
                    }

                    iaddmoney = iiaddmoney;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        TextWatcher watcher3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)){
                    result = decimalFormat.format(Double.parseDouble(s.toString().replaceAll(",","")));
                    addMoney.setText(result);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        manMoney.addTextChangedListener(watcher);
        womanMoney.addTextChangedListener(watcher2);
        addMoney.addTextChangedListener(watcher3);


        this.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog.Builder alert = new AlertDialog.Builder(DatePickerActivity.this);
                alert.setTitle("오늘의 인생 샷");
                alert.setIcon(R.drawable.plancamera_web);
                alert.setMessage("오늘의 인생 샷 사진을 일기로 남깁니다!");
                alert.setPositiveButton("사진 촬영", cameraListener);
                alert.setNegativeButton("앨범 선택", albumListener);
                alert.setNeutralButton("취소", cancelListener);
                alert.show();
            }
        });
        //사용자가 버튼을 클릭하는 경우에는 화면에 날짜 창을 출력
        this.datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(DatePickerActivity.this, dateset, year, month, day);
                //show()함수를 실행해서 날짜 창을 출력하기
                datePickerDialog.setTitle("날짜 정하기");
                datePickerDialog.setIcon(R.mipmap.ic_launcher_date);
                datePickerDialog.show();
               /* String[] columns = new String[]{"date"};
                String mRefDate = null;
                String dateText = datePickerTxt.getText().toString();
                Cursor c = mSQLiteDBManager.query02(columns, null, null, null, null, null);
                if (c != null) {
                    while (c.moveToNext()) {
                        mRefDate = c.getString(0);
                        Log.v("mylog", "date " + mRefDate);
                        Toast.makeText(DatePickerActivity.this, "저장된 날짜는: " + mRefDate, Toast.LENGTH_SHORT).show();
                    }
                    c.close();
                }
                if (dateText.equals(mRefDate) == true) {
                    Toast.makeText(DatePickerActivity.this, dateText + "에 일기가 이미 저장되어 있습니다.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DatePickerActivity.this, "날짜 설정을 다시 해주세요 저장이 되지 않습니다.", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        this.timeHelloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog01 = new TimePickerDialog(DatePickerActivity.this, timeset, 00, 00, true);
                timePickerDialog01.setIcon(R.mipmap.starttimeimage);
                timePickerDialog01.setTitle("만난 시간 정하기");
                timePickerDialog01.show();
//                timeHelloBtn.setVisibility(View.INVISIBLE);
//                timeByeBtn.setVisibility(View.VISIBLE);
//                timeResetBtn.setVisibility(View.INVISIBLE);
//                starttimeText.setVisibility(View.INVISIBLE);
//                endtimeText.setVisibility(View.VISIBLE);
//                resetTimeText.setVisibility(View.INVISIBLE);
            }
        });
        this.timeByeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog02 = new TimePickerDialog(DatePickerActivity.this, timeset02, 00, 00, true);
                timePickerDialog02.setIcon(R.mipmap.endtimeimage);
                timePickerDialog02.setTitle("헤어진 시간 정하기");
                timePickerDialog02.show();
//                timeHelloBtn.setVisibility(View.INVISIBLE);
//                timeByeBtn.setVisibility(View.INVISIBLE);
//                timeResetBtn.setVisibility(View.VISIBLE);
//                starttimeText.setVisibility(View.INVISIBLE);
//                endtimeText.setVisibility(View.INVISIBLE);
//                resetTimeText.setVisibility(View.VISIBLE);
            }
        });
//        this.timeResetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timeHelloText.setText("");
//                timeByeText.setText("");
//                resultHour01.setText("");
//                timeHelloBtn.setVisibility(View.VISIBLE);
//                timeByeBtn.setVisibility(View.INVISIBLE);
//                timeResetBtn.setVisibility(View.INVISIBLE);
//                starttimeText.setVisibility(View.VISIBLE);
//                endtimeText.setVisibility(View.INVISIBLE);
//                resetTimeText.setVisibility(View.INVISIBLE);
//
//            }
//        });
//        this.addmoneyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dateMoney();
//            }
//        });


    }

    public void myTime() {

        resultHour = ihour2 - ihour;
        resultMinute = iminute2 - iminute;
        if (ihour > ihour2) {
            Toast.makeText(DatePickerActivity.this, "헤어진 시간이 더 작을 수 없습니다.", Toast.LENGTH_SHORT).show();
            timeByeText.setText("");
//            resultHour01.setText("");
        }
        if (ihour == ihour2 && iminute > iminute2) {
            Toast.makeText(DatePickerActivity.this, "헤어진 시간이 더 작을 수 없습니다.", Toast.LENGTH_SHORT).show();
            timeByeText.setText("");
//            resultHour01.setText("");
        }
        if (resultMinute < 0) {
            resultHour = ihour2 - ihour - 1;
            resultMinute = 60 - iminute2 - iminute;
            Log.v("mylog", "시간: " + resultHour);
            Log.v("mylog", "분: " + resultMinute);
        }
        if (iminute > iminute2) {
            resultMinute = (ihour2 * 60 + iminute2) - (ihour * 60 + iminute);
            Log.v("mylog", "시간2: " + resultHour);
            Log.v("mylog", "분2: " + resultMinute);
        }
        if (resultMinute >= 60) {
            resultMinute = resultMinute % 60;
            //resultHour = resultMinute/60;

            Log.v("mylog", "시간2: " + resultHour);
            Log.v("mylog", "분2: " + resultMinute);
        }
//        resultHour01.setText("총 만난 시간: " + resultHour + " 시간" + resultMinute + " 분");
//        resultHour01.setTextColor(Color.BLACK);
        FinalresultTime = resultHour * 60 + resultMinute;

    }

    public void dateMoney() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        int iman;
        int iwoman;
        strManMoney = manMoney.getText().toString();
        strWomanMoney = womanMoney.getText().toString();
        if (strManMoney != null && strWomanMoney != null && strManMoney.length() != 0 && strWomanMoney.length() != 0) {
            iman = Integer.parseInt(strManMoney);
            iwoman = Integer.parseInt(strWomanMoney);
            iaddmoney = iman + iwoman;
            numberstr = nf.format(iaddmoney);
            addMoney.setText(numberstr + "원");
            addMoney.setTextColor(Color.BLACK);
        } else {
            Toast.makeText(this, "데이트 비용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    //날짜 다이얼로그창을 만들때 꼭 사용해야하는 리스너 객체를 선언하고 생성합니다.
    //-> 사용자가 날짜 다이얼로그 창에서 출력된 년도 또는 월 또는 일을 선택 또는 변경 하는 경우에 자동으로 실행되는 함수
    //onDateSet() 재정의
    private DatePickerDialog.OnDateSetListener dateset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearValue, int monthValue, int dayOfMonth) {
            //사용자가 화면에 출력된 날짜 창에서 날짜를 선택 또는 변경하는 경우에 자동으로 실행되는 명령문들을 작성
            //Toast.makeText(DatePickerActivity.this, "사용자가 날짜를 선택 또는 변경함", Toast.LENGTH_SHORT).show();
            //위에서 선언한 전역변수에 날짜를 저장하기: 사용자가 선택한 날짜를 다른 함수에서도 사용 가능
            year = yearValue;
            month = monthValue + 1;
            day = dayOfMonth;
            // Toast.makeText(DatePickerActivity.this, "선택한 날짜는 년은 " + year + " 월은 " + month + " 일은 " + day, Toast.LENGTH_SHORT).show();
            datePickerTxt.setText(year + "년" + month + "월" + day + "일");
            datePickerTxt.setTextColor(Color.BLACK);
            /*ContentValues addRowValue = new ContentValues();
            addRowValue.put("date", year + "년" + month + "월" + day + "일");
            long insertRecordId = mSQLiteDBManager.insert(addRowValue);*/
        }
    };
    private TimePickerDialog.OnTimeSetListener timeset = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Toast.makeText(DatePickerActivity.this, "사용자가 시간을 설정시작", Toast.LENGTH_SHORT).show();
            ihour = hourOfDay;
            iminute = minute;
            // Toast.makeText(DatePickerActivity.this, "사용자가 설정한 시간은 " + ihour + "시"+ iminute + "분", Toast.LENGTH_SHORT).show();
            timeHelloText.setText(ihour + "시 " + iminute + "분");
            timeHelloText.setTextColor(Color.BLACK);
        }
    };
    private TimePickerDialog.OnTimeSetListener timeset02 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Toast.makeText(DatePickerActivity.this, "사용자가 시간을 설정시작", Toast.LENGTH_SHORT).show();
            ihour2 = hourOfDay;
            iminute2 = minute;
            timeByeText.setText(ihour2 + "시 " + iminute2 + "분");
            timeByeText.setTextColor(Color.BLACK);
            myTime();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.save_Btn:
                AlertDialog.Builder alert = new AlertDialog.Builder(DatePickerActivity.this);
                alert.setTitle("일기를 저장합니다.");
                alert.setIcon(R.mipmap.saveimagecolor);
                alert.setMessage("저장 하시겠습니까?");
                alert.setPositiveButton("응!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Toast.makeText(this, "저장버튼 클릭", Toast.LENGTH_SHORT).show();
                        // Log.v("mylog", "저장버튼 클릭");
                        DateValue = datePickerTxt.getText().toString();
                        TimeValue01 = timeHelloText.getText().toString();
                        TimeValue02 = timeByeText.getText().toString();
                        TitleValue = editText_title.getText().toString();
                        ContentValue = editText_content.getText().toString();
                        strManMoney = manMoney.getText().toString().replaceAll(",","");
                        strWomanMoney = womanMoney.getText().toString().replaceAll(",","");
                        numberstr = addMoney.getText().toString().replaceAll(",","");
//                        strResulthour = resultHour01.getText().toString();

                        /*String[] columns = new String[]{"date"};
                        String mRefDate = null;
                        Cursor c = mSQLiteDBManager.query(columns, null, null, null, null, null);
                        if (c != null) {
                            while (c.moveToNext()) {
                                mRefDate = c.getString(0);
                                Log.v("mylog", "date " + mRefDate);
                                //Toast.makeText(DatePickerActivity.this, "저장된 날짜는: " + mRefDate, Toast.LENGTH_SHORT).show();
                            }
                            c.close();
                        }
                        if (mRefDate.equals(DateValue) == true) {
                            Toast.makeText(DatePickerActivity.this, DateValue + "에 일기가 이미 저장되어 있습니다.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(DatePickerActivity.this, "같은 날의 일기는 저장이 되지 않습니다!", Toast.LENGTH_SHORT).show();
                        } else {
                        }*/
                        if (DateValue != null && DateValue.length() != 0 && TimeValue01 != null && TimeValue01.length() != 0
                                && TimeValue02 != null && TimeValue02.length() != 0 && TitleValue != null && TitleValue.length() != 0
                                && ContentValue != null && ContentValue.length() != 0 && strManMoney != null && strManMoney.length() != 0
                                && strWomanMoney != null && strWomanMoney.length() != 0 && addMoney != null && addMoney.length() != 0
                                /*&& resultHour01 != null && resultHour01.length() != 0*/) {
                            ContentValues addRowValue = new ContentValues();
                            addRowValue.put("date", DateValue);
                            addRowValue.put("starttime", TimeValue01);
                            addRowValue.put("endtime", TimeValue02);
                            addRowValue.put("title", TitleValue);
                            addRowValue.put("content", ContentValue);
//                            addRowValue.put("resulthour", strResulthour);
                            addRowValue.put("manmoney", strManMoney);
                            addRowValue.put("womanmoney", strWomanMoney);
                            addRowValue.put("resultmoney", iaddmoney);
                            addRowValue.put("finalminute", FinalresultTime);
                            addRowValue.put("bestphoto", img01);
//                            Toast.makeText(DatePickerActivity.this, "일기를 저장합니다.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(DatePickerActivity.this, DateValue + "의 일기가 저장되었습니다.", Toast.LENGTH_LONG).show();
                            mSQLiteDBManager.insert(addRowValue);
                            finish();
                        } else {
                            if(DateValue == null || DateValue.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "날짜를 설정 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(TimeValue01 == null || TimeValue01.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "만난 시간을 설정 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(TimeValue02 == null || TimeValue02.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "헤어진 시간을 설정 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(TitleValue == null || TitleValue.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "제목을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(ContentValue == null || ContentValue.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "일기 내용을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(strManMoney == null || strManMoney.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "남자친구의 데이트 비용을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(strWomanMoney == null || strWomanMoney.length() == 0){
                                Toast.makeText(DatePickerActivity.this, "여자친구의 데이트 비용을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });
                alert.setNegativeButton("아니!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();

                break;
            case R.id.list_Btn:
                // Toast.makeText(this, "리스트 버튼 클릭", Toast.LENGTH_SHORT).show();
                Log.v("mylog", "목록 버튼 클릭");
                Intent intent = new Intent(DatePickerActivity.this, DateListView.class);
                MoveToActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void doTakePhotoAction() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    myBitmap = extras.getParcelable("data");
                    MainActivity.setImageBitmaps(myBitmap, imageView);
                    img01 = BitMapToString(myBitmap);

                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;
            }
            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
            }
            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                intent.putExtra("outputX", true);
                intent.putExtra("outputY", true);
                intent.putExtra("aspectX", 1);//1
                intent.putExtra("aspectY", 1);//1
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }
        }
    }

    //이미지파일 Bitmap을 String함수로 변환
    private static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
