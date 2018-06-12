package www.khj08.com.dateplan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3ThreadActivity extends Activity {

    private Thread thread;
    private TextView textView;
    private Animation animation;
    private ImageView ingIMG;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        onBackPressed();
        //Log.v("mylog","start Main3ThreadActivity");
        this.setContentView(R.layout.main_thread_layout);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BMJUA_ttf.ttf");
      //  Log.v("mylog","setContentView 실행");
        this.textView = (TextView)this.findViewById(R.id.mainthread_text);
        ingIMG = (ImageView)findViewById(R.id.ingIMG);
        textView.setTypeface(typeface);
        animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        ingIMG.setAnimation(animation);
        thread = new Thread() {
            @Override
            public void run() {
                try {
//                    synchronized (this) {
//                        wait(2000);
//                    }
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }

                // Run next activity
                Intent intent = new Intent(Main3ThreadActivity.this,MainActivity.class);
                intent.setClass(Main3ThreadActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
//                overridePendingTransition(android.R.animator.fade_in, android.R.animator.fade_out);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


            }
        };
        thread.start();
    }
    @Override public void onBackPressed() {

    }
}
