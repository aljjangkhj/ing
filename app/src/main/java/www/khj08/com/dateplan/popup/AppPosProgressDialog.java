package www.khj08.com.dateplan.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.utils.AutoScreen;


/**
 * Created by bible5 on 2018-05-02.
 */

public class AppPosProgressDialog extends Dialog {

    private Context mContext;

    private ImageView iv_anim;

    public AppPosProgressDialog(@NonNull Context context) {
        super(context);

        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_progress);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setCancelable(false);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        AutoScreen.Adjust(mContext, getWindow().getDecorView(), 1);

        iv_anim = (ImageView) this.findViewById(R.id.iv_anim);
    }

    @Override
    public void show() {
        super.show();

        AnimationDrawable drawable = (AnimationDrawable) iv_anim.getBackground();
        drawable.start();
    }
}
