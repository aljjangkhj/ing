package www.khj08.com.dateplan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
//import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import www.khj08.com.dateplan.cipher.AES;
import www.khj08.com.dateplan.common.log;
import www.khj08.com.dateplan.config.AppPosPreferenceManager;
import www.khj08.com.dateplan.config.Config;
import www.khj08.com.dateplan.config.PollingStatus;
import www.khj08.com.dateplan.http.HttpPostSend;
import www.khj08.com.dateplan.popup.Popup;
import www.khj08.com.dateplan.ui.MainActivity;
import www.khj08.com.dateplan.utils.AutoScreen;
import www.khj08.com.dateplan.utils.SharedValue;
import www.khj08.com.dateplan.utils.Util;

public class BaseActivity extends AppCompatActivity
{
    public Context mContext = null;
    private Timer timer = null;
    private Timer TimeOut_timer = null;
    private final android.os.Handler handler = new android.os.Handler();

    static public long TimeOut_StartTime = 0;
    static public long TimeOut_CurrentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            setStatusBarBackground();

            Config.DEVICE_SCREEN_HEIGHT = displayMetrics.heightPixels;
            Config.DEVICE_SCREEN_WIDTH = displayMetrics.widthPixels;

            Config.InitConfig();
            super.onCreate(savedInstanceState);
            mContext = this;
            Config.TAG = this.getLocalClassName();
            Config.actList.add(this);
//            AppPosPreferenceManager.getFcmToken(mContext);

            ActionBar ab = getSupportActionBar();
        }
        catch (Exception e)
        {
            log.vlog(2,"BaseActivity onCreate Error : " + e.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        PollingStop();
        TimeOutStop();
        super.onDestroy();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        try
        {
            // 타이머 다시시작
            super.onResume();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        catch (Exception e)
        {
            log.vlog(2,"onResume onCreate Error : " + e.getMessage());
        }
    }

    public void init_autoscreen(int option)
    {
        try
        {
            AutoScreen.Adjust(mContext, getWindow().getDecorView(), option);
        }
        catch (Exception e)
        {
            log.vlog(2, "BaseActivity init_autoscreen Error : " + e.getMessage());
        }
    }

    public void MovetoMain()
    {
        ClearTop();
        Intent intent = new Intent(mContext, MainActivity.class);
        MoveToActivity(intent);
        finish();
    }

    public void BacktoMain()
    {
        ClearTop();
        Intent intent = new Intent(mContext, MainActivity.class);
        BackToActivity(intent);
        finish();
    }

    public void ClearTop()
    {
        for(int i = 0; i < Config.actList.size(); i++)
        {
            Config.actList.get(i).finish();
        }
    }

    public void MoveToMain(Intent intent)
    {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_center_from_right, R.anim.slide_left_from_center);
    }

    public void MoveToActivity(Intent intent)
    {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_center_from_right, R.anim.slide_left_from_center);
    }

    public void BackToActivity(Intent intent)
    {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_center_from_left, R.anim.slide_right_from_center);
    }

    public void FinishAnim()
    {
        finish();
        overridePendingTransition(R.anim.slide_center_from_left, R.anim.slide_right_from_center);
    }

    public void ShowPopup(String title, String msg)
    {
        if(!BaseActivity.this.isFinishing())
        {
            final Popup popup = new Popup(mContext, title, msg, "", "확인");
            popup.Cancel_Click = new Popup.onClick()
            {
                @Override
                public void onClick()
                {
                    //
                }
            };
            popup.OK_Click = new Popup.onClick()
            {
                @Override
                public void onClick()
                {
                    return;
                }
            };
            popup.show();
        }
    }

    public void ShowPopup_Finish(String msg, String errmsg)
    {
        if(!BaseActivity.this.isFinishing())
        {
            if(Config.RELEASE == false)
            {
                msg = msg + "\n" + errmsg;
            }

            final Popup popup = new Popup(mContext, "", msg, "", "닫기");
            popup.OK_Click = new Popup.onClick()
            {
                @Override
                public void onClick()
                {
                    finish();
                    return;
                }
            };
            popup.show();
        }
    }

    public void ShowPopup_APPEND()
    {
        if(!BaseActivity.this.isFinishing())
        {
            final Popup popup = new Popup(mContext, "종료", "잉ing 종료하시겠습니까?", "취소", "종료");
            popup.Cancel_Click = new Popup.onClick()
            {
                @Override
                public void onClick()
                {
                    //
                }
            };
            popup.OK_Click = new Popup.onClick()
            {
                @Override
                public void onClick()
                {
                    APP_END();
                }
            };
            popup.show();
        }
    }

    public void APP_END()
    {
        PollingStop();
        for (int i = 0; i < Config.actList.size(); i++)
        {
            Config.actList.get(i).finish();
        }

        Config.actList.clear();
        finish();
    }

    public String JSON_getString(JSONObject resData, String key)
    {
        String result = "";

        try
        {
            result = resData.getString(key);
        }
        catch (Exception e)
        {
            log.vlog(2, "Json_getString Exception : " + e.getMessage());
            result = "";
        }

        return result;
    }

    public int JSON_getInt(JSONObject resData, String key)
    {
        int result = 0;

        try
        {
            result = resData.getInt(key);
        }
        catch (Exception e)
        {
            log.vlog(2, "JSON_getInt Exception : " + e.getMessage());
            result = 0;
        }

        return result;
    }

    public void TimeOutStart()
    {
        TimeOut_StartTime  = System.currentTimeMillis();

        TimerTask mm = new TimerTask()
        {
            @Override
            public void run()
            {
                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        TimeOut_CurrentTime  = System.currentTimeMillis();
                        long left_time = Config.POLLING_TIMEOUT - (TimeOut_CurrentTime - TimeOut_StartTime);
                        log.vlog(2, "타임아웃 까지 남은 시간 : " + left_time + "ms");

                        // 폴링 타임아웃 체크
                        if (left_time <= 0)
                        {
                            TimeOutStop();
                            PollingStop();
                            PollingTimeOut();
                            return;
                        }
                    }
                };
                handler.post(runnable);
            }
        };

        TimeOut_timer = new Timer();
        TimeOut_timer.schedule(mm, 0, 1000);
    }

    public void TimeOutStop()
    {
        if(TimeOut_timer != null)
        {
            log.vlog(2, "TimeOut 중지됨");
            TimeOut_timer.cancel();
        }
    }

    public void PollingStart()
    {
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run()
            {
                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run() {
                        polling();
                    }
                };
                handler.post(runnable);
            }
        };

        timer = new Timer();
        timer.schedule(tt, 0, Integer.parseInt(SharedValue.pollingT));
    }

    public void PollingStop()
    {
        TimeOutStop();

        if(timer != null)
        {
            log.vlog(2, "폴링중지됨");
            timer.cancel();
        }
    }

    // 오버라이드 해서 써야함.
    public void PollingTimeOut()
    {
        log.vlog(2, "폴링 타임아웃");
    }

    // 오버라이드 해서 써야함.
    public void PollingProcess(String serverState, String orderSeq, String orderApprovalInfo, String appUserSeq, String orderDate, String amount)
    {
        if(serverState != null && serverState.equals("") == false)
        {
            PollingStatus.serverState = serverState.trim();

            if(Config.FUNC_TEST_MODE == true)
            {
                if(Config.FUNC_TEST_POLLING_STATE == 1){ PollingStatus.serverState = "idle"; }
                else if(Config.FUNC_TEST_POLLING_STATE == 2){ PollingStatus.serverState = "wait_payment"; }
                else if(Config.FUNC_TEST_POLLING_STATE == 3){
                    PollingStatus.serverState = "paymentCancel_wait";
                    PollingStatus.appUserSeq="111L";
                    PollingStatus.orderDate="20180111";
                }
                else if(Config.FUNC_TEST_POLLING_STATE == 4){ PollingStatus.serverState = "payment_fail"; }
                else if(Config.FUNC_TEST_POLLING_STATE == 5){ PollingStatus.serverState = "payment_success"; }
            }
        }

        if(orderSeq != null && orderSeq.equals("") == false)
        {
            PollingStatus.orderSeq = orderSeq.trim();
        }

        if(orderApprovalInfo != null && orderApprovalInfo.equals("") == false)
        {
            PollingStatus.orderApprovalInfo = orderApprovalInfo.trim();
        }

        if(appUserSeq != null && appUserSeq.equals("") == false)
        {
            PollingStatus.appUserSeq = appUserSeq.trim();
        }

        if(orderDate != null && orderDate.equals("") == false)
        {
            PollingStatus.orderDate = orderDate.trim();
        }

        if(amount != null && amount.equals("") == false)
        {
            PollingStatus.amount = amount.trim();
        }
    }

    public void polling()
    {
        HttpPostSend httpPostSend = new HttpPostSend(mContext);
        httpPostSend.show_progress = false;
        httpPostSend.CallBack = new HttpPostSend.OnResponse()
        {
            @Override
            public void Response(String result)
            {
                JSONObject Jobject = null;
                JSONObject resData = null;
                String resultCode = null;   //처리 결과 코드
                String resultMsg = null;    //처리 결과 메시지
                String serverState = "";
                String orderSeq = "";
                String orderApprovalInfo = "";
                String appUserSeq = "";
                String orderDate = "";
                String amount = "";

                try
                {
                    Jobject = new JSONObject(result);
                    resultCode = Jobject.getString("resultCode");
                    resultMsg = Jobject.getString("resultMsg");
                    resData = new JSONObject(Jobject.getString("resData"));

                    //메시지 유형 idle: 거래 없음 wait_payment: 결제금액 입력 활성화 요청 payment_success: 승인 결과 성공  payment_fail: 승인 결과 실패 paymentCancel_wait: 결제 취소 요청
                    serverState = JSON_getString(resData,"serverState");
                    orderSeq = JSON_getString(resData,"orderSeq");    // 거래 순번
                    SharedValue.orderSeq = orderSeq;
                    orderApprovalInfo = JSON_getString(resData,"orderApprovalInfo");//paymentCancel_wait  경우에만 전달(승인 시 필요한 결제 정보 (otc 등) )
                    appUserSeq = JSON_getString(resData,"appUserSeq"); //paymentCancel_wait  경우에만 전달  (금융사 앱 순번 단 현금카드 거래일 시 필요)
                    orderDate = JSON_getString(resData,"orderDate"); //paymentCancel_wait  경우에만 전달 (원거래일자)

                    if(appUserSeq.equals("") == false) {
                        amount = JSON_getString(resData, "amount");
                    }
                }
                catch (Exception e)
                {
                    log.vlog(2, "polling() Exception : " + e.getMessage());
                }

                if("000".equals(resultCode))
                {
                    PollingProcess(serverState, orderSeq, orderApprovalInfo, appUserSeq, orderDate, amount);
                }
                else if("100".equals(resultCode))
                {
                    PollingStop();
                }
                else {
                    log.vlog(2,"Polling 통신 실패 resultCode: " + resultCode);
                }
            }
        };

        try
        {
            String appKeyID = AppPosPreferenceManager.getAppKeyId(mContext);
            String pubKeyMod = AppPosPreferenceManager.getPubKeyMod(mContext);
            String accessToken = Util.createAccessToken(mContext);

            JSONObject sourceData = new JSONObject();

            sourceData.put("deviceID", Util.getUniqueDeviceID(getApplicationContext()));
            sourceData.put("fixedCode", SharedValue.fixedCode);
            sourceData.put("posSeq", AppPosPreferenceManager.getPosSeq(this));

            String encData = AES.AES_Encrypt(accessToken, sourceData.toString());

            httpPostSend.addParam("appKeyID",appKeyID);
            httpPostSend.addParam("accessToken", Util.encRSA("AQAB", Base64.decode(pubKeyMod, Base64.NO_WRAP), accessToken));
            httpPostSend.addParam("encData", encData);

            httpPostSend.execute("/polling");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setStatusBarBackground() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
    }

    public void Log_out()
    {
        AppPosPreferenceManager.setAutoLogin(mContext, false);
        HttpPostSend.clearCookie();
    }
}
