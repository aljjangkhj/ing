package www.khj08.com.dateplan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.NumberFormat;
import java.util.Calendar;


public class LoadDatePickerActivity extends AppCompatActivity {
    //날짜 정보를 가져올때 사용할 변수 선언: 달력 변수
    private Calendar calendar;
    private Uri mImageCaptureUri;

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

    private TextView timeHelloText;
    private TextView timeByeText;
    //화면에 날짜(연도, 월, 일)을 보여주는 텍스트 뷰의 주소 보관 변수 선언
    private TextView datePickerTxt;
    private TextView addMoney;
    private TextView resultHour01;
    private TextView starttimeText;
    private TextView endtimeText;
    private TextView resetTimeText;

    //화면에 날짜 선택 또는 변경 창을 출력해주는 버튼의 주소 보관 변수 선언
    private EditText editText_title;
    private EditText editText_content;
    private EditText manMoney;
    private EditText womanMoney;

    private Button list_Btn;
    private Button addmoneyBtn;
    private Button datePickerBtn;
    private Button timeHelloBtn;
    private Button timeByeBtn;
    private Button timeResetBtn;
    private Button save_Btn;

    private ListView dateListView;
    private SQLiteDBListView listViewAdapter;
    private String intid;

    private ImageView imageView;
    private ImageButton imageButton;
    //데이터베이스 함수
    public SQLiteDBManager mSQLiteDBManager = null;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.datepicker_update_layout);
        listViewAdapter = new SQLiteDBListView();

        //데이터베이스 테이블 생성 SQL문
        mSQLiteDBManager = SQLiteDBManager.getInstance(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        this.datePickerBtn = (Button) this.findViewById(R.id.date_picker_btn01);
        this.timeHelloBtn = (Button) this.findViewById(R.id.time_Hello_btn01);
        this.timeByeBtn = (Button) this.findViewById(R.id.time_Bye_btn01);
        this.timeResetBtn = (Button) this.findViewById(R.id.time_reset_btn01);
        // this.save_Btn = (Button) this.findViewById(R.id.save_diaryBtn01);
        // this.list_Btn = (Button) this.findViewById(R.id.diary_list01);
        this.addmoneyBtn = (Button) this.findViewById(R.id.addmoneybtn01);

        this.datePickerTxt = (TextView) this.findViewById(R.id.date_picker_text01);
        this.timeHelloText = (TextView) this.findViewById(R.id.hello_time_text01);
        this.timeByeText = (TextView) this.findViewById(R.id.bye_time_text01);
        this.resultHour01 = (TextView) this.findViewById(R.id.resulthour01);
        this.addMoney = (TextView) this.findViewById(R.id.add_money01);
        this.starttimeText = (TextView) this.findViewById(R.id.starttimeText01);
        this.endtimeText = (TextView) this.findViewById(R.id.endtimeText01);
        this.resetTimeText = (TextView) this.findViewById(R.id.timeresetText01);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf");
        this.datePickerTxt.setTypeface(typeface);
        this.timeHelloText.setTypeface(typeface);
        this.timeByeText.setTypeface(typeface);
        this.resultHour01.setTypeface(typeface);
        this.addMoney.setTypeface(typeface);
        this.starttimeText.setTypeface(typeface);
        this.endtimeText.setTypeface(typeface);
        this.resetTimeText.setTypeface(typeface);
        TextView font01 = (TextView) this.findViewById(R.id.font1);
        TextView font02 = (TextView) this.findViewById(R.id.font2);
        TextView font03 = (TextView) this.findViewById(R.id.font3);
        TextView font04 = (TextView) this.findViewById(R.id.font4);
        TextView font05 = (TextView) this.findViewById(R.id.font5);
        TextView font06 = (TextView) this.findViewById(R.id.font6);
        TextView font07 = (TextView) this.findViewById(R.id.font7);
        TextView font08 = (TextView) this.findViewById(R.id.font8);
        font01.setTypeface(typeface);
        font02.setTypeface(typeface);
        font03.setTypeface(typeface);
        font04.setTypeface(typeface);
        font05.setTypeface(typeface);
        font06.setTypeface(typeface);
        font07.setTypeface(typeface);
        font08.setTypeface(typeface);

        this.editText_title = (EditText) this.findViewById(R.id.edit_text_title01);
        this.editText_content = (EditText) this.findViewById(R.id.edit_text_content01);
        this.manMoney = (EditText) this.findViewById(R.id.man_money01);
        this.womanMoney = (EditText) this.findViewById(R.id.woman_money01);
        this.imageView = (ImageView) this.findViewById(R.id.dateplan_imageView01);
        this.imageButton = (ImageButton) this.findViewById(R.id.dateplan_imagebtn01);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(LoadDatePickerActivity.this);
                alert.setTitle("오늘의 인생 샷 수정");
                alert.setIcon(R.mipmap.plancamera);
                alert.setMessage("오늘의 인생 샷 사진을 수정 합니다!");
                alert.setPositiveButton("사진 촬영", cameraListener);
                alert.setNegativeButton("앨범 선택", albumListener);
                alert.setNeutralButton("취소", cancelListener);
                alert.show();

            }
        });

        Intent intent = getIntent();
        intid = intent.getStringExtra("위치값");

        String[] columns = new String[]{"_id", "date", "starttime", "endtime", "title", "content", "resulthour", "manmoney", "womanmoney", "resultmoney", "bestphoto"};
        int id01;
        String mRefDate;
        String selection = "date='" + intid + "'";
        Cursor c = mSQLiteDBManager.query(columns, selection, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                id01 = c.getInt(0);
                mRefDate = c.getString(1);
                String mRefstart = c.getString(2);
                String mRefEnd = c.getString(3);
                String mRefTitle = c.getString(4);
                String mRefContent = c.getString(5);
                String mRefResulthour = c.getString(6);
                String mRefManmoney = c.getString(7);
                String mRefWomanmoney = c.getString(8);
                String mRefresultmoeny = c.getString(9);
                String mRefbestphoto = c.getString(10);
                //Log.v("mylog", "date " + mRefDate);
//                Toast.makeText(this, "불러온 아이디는 "+id01 + "불러온 날짜는 "+ mRefDate, Toast.LENGTH_SHORT).show();

                myBitmap = StringToBitMap(mRefbestphoto);
                imageView.setImageBitmap(myBitmap);
                datePickerTxt.setText(mRefDate);
                timeHelloText.setText(mRefstart);
                timeByeText.setText(mRefEnd);
                resultHour01.setText(mRefResulthour);
                addMoney.setText(mRefresultmoeny);
                editText_title.setText(mRefTitle);
                editText_content.setText(mRefContent);
                manMoney.setText(mRefManmoney);
                womanMoney.setText(mRefWomanmoney);
            }
            c.close();
        }
       /* this.save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        list_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //사용자가 버튼을 클릭하는 경우에는 화면에 날짜 창을 출력
        this.datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoadDatePickerActivity.this, "날짜는 변환할 수 없습니다. :)", Toast.LENGTH_SHORT).show();
            }
        });
        this.timeHelloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog01 = new TimePickerDialog(LoadDatePickerActivity.this, timeset, 00, 00, true);
                timePickerDialog01.setIcon(R.mipmap.starttimeimage);
                timePickerDialog01.setTitle("만난 시간 정하기");
                timePickerDialog01.show();
                timeHelloBtn.setVisibility(View.INVISIBLE);
                timeByeBtn.setVisibility(View.VISIBLE);
                timeResetBtn.setVisibility(View.INVISIBLE);
                starttimeText.setVisibility(View.INVISIBLE);
                endtimeText.setVisibility(View.VISIBLE);
                resetTimeText.setVisibility(View.INVISIBLE);
            }
        });
        this.timeByeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog02 = new TimePickerDialog(LoadDatePickerActivity.this, timeset02, 00, 00, true);
                timePickerDialog02.setIcon(R.mipmap.endtimeimage);
                timePickerDialog02.setTitle("헤어진 시간 정하기");
                timePickerDialog02.show();
                timeHelloBtn.setVisibility(View.INVISIBLE);
                timeByeBtn.setVisibility(View.INVISIBLE);
                timeResetBtn.setVisibility(View.VISIBLE);
                starttimeText.setVisibility(View.INVISIBLE);
                endtimeText.setVisibility(View.INVISIBLE);
                resetTimeText.setVisibility(View.VISIBLE);
            }
        });
        this.timeResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeHelloText.setText("");
                timeByeText.setText("");
                resultHour01.setText("");
                timeHelloBtn.setVisibility(View.VISIBLE);
                timeByeBtn.setVisibility(View.INVISIBLE);
                timeResetBtn.setVisibility(View.INVISIBLE);
                starttimeText.setVisibility(View.VISIBLE);
                endtimeText.setVisibility(View.INVISIBLE);
                resetTimeText.setVisibility(View.INVISIBLE);
            }
        });
        this.addmoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateMoney();
            }
        });
        addmoneyBtn.performClick();//계산 강제클릭

    }

    public void myTime() {

        resultHour = ihour2 - ihour;
        resultMinute = iminute2 - iminute;
        if (ihour > ihour2) {
            //Toast.makeText(DatePickerActivity.this, "헤어진 시간이 더 작을 수 없습니다.", Toast.LENGTH_SHORT).show();
            timeByeText.setText("");
            resultHour01.setText("");
        }
        if (ihour == ihour2 && iminute > iminute2) {
            //Toast.makeText(.this, "헤어진 시간이 더 작을 수 없습니다.", Toast.LENGTH_SHORT).show();
            timeByeText.setText("");
            resultHour01.setText("");
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
        resultHour01.setText("총 만난 시간: " + resultHour + " 시간" + resultMinute + " 분");
        resultHour01.setTextColor(Color.BLACK);
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

    private DatePickerDialog.OnDateSetListener dateset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearValue, int monthValue, int dayOfMonth) {
            year = yearValue;
            month = monthValue + 1;
            day = dayOfMonth;
            datePickerTxt.setText(year + "년" + month + "월" + day + "일");
            datePickerTxt.setTextColor(Color.BLACK);
        }
    };
    private TimePickerDialog.OnTimeSetListener timeset = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            ihour = hourOfDay;
            iminute = minute;
            timeHelloText.setText("만난 시간: " + ihour + "시 " + iminute + "분");
            timeHelloText.setTextColor(Color.BLACK);
        }
    };
    private TimePickerDialog.OnTimeSetListener timeset02 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            ihour2 = hourOfDay;
            iminute2 = minute;
            timeByeText.setText("헤어진 시간: " + ihour2 + "시 " + iminute2 + "분");
            timeByeText.setTextColor(Color.BLACK);
            myTime();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.loadsave, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.insert_btn:
                AlertDialog.Builder alert01 = new AlertDialog.Builder(LoadDatePickerActivity.this);
                alert01.setTitle("일기를 수정합니다.");
                alert01.setIcon(R.mipmap.insertcolor);
                alert01.setMessage("수정 하시겠습니까?");
                alert01.setPositiveButton("응!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(LoadDatePickerActivity.this, "수정 버튼 클릭", Toast.LENGTH_SHORT).show();
                        DateValue = datePickerTxt.getText().toString();
                        TimeValue01 = timeHelloText.getText().toString();
                        TimeValue02 = timeByeText.getText().toString();
                        TitleValue = editText_title.getText().toString();
                        ContentValue = editText_content.getText().toString();
                        strManMoney = manMoney.getText().toString();
                        strWomanMoney = womanMoney.getText().toString();
                        numberstr = addMoney.getText().toString();
                        strResulthour = resultHour01.getText().toString();
                        String LoadBitmap = "";
                        if(myBitmap != null) {
                            LoadBitmap = BitMapToString(myBitmap);
                        }
                        if (DateValue != null && DateValue.length() != 0 && TimeValue01 != null && TimeValue01.length() != 0
                                && TimeValue02 != null && TimeValue02.length() != 0 && TitleValue != null && TitleValue.length() != 0
                                && ContentValue != null && ContentValue.length() != 0 && strManMoney != null && strManMoney.length() != 0
                                && strWomanMoney != null && strWomanMoney.length() != 0 && addMoney != null && addMoney.length() != 0
                                && resultHour01 != null && resultHour01.length() != 0) {
                            //img01 = BitMapToString(myBitmap);

                            ContentValues updateRowValue = new ContentValues();
//                            Log.v("mylog", "날짜는 " + DateValue);
//                            Log.v("mylog", "만난 시간은 " + TimeValue01);
//                            Log.v("mylog", "헤어진 시간은 " + TimeValue02);
//                            Log.v("mylog", "제목은 " + TitleValue);
//                            Log.v("mylog", "내용은 " + ContentValue);
//                            Log.v("mylog", "총만난 시간: " + strResulthour);
//                            Log.v("mylog", "남자지출: " + strManMoney);
//                            Log.v("mylog", "여자지출: " + strWomanMoney);
//                            Log.v("mylog", "총지출: " + numberstr);
                            updateRowValue.put("date", DateValue);
                            updateRowValue.put("starttime", TimeValue01);
                            updateRowValue.put("endtime", TimeValue02);
                            updateRowValue.put("title", TitleValue);
                            updateRowValue.put("content", ContentValue);
                            updateRowValue.put("resulthour", strResulthour);
                            updateRowValue.put("manmoney", strManMoney);
                            updateRowValue.put("womanmoney", strWomanMoney);
                            updateRowValue.put("resultmoney", iaddmoney);
                            updateRowValue.put("finalminute", FinalresultTime);
                            updateRowValue.put("bestphoto",  img01 = (img01 == null)? LoadBitmap : img01 );

                            //Intent intent = getIntent();
                            // intid = intent.getIntExtra("위치값",0);
                            // Toast.makeText(LoadDatePickerActivity.this, "불러온 위치값은: "+ intid, Toast.LENGTH_SHORT).show();
                            mSQLiteDBManager.update(updateRowValue, "date='" + intid+"'", null);
                            //  Toast.makeText(LoadDatePickerActivity.this, "_id"+intid+"가 수정됌", Toast.LENGTH_SHORT).show();
                            // Toast.makeText(DatePickerActivity.this, DateValue+"의 일기가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            //mSQLiteDBManager.insert(updateRowValue);
                            Toast.makeText(LoadDatePickerActivity.this, "수정 되었습니다!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            if(DateValue == null || DateValue.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "날짜를 설정 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(TimeValue01 == null || TimeValue01.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "만난 시간을 설정 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(TimeValue02 == null || TimeValue02.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "헤어진 시간을 설정 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(TitleValue == null || TitleValue.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "제목을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(ContentValue == null || ContentValue.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "일기 내용을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(strManMoney == null || strManMoney.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "남자친구의 데이트 비용을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(strWomanMoney == null || strWomanMoney.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "여자친구의 데이트 비용을 입력 해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(addMoney == null || addMoney.length() == 0){
                                Toast.makeText(LoadDatePickerActivity.this, "+ 버튼을 눌러 주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });
                alert01.setNegativeButton("아니!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert01.show();

                break;
            case R.id.delete_btn:
                AlertDialog.Builder alert = new AlertDialog.Builder(LoadDatePickerActivity.this);
                alert.setTitle("일기를 삭제합니다.");
                alert.setIcon(R.mipmap.deletecolor);
                alert.setMessage("정말로 삭제 하시겠습니까?!" + "\n" + "삭제된 데이터는 복구할 수 없습니다.");
                alert.setPositiveButton("응!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoadDatePickerActivity.this, "삭제합니다.", Toast.LENGTH_SHORT).show();
                        String deleteManager = "date='" + intid + "'";
                        mSQLiteDBManager.delete(deleteManager, null);
                        finish();
                    }
                });
                alert.setNegativeButton("아니!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoadDatePickerActivity.this, "추억을 간직하세요♥", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
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

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
