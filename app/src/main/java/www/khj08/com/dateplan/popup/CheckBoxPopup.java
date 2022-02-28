package www.khj08.com.dateplan.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.MySharedPreferencesManager;
import www.khj08.com.dateplan.utils.AutoScreen;

public class CheckBoxPopup extends Dialog{
    private boolean currentCheckBoxState;
    static Context mContext = null;
    private String TAG;
    private ImageView iv_check_box;

    private TextView tv_popup_msg = null;

    private LinearLayout layout_popup_1btn = null;
    private FrameLayout btn_popup_1btn_ok = null;
    private TextView tv_popup_1btn_ok = null;

    private LinearLayout layout_popup_2btn = null;
    private FrameLayout btn_popup_2btn_cancel = null;
    private TextView tv_popup_2btn_cancel = null;
    private FrameLayout btn_popup_2btn_ok = null;
    private TextView tv_popup_2btn_ok = null;

    public CheckBoxPopup.onClick Cancel_Click = null;
    public CheckBoxPopup.onClick OK_Click = null;

    public abstract interface onClick
    {
        public abstract void onClick();
    }

    public CheckBoxPopup(Context context)
    {

        super(context);
        // TODO Auto-generated constructor stub
        TAG = "Popup";
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkbox_dialog);
        initView();

//        LayoutInflater inflater = getLayoutInflater();
//        View popup_view = inflater.inflate(R.layout.popup, null);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setCancelable(false);
        AutoScreen.Adjust(mContext, getWindow().getDecorView(), 1);

        tv_popup_msg = (TextView)this.findViewById(R.id.tv_popup_msg);

        layout_popup_1btn = (LinearLayout)this.findViewById(R.id.layout_popup_1btn);
        btn_popup_1btn_ok = (FrameLayout)this.findViewById(R.id.btn_popup_1btn_ok);
        tv_popup_1btn_ok = (TextView)this.findViewById(R.id.tv_popup_1btn_ok);

        iv_check_box = (ImageView) findViewById(R.id.iv_check_box);
        layout_popup_2btn = (LinearLayout)this.findViewById(R.id.layout_popup_2btn);
        btn_popup_2btn_cancel = (FrameLayout)this.findViewById(R.id.btn_popup_2btn_cancel);
        tv_popup_2btn_cancel = (TextView)this.findViewById(R.id.tv_popup_2btn_cancel);
        btn_popup_2btn_ok = (FrameLayout)this.findViewById(R.id.btn_popup_2btn_ok);
        tv_popup_2btn_ok = (TextView)this.findViewById(R.id.tv_popup_2btn_ok);


        btn_popup_1btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBoxPopup.this.dismiss();

                if(OK_Click != null)
                {
                    OK_Click.onClick();
                }
            }
        });

        btn_popup_2btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                CheckBoxPopup.this.dismiss();

                if(OK_Click != null)
                {
                    OK_Click.onClick();
                }
            }
        });

        btn_popup_2btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBoxPopup.this.dismiss();

                if(Cancel_Click != null)
                {
                    Cancel_Click.onClick();
                }

            }
        });

        iv_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxImg(false);
            }
        });
    }

    @Override
    public void show()
    {
        super.show();
    }

    private void initView() {
        currentCheckBoxState = MySharedPreferencesManager.getCheckbox(mContext);
        updateCheckBoxImg(true);
    }

    private void updateCheckBoxImg(boolean isInitView) {

        iv_check_box = (ImageView) findViewById(R.id.iv_check_box);

        if(!isInitView) {
            MySharedPreferencesManager.setCheckbox(!currentCheckBoxState,mContext);
            currentCheckBoxState = !currentCheckBoxState;
        }

        Drawable drawable = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = currentCheckBoxState ?
                    iv_check_box.getResources().getDrawable(R.drawable.selector_check_box_on) :
                    iv_check_box.getResources().getDrawable(R.drawable.selector_check_box_off);

        } else {
            drawable = currentCheckBoxState ?
                    iv_check_box.getResources().getDrawable(R.drawable.selector_check_box_on) :
                    iv_check_box.getResources().getDrawable(R.drawable.selector_check_box_off);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            iv_check_box.setBackground(drawable);
        } else {
            iv_check_box.setBackgroundDrawable(ContextCompat.getDrawable(mContext, currentCheckBoxState ?
                    R.drawable.selector_check_box_on : R.drawable.selector_check_box_off));
        }
    }
}
