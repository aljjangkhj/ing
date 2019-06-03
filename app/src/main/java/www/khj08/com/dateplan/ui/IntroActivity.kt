package www.khj08.com.dateplan.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import www.khj08.com.dateplan.BaseActivity
import www.khj08.com.dateplan.R

class IntroActivity : BaseActivity() {

    private var thread: Thread? = null
    private var textView: TextView? = null
    private var ingIMG: ImageView? = null

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        onBackPressed()
        setContentView(R.layout.main_thread_layout)
        this.textView = this.findViewById(R.id.mainthread_text) as TextView
        ingIMG = findViewById(R.id.ingIMG) as ImageView

        val intent = Intent(mContext, MyNotificationService::class.java)

        if (MySharedPreferencesManager.getCheckbox(mContext)!!) {
            startService(intent)
        } else {
            stopService(intent)
            MySharedPreferencesManager.setCheckbox(false, mContext)
        }

        thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                } catch (ex: InterruptedException) {
                }

                // Run next activity
                val intent = Intent(mContext, MainActivity::class.java)
                intent.setClass(mContext, MainActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }
        thread!!.start()
    }

    override fun onBackPressed() {
        //취소버튼 동작안함
    }
}
