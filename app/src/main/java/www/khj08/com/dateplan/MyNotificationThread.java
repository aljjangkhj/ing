package www.khj08.com.dateplan;

import android.os.Handler;

/**
 * Created by user on 2017-07-11.
 */

public class MyNotificationThread extends Thread {

    //Handler 변수를 선언해서 서비스 클래스에 있는 MyHandler 클래스의 참조 변수를 보관
    private Handler mRefhandler;

    //핸들러의 실행 여부를 변수로 제어하기
    //true이면 현재 핸들러가 실행중
    //false이면 현재 핸들러가 중지
    private boolean mIshandlerRun = true;

    //생성자를 작성하기 : 서비스 클래스에서 핸들러 객체의 주소를 받는 함수
    public MyNotificationThread(Handler Refhandler){
        this.mRefhandler = Refhandler;
    }

    //run()함수 재정의
    @Override
    public void run(){
        //위에서  선언한 변수 mIshandlerRun 값이 참일동안 계속 일을 하는 명령문 작성
        while(mIshandlerRun == true){
            //sendEmptyMessage() 함수를 실행해서 화면에 노티피케이션을 출력
            mRefhandler.sendEmptyMessage(0);

            //sleep함수를 실행해서 잠간 현재 스레드 실행을 멈춥니다.
            try{
                Thread.sleep(2000); // 2초동안만
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        //while() 반복문을 벗어난 위치에서는 노티피케이션을 삭제하기위해서 sendEmptyMessage(1)함수를 실행
        mRefhandler.sendEmptyMessage(1);
    }

    //mIsHandlerRun 변수의 값을 false로 바꾸어주는 함수 만들기
    public void stopNoti(){
        //동기화 블럭을 사용해서 하나의 스레드만이 mIsHandlerRun 변수를 사용할 수 있도록 합니다.
        synchronized (this){
            mIshandlerRun = false;
        }
    }//Service 클래스에 있는 onDestroy()함수에서 실행
}
