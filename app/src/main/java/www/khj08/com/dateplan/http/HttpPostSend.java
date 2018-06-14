package www.khj08.com.dateplan.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.common.log;
import www.khj08.com.dateplan.config.Config;
import www.khj08.com.dateplan.popup.AppPosProgressDialog;
import www.khj08.com.dateplan.popup.Popup;
import www.khj08.com.dateplan.ui.MainActivity;

public class HttpPostSend extends AsyncTask<String, Void, String>
{
    Context mContext = null;
    JSONObject json = null;
    public OnResponse CallBack = null;

    public boolean show_progress = true;
    private AppPosProgressDialog dialog = null;

    public String SEND_URL = "";

    static String m_cookies = "";

    HashMap<String, String> params = new HashMap<String, String>();

    public abstract interface OnResponse
    {
        public abstract void Response(String result);
    }

    public HttpPostSend(Context context)
    {
        mContext = context;
    }

    @Override
    protected void onPreExecute()
    {
        if(show_progress)
        {
            dialog = new AppPosProgressDialog(mContext);
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params)
    {
        // TODO Auto-generated method stub

        String result = "";

        result = HttpPostConn(params[0]);

        log.vlog(2, "HttpPostConn result : " + result);

        return result;
    }

    @Override
    protected void onPostExecute(final String json)
    {
        closeProgress();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(json.contains("Error.0"))
                {
                    ShowFailedDialog(json);
                }
                else
                {
                    boolean sucess = false;

                    try
                    {
                        JSONObject jsonObject = new JSONObject(json);

                        String resultCode = jsonObject.getString("resultCode");

                        if(resultCode.equals("100"))
                        {
                            // 세션만료
                            ShowPopup_SessionTerminate();
                            sucess = true;
                        }
                        else
                        {
                            sucess = true;
                        }

                    }
                    catch (Exception e)
                    {
                        log.vlog(2, "HttpSend onPostExecute Exception : " + e.getMessage());
                        ShowFailedDialog(e.getMessage());
                    }

                    if(sucess == true)
                    {
                        if(CallBack != null)
                        {
                            CallBack.Response(json);
                        }
                    }
                }
            }
        }, 100);
    }


    private void ShowPopup_SessionTerminate()
    {
        if(mContext !=null && ((Activity)mContext).isFinishing() == false)
        {
            ((BaseActivity)mContext).PollingStop();
            Popup popup = new Popup(mContext, "", "Session 정보가 없습니다.\n메인화면으로 이동하겠습니다.", "", "확인");
            popup.OK_Click = new Popup.onClick() {
                @Override
                public void onClick() {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((BaseActivity) mContext).MoveToActivity(intent);
                    ((Activity) mContext).finish();
                }
            };
            popup.show();

            // 메인으로 이동
        }
    }

    public void saveCookie( HttpURLConnection conn)
    {
        Map<String, List<String>> imap = conn.getHeaderFields( ) ;

        if( imap.containsKey( "Set-Cookie" ) )
        {
            List<String> lString = imap.get( "Set-Cookie" ) ;
            for( int i = 0 ; i < lString.size() ; i++ ) {
                m_cookies = lString.get( i ) ;
            }

            log.vlog(2, "HttpPostConn saveCookie true : " + m_cookies);
        }
    }

    private void closeProgress()
    {
        try
        {
            if(dialog != null)
            {
                if (dialog.isShowing())
                {
                    if( ((Activity)mContext).isFinishing() == false)
                    {
                        dialog.dismiss();
                    }
                }
            }
        }
        catch (Exception e)
        {
            Log.e(Config.TAG,"HttpPostSend closeProgress error : " + e.getMessage());
        }
    }

    public void addParam(String key, String value)
    {
        params.put(key,value);
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public String HttpPostConn(String cmd)
    {
        HttpsURLConnection httpcon;

        String result = null;
        SEND_URL = cmd;
        String Server = "";

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                        return myTrustedAnchors;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        try
        {
            Server = Config.SERVER_IP + cmd;
            log.vlog(2, "HttpPostConn URL : " + Server);
            URL ServerURL = new URL(Server);

            httpcon = (HttpsURLConnection)ServerURL.openConnection();

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            httpcon.setDefaultSSLSocketFactory(sc.getSocketFactory());
            httpcon.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            if(m_cookies.length() > 1)
            {
                httpcon.setRequestProperty("Cookie", m_cookies );
                log.vlog(2, "HttpPostConn SEND  Cookie: " + m_cookies);
            }

            httpcon.setUseCaches(true);
            httpcon.setDoInput(true);
            httpcon.setConnectTimeout(10000);
            httpcon.setReadTimeout(10000);
            httpcon.setRequestMethod("POST");

            //Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));
            writer.close();
            os.close();

            saveCookie(httpcon);

//            for (Map.Entry<String, List<String>> entries : httpcon.getHeaderFields().entrySet()) {
//                String values = "";
//                for (String value : entries.getValue()) {
//                    values += value + ",";
//                }
//                Log.d("SKO Response", entries.getKey() + " - " +  values );
//            }
//
//            Log.d("SKO ", "++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            result = "Error.001 : " + e.getMessage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = "Error.002 : " + e.getMessage();
        }
        catch(Exception e)
        {
            result = "Error.003 : " + e.getMessage();
        }

        return result;
    }

    private void ShowFailedDialog(String Error)
    {
        if(mContext !=null && ((Activity)mContext).isFinishing() == false)
        {
            ((BaseActivity)mContext).PollingStop();
            String msg = mContext.getResources().getString(R.string.data_failed);

            if(Config.RELEASE == false)
            {
                String Server = Config.SERVER_IP + SEND_URL;
//            msg = msg + "\n"
//                    + Server + "\n" + Error;
            }

            final Popup popup = new Popup(mContext, "통신오류", msg, "", "확인");

            popup.OK_Click = new Popup.onClick()
            {
                @Override
                public void onClick()
                {
                    ((BaseActivity)mContext).APP_END();
                }
            };
            popup.show();
        }
    }

    public void ClearTop()
    {
        for(int i = 0; i < Config.actList.size(); i++)
        {
            Config.actList.get(i).finish();
        }
    }

    public static void clearCookie()
    {
        m_cookies = "";
    }
}

