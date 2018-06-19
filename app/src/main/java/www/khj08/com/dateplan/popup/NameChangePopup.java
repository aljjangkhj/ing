package www.khj08.com.dateplan.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.MySharedPreferencesManager;
import www.khj08.com.dateplan.utils.AutoScreen;

public class NameChangePopup extends Dialog {

    static Context mContext = null;
    private String TAG;

    private TextView tv_popup_msg = null;

    private LinearLayout layout_popup_1btn = null;
    private FrameLayout btn_popup_1btn_ok = null;
    private TextView tv_popup_1btn_ok = null;
    private TextView tv_name1 = null;
    private TextView tv_name2 = null;

    private LinearLayout layout_popup_2btn = null;
    private FrameLayout btn_popup_2btn_cancel = null;
    private TextView tv_popup_2btn_cancel = null;
    private FrameLayout btn_popup_2btn_ok = null;
    private TextView tv_popup_2btn_ok = null;

    private EditText et_name1 = null;
    private EditText et_name2 = null;

    public NameChangePopup.onClick Cancel_Click = null;
    public NameChangePopup.onClick OK_Click = null;

    public abstract interface onClick
    {
        public abstract void onClick();
    }

    public NameChangePopup(Context context)
    {

        super(context);
        // TODO Auto-generated constructor stub
        TAG = "Popup";
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edittext_dialog);

//        LayoutInflater inflater = getLayoutInflater();
//        View popup_view = inflater.inflate(R.layout.popup, null);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setCancelable(false);
        AutoScreen.Adjust(mContext, getWindow().getDecorView(), 1);

        tv_popup_msg = (TextView)this.findViewById(R.id.tv_popup_msg);

        layout_popup_1btn = (LinearLayout)this.findViewById(R.id.layout_popup_1btn);
        btn_popup_1btn_ok = (FrameLayout)this.findViewById(R.id.btn_popup_1btn_ok);
        tv_popup_1btn_ok = (TextView)this.findViewById(R.id.tv_popup_1btn_ok);

        layout_popup_2btn = (LinearLayout)this.findViewById(R.id.layout_popup_2btn);
        btn_popup_2btn_cancel = (FrameLayout)this.findViewById(R.id.btn_popup_2btn_cancel);
        tv_popup_2btn_cancel = (TextView)this.findViewById(R.id.tv_popup_2btn_cancel);
        btn_popup_2btn_ok = (FrameLayout)this.findViewById(R.id.btn_popup_2btn_ok);
        tv_popup_2btn_ok = (TextView)this.findViewById(R.id.tv_popup_2btn_ok);

        et_name1 = (EditText)findViewById(R.id.edit_name1);
        et_name2 = (EditText)findViewById(R.id.edit_name2);
        tv_name1 = (TextView) findViewById(R.id.first_name_dialog);
        tv_name2 = (TextView) findViewById(R.id.second_name_dialog);

        tv_name1.setText(MySharedPreferencesManager.getPic01(mContext));
        tv_name2.setText(MySharedPreferencesManager.getPic02(mContext));

        btn_popup_1btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NameChangePopup.this.dismiss();

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

                NameChangePopup.this.dismiss();

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
                NameChangePopup.this.dismiss();

                if(Cancel_Click != null)
                {
                    Cancel_Click.onClick();
                }

            }
        });
    }

    @Override
    public void show()
    {
        super.show();
    }

    public String getName1(){
        et_name1 = (EditText)findViewById(R.id.edit_name1);
        String a = et_name1.getText().toString();

        return a;
    }

    public String getName2(){
        et_name2 = (EditText)findViewById(R.id.edit_name2);
        String a = et_name2.getText().toString();

        return a;
    }

}
