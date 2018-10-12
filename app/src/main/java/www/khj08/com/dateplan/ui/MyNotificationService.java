package www.khj08.com.dateplan.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
//import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Date;

import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.common.log;
import www.khj08.com.dateplan.utils.Util;

/**
 * Created by user on 2017-07-11.
 */

public class MyNotificationService extends Service{

    //1. 전역변수들을 선언
    //1) NotificationManager 클래스를 사용하는 변수 선언: notify()함수 또는 cancel()함수 사용
    private NotificationManager mRefNotificationManager;
    //2) Notification객체를 메모리에 만들때 사용하는 Builder 클래스를 사용하는 변수 선언
    private NotificationCompat.Builder mRefNotificationCompatBuilder;
    //3) Thread 클래스 사용하는 변수 선언
    private MyNotificationThread mRefNotificationThread;
    private Context mContext;
    //핸들러 클래스 만들기
    //-> Handler 클래스르 상속받는 자식 클래스
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*
                sendEmptyMessage()함수로부터 받는 값에 따라서 처리할 명령문들을 실행
                */
            Bitmap bitmap2 = MainActivity.StringToBitMap(MySharedPreferencesManager.getWomanPicture(MyNotificationService.this));
            Bitmap bitmap = MainActivity.StringToBitMap(MySharedPreferencesManager.getManPicture(MyNotificationService.this));
            if(bitmap != null && bitmap.toString().length() != 0 && bitmap2 != null && bitmap2.toString().length() != 0) {
                if (msg.what == 0) {
                    //개발자가 정합니다. -> 화면에 Notification을 출력하는 명령문들을 작성
                   // Log.v("mylog", "msg.what ==0 in MyHandler");

                    //1.인텐트 객체를 생성하고 임시 변수에 저장합니다.
                    //-> 액티비티 클래스 정보를 저장하기 위한 변수(객체)
                    //new 클래스이름();
                    Intent refIntent = new Intent(MyNotificationService.this, Main3ThreadActivity.class);
                    //MyNotiService.this : Context 객체
                    //2. PendingIntent 객체를 생성: getActivity()함수를 사용
                    PendingIntent refPendingIntent = PendingIntent.getActivity(MyNotificationService.this, 0, refIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //MyNotiService.this : 현재 서비스 클래스의 컨텍스트
                    //0: 요청코드
                    //refIntent: 위에서 만든 intent객체
                    //PendingIntent.FLAG_UPDATE_CURRENT: 플래그 정수 값 : putExtra()함수를 사용하는 경우에 메모리에 intent 객체가 있는 경우 Intent 객체를 재사용하고
                    //putExtra()함수에서 사용한 데이터를 갱신
                    //빌더 객체를 만듭니다.


//                    Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");
                   // Log.v("mylog", "notifi if");
                    bitmap = getCircularBitmap(bitmap);
                    bitmap2 = getCircularBitmap(bitmap2);
                    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
                    remoteViews.setImageViewBitmap(R.id.notifi_Img01, bitmap);
                    remoteViews.setImageViewResource(R.id.notifi_img02, R.drawable.mainthreadlogo1);
                    remoteViews.setImageViewBitmap(R.id.notifi_Img03, bitmap2);
                    //2018.03.28
//                    Calendar calendar = Calendar.getInstance();
//                    int year = calendar.get(Calendar.YEAR);
//                    int month = calendar.get(Calendar.MONTH);
//                    int day = calendar.get(Calendar.DAY_OF_MONTH);
//                    calendar.set(year, month + 1, day);
//                    long tday = calendar.getTimeInMillis() / 86400000;
//                    long b = MySharedPreferencesManager.getLoveDay(MyNotificationService.this);
//                    Date mdate = new Date();
//                    long tt = mdate.getTime() / 86400000;
//                    Date mdate2 = new Date();
//                    mdate2.setYear(MySharedPreferencesManager.getYear(MyNotificationService.this)-1900);
//                    mdate2.setMonth(MySharedPreferencesManager.getMonth(MyNotificationService.this)-1);
//                    mdate2.setDate(MySharedPreferencesManager.getDay(MyNotificationService.this)-1);
//                    long tt2 = mdate2.getTime() / 86400000;

                    String today = Util.yyyyMMdd();
                    long saveDateDday = Util.diffOfDate(MySharedPreferencesManager.getLoveStartDay(MyNotificationService.this),today) + 1;
                    remoteViews.setTextViewText(R.id.notifi_text01, "D+" + saveDateDday + " ~잉ing");
                    remoteViews.setTextViewText(R.id.manName2, MySharedPreferencesManager.getPic01(MyNotificationService.this));
                    remoteViews.setTextViewText(R.id.womanName2, MySharedPreferencesManager.getPic02(MyNotificationService.this));

                    mRefNotificationCompatBuilder = new NotificationCompat.Builder(MyNotificationService.this);
                    mRefNotificationCompatBuilder.setSmallIcon(R.drawable.mainthreadlogo1);
                    mRefNotificationCompatBuilder.setContent(remoteViews);

                    //티커 텍스트 : 상태바에 노티피케이션이 추가된 후에 바로 출력되는 텍스트
                    mRefNotificationCompatBuilder.setTicker("알림이 표시됩니다.  [ 잉ing ]");
                    //위에서 만든 PendingIntent 객체를 사용하기
                    mRefNotificationCompatBuilder.setContentIntent(refPendingIntent);
                    //mRefNotificationCompatBuilder.setCategory("Category");
                    //mRefNotificationCompatBuilder.setContentInfo("ContentInfo");
                    //mRefNotificationCompatBuilder.setSubText("SubText");
                    //mRefNotificationCompatBuilder.setContentText("ContentText");

                    //진동 또는 빛(LED) 또는 소리(Sound)선택
                    //mRefNotificationCompatBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
                    //NotificationCompat>DEFAULT_VIBRATE : 진동
                    //빛: NotificationCompat.DEFAULT_LIGHTS
                    //소리 : NotificationCompat.DEFAULT_SOUND

                    //사용자가 선택하면 자동으로 화면에서 사라지게 하기x
                    //mRefNotificationCompatBuilder.setAutoCancel(true);
                    //build()함수를 실행해서 메모리에 생성
                    Notification refNotification = mRefNotificationCompatBuilder.build();
                    //notification이 항상위,항상고정됌
                    refNotification.flags = Notification.FLAG_ONGOING_EVENT;
                    //notify()함수를 실행해서 화면에 출력
                    mRefNotificationManager.notify(1, refNotification);
                    //1: 아이디
//                    Log.v("mylog", "notify()");


                } else if (msg.what == 1) {
                    //화면에 출력된 Notification을 삭제하는 명령문들을 작성
//                    Log.v("mylog", "msg.what == 1 in MyHandler");

                    //cancel()함수를 실행하기 : 강제로 노티피케이션을 삭제(화면에서 없애기)
                    mRefNotificationManager.cancel(1);
                    //1: notify()함수에서 사용한 정수 값과 일치
                }
            }else{
//                Log.v("mylog","notifi else");
                mRefNotificationThread.stopNoti();
                Toast.makeText(MyNotificationService.this, "메인 화면에 사진을 등록해야 합니다.", Toast.LENGTH_SHORT).show();
                MySharedPreferencesManager.setCheckbox(false,MyNotificationService.this);
            }
        }

    }
    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    //기본 생성자 함수
    public MyNotificationService() {

    }

    //bindService()함수를 사용하는 경우에는 명령문을 작성
    //-> onBind()함수를 사용

    //startService()함수를 사용하는 예제 작성(지금 예제)
    //-> onBind()함수를 사용하지 않음
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    //onStartCommand()함수 재정의하기 : startService()함수에 의해서 자동으로 호출되는 함수
    //-> 서비스 함수들 중에서 main()함수
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //1. 로그 남기기
//        Log.v("mylog", "onStartCommand");

        //2. 안드로이드 시스템이 가지고있는 Notification 서비스에 접근하기
        mRefNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //3. 핸들러 객체를 생성합니다.
        MyHandler refHandler = new MyHandler();

        //4. 위에서 만든 핸들러 객체를 Thread클래스에 전달하기
        mRefNotificationThread = new MyNotificationThread(refHandler);

        //5. 스레드 객체를 실행 가능한 상태로 바꾸기
        mRefNotificationThread.start();

        //6. 함수의 반환 값은 Service 클래스가 가지고 있는 START_STICKY
        return Service.START_STICKY;
        //서비스가 강제 종료되었을때 다시 재시작 해주는 플래그 상수이름
        // return super.onStartCommand(intent,flags,startId);
    }

    //가장 마지막에 종료되는 onDestroy()함수 재정의

    @Override
    public void onDestroy() {
        //super.onDestroy();
        //스레드 클래스에서 만든 stopNoti()함수를 실행해서 Notification을 삭제
        mRefNotificationThread.stopNoti();
        //가비지 컬렉터(메모리청소부)에 의해서 메모리에서 삭제되는 객체 지정
        mRefNotificationThread = null;
    }
}

