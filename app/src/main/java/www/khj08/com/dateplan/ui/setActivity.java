package www.khj08.com.dateplan.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.popup.CheckBoxPopup;
import www.khj08.com.dateplan.popup.NameChangePopup;
import www.khj08.com.dateplan.popup.Popup;
import www.khj08.com.dateplan.ui.list_adapter.ListViewAdapter;

public class setActivity extends BaseActivity {

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    boolean [] checkAlram = {false};
    int checkint;
    boolean checkvalue = false;
    private String[] strSet = {"항상 상단 알림 표시 설정"};

    public SQLiteDBManager mSQLiteDBManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set);
        init_autoscreen(1);

        mSQLiteDBManager = SQLiteDBManager.getInstance(this);
        ListView listview;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        // 첫 번째 아이템 추가.

        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_date), "만남 시작한 날 정하기", "메인화면에 "+MySharedPreferencesManager.getPic01(mContext)+"(와)과"+
                MySharedPreferencesManager.getPic02(mContext)+"(이)가 만나기 시작한 날을 지정합니다.");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_plan), "메인 문구 정하기", "메인 화면에 "+MySharedPreferencesManager.getPic01(mContext)+"(와)과"+
                MySharedPreferencesManager.getPic02(mContext)+"의 이름을 수정합니다.");
        // 세 번째 아이템 추가.
        //adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_cake), "기념일 추가하기", "기념일을 추가합니다.");
        //네번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.alram), "상단 알림 표시", "상단 알림창에 잉ing알림을 표시 또는 해제 합니다.");
       // adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.ic_launcher), "배경 화면 색상 바꾸기", "메인 배경화면의 색상을 지정 합니다.");
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.askicon),"사용 TIP","잉ing 어플리케이션의 사용법을 설명 해드립니다.");

        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.myicon),"피드백","불편하셨던 기능이나 추가했으면 좋겠다는 기능을 개발자에게 피드백을 줍니다.");
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.deletecolor),"모두 삭제","저장되있는 모든 데이터를 삭제하고 처음 상태로 되돌립니다.");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 첫번째 리스트뷰를 클릭할시 달력Dialog 출력
                        //  Toast.makeText(setActivity.this, "달력", Toast.LENGTH_SHORT).show();
//                        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year01, int month01, int dayOfMonth) {
//
//                                year = year01;
//                                month = month01 + 1;
//                                day = dayOfMonth;
//                                MySharedPreferencesManager.setStartLoveDay(year, month, day, setActivity.this);
//                                //  Toast.makeText(setActivity.this,year+"년"+month+"월"+day+"일" , Toast.LENGTH_SHORT).show();
//                                Toast.makeText(setActivity.this, "메인화면에 등록 되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                DdayManager(year, month, day);
//                            }
//                        };
//
//                        //달력의 현재 날짜를 읽어오고 현재 날짜부터 선택이 가능합니다.
//                        calendar = Calendar.getInstance();
//                        year = calendar.get(Calendar.YEAR);
//                        month = calendar.get(Calendar.MONTH);
//                        day = calendar.get(Calendar.DAY_OF_MONTH);
//                        datePickerDialog = new DatePickerDialog(setActivity.this, callBack, year, month, day);
//                        datePickerDialog.show();

                        calendar = Calendar.getInstance();
                        int MyYear = calendar.get(Calendar.YEAR);
                        int MyMonth = calendar.get(Calendar.MONTH);
                        int MyDay = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(mContext, android.R.style.Theme_Holo_Light_Dialog , new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year01, int month01, int dayOfMonth) {
                                //Todo your work here
                                year = year01;
                                month = month01 + 1;
                                day = dayOfMonth;
                                MySharedPreferencesManager.setStartLoveDay(year, month, day, setActivity.this);
                                //  Toast.makeText(setActivity.this,year+"년"+month+"월"+day+"일" , Toast.LENGTH_SHORT).show();
                                Toast.makeText(setActivity.this, "메인화면에 등록 되었습니다.", Toast.LENGTH_SHORT).show();

                                DdayManager(year, month, day);
                            }
                        }, MyYear, MyMonth, MyDay);

                        dialog.setTitle("만남 시작한 날 정하기");
                        dialog.setIcon(R.mipmap.mainlogo01);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();

                        break;
                    case 1:
//                        LayoutInflater layoutInflater = getLayoutInflater();
//                        final View dialogView = layoutInflater.inflate(R.layout.edittext_dialog, null);
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(setActivity.this);
//                        builder.setIcon(R.mipmap.ic_launcher_plan);
//                        builder.setTitle("메인(사진1,사진2) 문구 정하기");
//                        builder.setView(dialogView);
//                        builder.setPositiveButton("이 문구로 할게요!", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                EditText firstName = (EditText)dialogView.findViewById(R.id.picName01);
//                                EditText secondName = (EditText)dialogView.findViewById(R.id.picName02);
//                                String strFirst = firstName.getText().toString();
//                                String strSec = secondName.getText().toString();
//                                MySharedPreferencesManager.setPic01(strFirst,setActivity.this);
//                                MySharedPreferencesManager.setPic02(strSec,setActivity.this);
//                                Toast.makeText(setActivity.this, "사진1을 "+strFirst+"로 변경하였습니다.", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(setActivity.this, "사진2를 "+strSec+"로 변경하였습니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //Toast.makeText(setActivity.this, "문구 설정을 취소합니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
                        final NameChangePopup nameChangePopup = new NameChangePopup(mContext);
                        nameChangePopup.OK_Click = new NameChangePopup.onClick() {
                            @Override
                            public void onClick() {
                                MySharedPreferencesManager.setPic01(nameChangePopup.getName1(),mContext);
                                MySharedPreferencesManager.setPic02(nameChangePopup.getName2(),mContext);
                                Toast.makeText(mContext, MySharedPreferencesManager.getPic01(mContext)+"(을)를 "+nameChangePopup.getName1()+"(으)로 변경하였습니다.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext, MySharedPreferencesManager.getPic02(mContext)+"(을)를 "+nameChangePopup.getName2()+"(으)로 변경하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        };
                        nameChangePopup.show();

//                        Popup popup4 = new Popup(mContext,"","메인("+MySharedPreferencesManager.getPic01(mContext)+","+MySharedPreferencesManager.getPic02(mContext)+") 문구 정하기","취소","확인");
//                        popup4.OK_Click = new Popup.onClick() {
//                            @Override
//                            public void onClick() {
//
//                            }
//                        };
//                        popup4.Cancel_Click = new Popup.onClick() {
//                            @Override
//                            public void onClick() {
//
//                            }
//                        };
//                        popup4.show();
                        break;
                    case 2:
//                        AlertDialog alterDialog = null;
//                        //Toast.makeText(setActivity.this, "알림 표시하기", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder alert = new AlertDialog.Builder(setActivity.this);
//                        final DialogInterface.OnMultiChoiceClickListener multilistener = new DialogInterface.OnMultiChoiceClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                                if(isChecked == true){
//                                  //  Toast.makeText(setActivity.this, "체크됨", Toast.LENGTH_SHORT).show();
//                                    checkint = 1;
//                                   // checkAlram = new boolean[]{true};
//                                    checkvalue = true;
//                                }
//                                else{
//                                  //  Toast.makeText(setActivity.this, "해제됨", Toast.LENGTH_SHORT).show();
//                                    checkint = 2;
//                                    //checkAlram = new boolean[]{false};
//                                    checkvalue = false;
//                                }
//
//                            }
//                        };
//
//                        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                switch (which) {
//                                    case AlertDialog.BUTTON_POSITIVE:
//                                        if(checkint == 1){
//                                            Intent refintent = new Intent(setActivity.this, MyNotificationService.class);
//                                            MySharedPreferencesManager.setCheckbox(checkvalue,setActivity.this);
//                                            startService(refintent);
//                                        }
//                                        else if(checkint == 2){
//                                            Intent refIntent = new Intent(setActivity.this, MyNotificationService.class);
//                                            MySharedPreferencesManager.setCheckbox(checkvalue,setActivity.this);
//                                            stopService(refIntent);
//                                        }
//                                        break;
//                                    case AlertDialog.BUTTON_NEGATIVE:
//                                        dialog.dismiss();
//                                        break;
//                                }
//                            }
//                        };
//                        checkAlram = new boolean[]{MySharedPreferencesManager.getCheckbox(setActivity.this)};
//                        alert.setMultiChoiceItems(strSet,checkAlram,multilistener);
//                        alert.setPositiveButton("확인", listener);
//                        alert.setNegativeButton("취소", listener);
//                        alterDialog = alert.create();
//                        alterDialog.setTitle("상단바 표시 설정하기");
//                        alterDialog.setIcon(R.mipmap.alram);
//                        alterDialog.show();
//                        break;

                        CheckBoxPopup checkBoxPopup = new CheckBoxPopup(mContext);
                        checkBoxPopup.OK_Click = new CheckBoxPopup.onClick() {
                            @Override
                            public void onClick() {
                                Intent intent = new Intent(mContext,MyNotificationService.class);
                                if (MySharedPreferencesManager.getCheckbox(mContext)){
                                    startService(intent);
                                }else{
                                    stopService(intent);
                                }
                            }
                        };
                        checkBoxPopup.Cancel_Click = new CheckBoxPopup.onClick() {
                            @Override
                            public void onClick() {
                                Intent intent = new Intent(mContext,MyNotificationService.class);
                                if (MySharedPreferencesManager.getCheckbox(mContext)){
                                    startService(intent);
                                }else{
                                    stopService(intent);
                                }
                            }
                        };
                        checkBoxPopup.show();
                        break;

                    case 3:
                     //   Toast.makeText(setActivity.this, "사용 설명서", Toast.LENGTH_SHORT).show();
//                        AlertDialog alterDialog01 = null;
//                        AlertDialog.Builder alert01 = new AlertDialog.Builder(setActivity.this);
//                        alert01.setMessage("잉ing을 이용해 주셔서 감사합니다"+"\n"+"\n"+
//                                "첫번째, 잉ing은 사진을 넣을 수 있는 일기장이 제공됩니다."+"\n"+"\n"+
//                                "두번째, 사람과 사람간의 만남 시간과 사용한 비용을 간략히 정리할 수 있습니다."+"\n"+"\n"+
//                                "세번째, D-DAY를 항시 상단 알림에 출력이 가능합니다." +"\n"+"\n"+
//                                "네번째, 메인 화면에 사랑하는 사람의 얼굴과 한줄 평을 남길 수 있습니다."+ "\n"+"\n"+
//                                "다섯번째, D-DAY로 100일부터 18200일의 기념일과 1년부터 50년의 기념일이 제공됩니다."+ "\n"+"\n"+
//                                "여섯번째, 일기 목록을 통해 간편하게 자신이 써왔던 일기를 볼 수 있습니다." + "\n"+"\n"+
//                                "※ 사용시 주의할점: 저장이 되어있는 날짜의 일기는 또 다시 저장할 수 없습니다!! 또한 상단 알림은 메인화면에서 사진을 선택 하셔야 상단 알림을 출력 할 수 있습니다. "+
//                                "일기나 D-DAY를 저장 또는 삭제 하였으나 출력이 되지 않을시 새로고침 버튼을 눌러주시거나 어플을 종료 후 다시 실행하면 출력됩니다." +"\n"+"\n"+ "감사합니다.");
//                                //"사용하실 때 불편한 점이나 더 편리한 기능을 넣고싶다면"+"\n"
//                                //"개발자 이메일: aljjangkhj@nate.com 으로 메일을 넣어주십시오."
//                        alert01.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        alterDialog01 = alert01.create();
//                        alterDialog01.setTitle("사용 TIP");
//                        alterDialog01.setIcon(R.mipmap.askicon);
//                        alterDialog01.show();

                        Popup popup = new Popup(mContext,"사용 설명서","잉ing을 이용해 주셔서 감사합니다"+"\n"+"\n"+
                                "첫번째, 잉ing은 사진을 넣을 수 있는 일기장이 제공됩니다."+"\n"+"\n"+
                                "두번째, 사람과 사람간의 만남 시간과 사용한 비용을 간략히 정리할 수 있습니다."+"\n"+"\n"+
                                "세번째, D-DAY를 항시 상단 알림에 출력이 가능합니다." +"\n"+"\n"+
                                "네번째, 메인 화면에 사랑하는 사람의 얼굴과 한줄 평을 남길 수 있습니다."+ "\n"+"\n"+
                                "다섯번째, D-DAY로 100일부터 18200일의 기념일과 1년부터 50년의 기념일이 제공됩니다."+ "\n"+"\n"+
                                "여섯번째, 일기 목록을 통해 간편하게 자신이 써왔던 일기를 볼 수 있습니다." + "\n"+"\n"+
                                "※ 사용시 주의할점: 저장이 되어있는 날짜의 일기는 또 다시 저장할 수 없습니다!! 또한 상단 알림은 메인화면에서 사진을 선택 하셔야 상단 알림을 출력 할 수 있습니다. "+
                                "일기나 D-DAY를 저장 또는 삭제 하였으나 출력이 되지 않을시 새로고침 버튼을 눌러주시거나 어플을 종료 후 다시 실행하면 출력됩니다." +"\n"+"\n"+ "감사합니다.","","확인");
                        popup.show();

                        break;
                    case 4:
//                         AlertDialog alterDialog02 = null;
//                         AlertDialog.Builder alert02 = new AlertDialog.Builder(setActivity.this);
//                         alert02.setMessage("사용하실 때 불편한 점이나 더 편리한 기능을 넣고싶다면 "+"\n"+
//                                 "개발자 이메일: aljjangkhj@nate.com "+"\n"+
//                                 "메일 주세요. "+"\n"+"좋은 하루 되세요^^ ");
//                         //"사용하실 때 불편한 점이나 더 편리한 기능을 넣고싶다면"+"\n"
//                         //"개발자 이메일: aljjangkhj@nate.com 으로 메일을 넣어주십시오."
//                         alert02.setPositiveButton("메일 보내기", new DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialog, int which) {
//
//                                 Intent it = new Intent(Intent.ACTION_SEND);
//                                 String[] mailaddr = {"aljjangkhj@nate.com"};
//
//                                 it.setType("plaine/text");
//                                 it.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람
//                                 it.putExtra(Intent.EXTRA_SUBJECT, ""); // 제목
//                                 it.putExtra(Intent.EXTRA_TEXT,""); // 첨부내용
//
//                                 MoveToActivity(it);
//                             }
//                         });
//                         alert02.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialog, int which) {
//
//                             }
//                         });
//                         alterDialog02 = alert02.create();
//                         alterDialog02.setTitle("피드백");
//                         alterDialog02.setIcon(R.mipmap.myicon);
//                         alterDialog02.show();

                        Popup popup2 = new Popup(mContext,"이메일 보내기","사용하실 때 불편한 점이나 더 편리한 기능을 넣고싶다면 "+"\n"+
                                "개발자 이메일: aljjangkhj@nate.com "+"\n"+
                                "메일 주세요. "+"\n"+"좋은 하루 되세요^^ ","취소","메일 보내기");
                        popup2.OK_Click = new Popup.onClick() {
                            @Override
                            public void onClick() {
                                Intent it = new Intent(Intent.ACTION_SEND);
                                String[] mailaddr = {"aljjangkhj@nate.com"};

                                it.setType("plaine/text");
                                it.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람
                                it.putExtra(Intent.EXTRA_SUBJECT, ""); // 제목
                                it.putExtra(Intent.EXTRA_TEXT,""); // 첨부내용

                                MoveToActivity(it);
                            }
                        };
                        popup2.show();

                         break;
                    case 5:
//                        AlertDialog alterDialog03 = null;
//                        AlertDialog.Builder alert03 = new AlertDialog.Builder(setActivity.this);
//                        alert03.setMessage("데이터들을 모두 삭제하시겠습니까? "+"\n삭제된 정보들은 되돌릴 수 없습니다.");
//                        alert03.setPositiveButton("삭제할래", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                MySharedPreferencesManager.setStartLoveDay(0,0,0,setActivity.this);
//                                MySharedPreferencesManager.setDdaySave(0,setActivity.this);
//                                MySharedPreferencesManager.setLoveDay(0,setActivity.this);
//                                MySharedPreferencesManager.setToday(0,setActivity.this);
//                                MySharedPreferencesManager.setManPicture("",setActivity.this);
//                                MySharedPreferencesManager.setWomanPicture("",setActivity.this);
//                                MySharedPreferencesManager.setYear(0,setActivity.this);
//                                MySharedPreferencesManager.setMonth(0,setActivity.this);
//                                MySharedPreferencesManager.setDay(0,setActivity.this);
//                                MySharedPreferencesManager.setPic01("",setActivity.this);
//                                MySharedPreferencesManager.setPic02("",setActivity.this);
//                                MySharedPreferencesManager.setMainPicture("",setActivity.this);
//                                MySharedPreferencesManager.setMainEdit("",setActivity.this);
//                                mSQLiteDBManager.delete(null,null);
//                                Toast.makeText(setActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        alert03.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(setActivity.this, "혹시라도 삭제하면 추억들이 다 날아가요:)", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        alterDialog03 = alert03.create();
//                        alterDialog03.setTitle("데이터를 모두 삭제합니다.");
//                        alterDialog03.setIcon(R.mipmap.deletecolor);
//                        alterDialog03.show();
                        Popup popup3 = new Popup(mContext,"데이터 삭제하기","데이터들을 모두 삭제하시겠습니까? \n삭제된 정보들은 되돌릴 수 없습니다.","취소","삭제");
                        popup3.OK_Click = new Popup.onClick() {
                            @Override
                            public void onClick() {
                                MySharedPreferencesManager.setStartLoveDay(0,0,0,setActivity.this);
                                MySharedPreferencesManager.setDdaySave(0,setActivity.this);
                                MySharedPreferencesManager.setLoveDay(0,setActivity.this);
                                MySharedPreferencesManager.setToday(0,setActivity.this);
                                MySharedPreferencesManager.setManPicture("",setActivity.this);
                                MySharedPreferencesManager.setWomanPicture("",setActivity.this);
                                MySharedPreferencesManager.setYear(0,setActivity.this);
                                MySharedPreferencesManager.setMonth(0,setActivity.this);
                                MySharedPreferencesManager.setDay(0,setActivity.this);
                                MySharedPreferencesManager.setPic01("",setActivity.this);
                                MySharedPreferencesManager.setPic02("",setActivity.this);
                                MySharedPreferencesManager.setMainPicture("",setActivity.this);
                                MySharedPreferencesManager.setMainEdit("",setActivity.this);
                                mSQLiteDBManager.delete(null,null);
                                Toast.makeText(setActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        };
                        popup3.Cancel_Click = new Popup.onClick() {
                            @Override
                            public void onClick() {
                                Toast.makeText(setActivity.this, "혹시라도 삭제하면 추억들이 다 날아가요:)", Toast.LENGTH_SHORT).show();
                            }
                        };
                        popup3.show();
                        break;
                }
            }
        });

    }

    public int DdayManager(int myyear, int mmonth, int mday) {
        try {
            //Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            Calendar dday = Calendar.getInstance();
            calendar.set(year, month + 1, day);
            dday.set(myyear, mmonth, mday);// D-day의 날짜를 입력합니다.
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값)))
//            if(myyear == 1960&&mmonth == 2|| myyear == 1964&&mmonth == 2|| myyear == 1968&&mmonth == 2 || myyear == 1972&&mmonth == 2|| myyear == 1976&&mmonth == 2
//                    || myyear == 1980&&mmonth == 2 || myyear == 1984&&mmonth == 2 || myyear == 1988&&mmonth == 2 || myyear == 1992&&mmonth == 2 || myyear == 1996&&mmonth == 2
//                    || myyear == 2000&&mmonth == 2 || myyear == 2004&&mmonth == 2 || myyear == 2008&&mmonth == 2 || myyear == 2012&&mmonth == 2 || myyear == 2016&&mmonth == 2 || myyear == 2020&&mmonth == 2){
//                dday.set(myyear,mmonth,mday);
//                Log.v("mylog","myyear 2012 mmonth 2");
//            }else if(mmonth == 2){dday.set(myyear,mmonth,mday+2);Log.v("mylog","else if");}
//            else{dday.set(myyear,mmonth,mday-1);}
//            if(mmonth == 1){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 1");}
//            if(mmonth == 3){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 3");}
//            if(mmonth == 5){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 5");}
//            if(mmonth == 7){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 7");}
//            if(mmonth == 8){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 8");}
//            if(mmonth == 9){dday.set(myyear,mmonth,mday-1);Log.v("mylog","월 9");}
//            if(mmonth == 10){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 10");}
//            if(mmonth == 12){dday.set(myyear,mmonth,mday-2);Log.v("mylog","월 12");}
            //2018.03.27
//            if(mmonth == 4){dday.set(myyear,mmonth,mday);Log.v("mylog","444444");}
//            switch (mmonth){
//                case 1 : mday = 31; break;
//                case 2 :
//                    if((myyear% 4 == 0 && myyear% 100 != 0) || myyear % 400 == 0){
//                        mday = 29;
//                    }else{
//                        mday = 28;
//                    }
//                    break;
//                case 3 : mday = 31; break;
//                case 4 : mday = 30; break;
//                case 5 : mday = 31; break;
//                case 6 : mday = 30; break;
//                case 7 : mday = 31; break;
//                case 8 : mday = 31; break;
//                case 9 : mday = 30; break;
//                case 10 : mday = 31; break;
//                case 11 : mday = 30; break;
//                case 12 : mday = 31; break;
//            }  2015년 2월 -2 1,3,5,7,8,10,12월 +1 2016년 1,3,5,7월 +1 2월 -1
            //2018.03.27
            if(month%2 == 0){//홀수의 달일경우 (month+1)
                if(mmonth == 4|| mmonth == 6 || mmonth == 9 || mmonth == 11){
                    dday.set(myyear,mmonth,mday);
                }else if(mmonth == 2){
                    if(myyear%4 == 0) {
                        dday.set(myyear, mmonth, mday + 1);
                    }else {
                        dday.set(myyear, mmonth, mday + 2);
                    }
                }else{
                    dday.set(myyear,mmonth,mday-1);
                }
            }else{
                if(mmonth == 4|| mmonth == 6 || mmonth == 9 || mmonth == 11){
                    dday.set(myyear,mmonth,mday-1);
                }else if(mmonth == 2){
                    if(myyear%4 == 0) {
                        dday.set(myyear, mmonth, mday);
                    }else {
                        dday.set(myyear, mmonth, mday + 1);
                    }
                }else{
                    dday.set(myyear,mmonth,mday-2);
                }
            }

            Log.v("mylog", "오늘 날짜는" + year + "년 " + (month+1) + "월 " + day + "일 ");
            Log.v("mylog", "dday설정 날짜는" + myyear + "년 " + mmonth + "월 " + mday + "일");
            long tday = calendar.getTimeInMillis() / 86400000;
            long lday = dday.getTimeInMillis() / 86400000;
            Log.v("mylog", "tday.Time " + calendar.getTimeInMillis());
            Log.v("mylog", "lday.Time " + dday.getTimeInMillis());
            Log.v("mylog", "D-DAY 값은 " + (tday-lday));
            Log.v("mylog", "tday: " + (tday-lday));
            MySharedPreferencesManager.setToday(tday,setActivity.this);
            MySharedPreferencesManager.setLoveDay(lday,setActivity.this);
            long count = tday - lday; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
        //    Toast.makeText(this, "저장된 D-Day는" + myyear + "년 " + mmonth + "월 " + mday + "일 입니다." + "D-DAY는 " + "-" + count, Toast.LENGTH_SHORT).show();
            MySharedPreferencesManager.setDdaySave((int) count, setActivity.this);
            //MySharedPreferencesManager.setDdaySaveint((int) count,setActivity.this);
            MySharedPreferencesManager.setYear(myyear,setActivity.this);
            MySharedPreferencesManager.setMonth(mmonth,setActivity.this);
            MySharedPreferencesManager.setDay(mday,setActivity.this);
            return (int) count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}