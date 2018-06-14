package www.khj08.com.dateplan.ui.list_adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RadioButton;

/**
 * Created by user on 2017-07-17.
 */

public class SQLiteDBListViewItem {

    private Bitmap myimageview;
    private String datelistStr ;
    private String datetitleStr ;
    private int id;
    //private String datecontentStr;
    public void setid(int setid){ id = setid;}
    public void setImageview(Bitmap imageview){ myimageview = imageview;}
    //public void setDateContent(String datecontent){ datecontentStr = datecontent; }
    public void setDatelist(String datelist) { datelistStr = datelist ;  }
    public void setDateTitle(String datetitle) {
        datetitleStr = datetitle ;
    }

    //public String getDateContent(){ return this.datecontentStr; }
    public String getDatelist() {
        return this.datelistStr ;
    }
    public String getDateTitle() {
        return this.datetitleStr ;
    }
    public Bitmap getMyimageview(){ return this.myimageview; }
    public int getid() {return this.id ;}

}