package www.khj08.com.dateplan.ui.list_adapter;

import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private CheckBox checkBox;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }

}
