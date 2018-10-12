package www.khj08.com.dateplan.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import www.khj08.com.dateplan.R;

public class SideMenuBar extends LinearLayout {
    Context mContext;

    FrameLayout layout_sidemenu_bg;
    LinearLayout layout_sidemenu_close;

    public boolean opened = false;

    Animation slide_right = null;
    Animation slide_left = null;

   public SideMenuBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        if(!isInEditMode())
        {
            initView();
        }
    }

    public SideMenuBar(Context context)
    {
        super(context);
        mContext = context;
        if(!isInEditMode())
        {
            initView();
        }
    }

    public void refresh()
    {
        //
    }

    public void show()
    {
        opened = true;

        SideMenuBar.this.startAnimation(slide_left);
        SideMenuBar.this.setVisibility(View.VISIBLE);
        slide_left.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                SideMenuBar.this.setBackgroundColor(Color.parseColor("#2f000000"));
            }
            public void onAnimationStart(Animation animation)
            {
                SideMenuBar.this.setBackgroundColor(Color.parseColor("#00ff0000"));
            }

            public void onAnimationRepeat(Animation animation){;}
        });
        bringToFront();

    }

    public void hide()
    {
        opened = false;
        SideMenuBar.this.startAnimation(slide_right);
        slide_right.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                SideMenuBar.this.setVisibility(View.GONE);
            }
            public void onAnimationStart(Animation animation)
            {
                SideMenuBar.this.setBackgroundColor(Color.parseColor("#00ff0000"));
            }
            public void onAnimationRepeat(Animation animation){;}
        });
    }

    private void initView()
    {
        slide_right = AnimationUtils.loadAnimation(mContext, R.anim.slide_right);
        slide_left = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.sidemenu, this, false);
        addView(v);

        layout_sidemenu_bg = (FrameLayout)v.findViewById(R.id.layout_sidemenu_bg);
        layout_sidemenu_bg.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {hide();}
        });

        layout_sidemenu_close = (LinearLayout)v.findViewById(R.id.layout_sidemenu_close);
        layout_sidemenu_close.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {hide();}
        });
    }

}
