package www.khj08.com.dateplan.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import www.khj08.com.dateplan.common.log;

/**
 * Created by user on 2017-08-09.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // api 28~ error
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            Intent i = new Intent(context, MyNotificationService.class);
//            context.startService(i);
//        }
    }
}