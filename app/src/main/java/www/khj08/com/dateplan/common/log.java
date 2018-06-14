package www.khj08.com.dateplan.common;

import android.util.Log;

import www.khj08.com.dateplan.config.Config;


public class log
{
    public static void vlog(int type, String msg) {
        if (type == 1) {
            if (Config.RELEASE == false) {
                Log.e(Config.TAG, msg);
            }
        } else if (type == 2) {
            Log.e(Config.TAG, msg);
        }
    }
}
