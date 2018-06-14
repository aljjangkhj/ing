package www.khj08.com.dateplan.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import www.khj08.com.dateplan.R;


public class TransparentProgressDialog extends Dialog
{
    private ImageView iv;

    public TransparentProgressDialog(Context context, int resourceIdOfImage)
    {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Bitmap img = Resize(context, 75, resourceIdOfImage);

        iv = new ImageView(context);
        //iv.setImageResource(resourceIdOfImage);
        iv.setImageBitmap(img);

        layout.addView(iv, params);
        addContentView(layout, params);
    }

    public Bitmap Resize(Context context, int size, int resid)
    {
        Bitmap Img= BitmapFactory.decodeResource(context.getResources(), resid);

        int viewHeight = size;

        float width = Img.getWidth();
        float height = Img.getHeight();

        if(height > viewHeight)
        {
            float percente = (float)(height / 100);
            float scale = (float)(viewHeight / percente);
            width *= (scale / 100);
            height *= (scale / 100);
        }

        Bitmap sizingBmp = Bitmap.createScaledBitmap(Img, (int) width, (int) height, true);
        return sizingBmp;
    }

    @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(3000);
        iv.setAnimation(anim);
        iv.startAnimation(anim);
    }
}