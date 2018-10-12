package www.khj08.com.dateplan.ui;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

import www.khj08.com.dateplan.R;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public static class UpdateClass extends Service{
        @Override
        public IBinder onBind(Intent intent){

            return null;
        }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            RemoteViews remoteViews = buildUpdate(this);

            ComponentName thisWidget = new ComponentName(this, NewAppWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, remoteViews);

            return super.onStartCommand(intent, flags, startId);
        }
        private RemoteViews buildUpdate(Context context) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            Intent intent = new Intent(context, Main3ThreadActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_btn, pendingIntent);

            Bitmap bitmap2 = MainActivity.StringToBitMap(MySharedPreferencesManager.getWomanPicture(context));
            Bitmap bitmap = MainActivity.StringToBitMap(MySharedPreferencesManager.getManPicture(context));
            //CharSequence widgetText = context.getString(R.string.appwidget_text);
            // Construct the RemoteViews object
            if (bitmap != null && bitmap.toString().length() != 0 && bitmap2 != null && bitmap2.toString().length() != 0) {
                bitmap = getCircularBitmap(bitmap);
                bitmap2 = getCircularBitmap(bitmap2);
                views.setImageViewBitmap(R.id.widget_Img01, bitmap);
                views.setImageViewResource(R.id.widget_img02, R.mipmap.mainthreadlogo);
                views.setImageViewBitmap(R.id.widget_Img03, bitmap2);
                //2018.03.28
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                calendar.set(year, month + 1, day);
//                long tday = calendar.getTimeInMillis() / 86400000;
//                long b = MySharedPreferencesManager.getLoveDay(context);
                Date mdate = new Date();
                long tt = mdate.getTime() / 86400000;
                Date mdate2 = new Date();
                mdate2.setYear(MySharedPreferencesManager.getYear(this)-1900);
                mdate2.setMonth(MySharedPreferencesManager.getMonth(this)-1);
                mdate2.setDate(MySharedPreferencesManager.getDay(this)-1);
                long tt2 = mdate2.getTime() / 86400000;
                views.setTextViewText(R.id.appwidget_text, "D+" + (tt - tt2));
                views.setTextViewText(R.id.widget_name1, MySharedPreferencesManager.getPic01(context));
                views.setTextViewText(R.id.widget_Name2, MySharedPreferencesManager.getPic02(context));
            }
            return views;
      }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        context.startService(new Intent(context, UpdateClass.class));

    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
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

}

