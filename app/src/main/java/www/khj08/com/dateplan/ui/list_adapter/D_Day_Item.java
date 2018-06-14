package www.khj08.com.dateplan.ui.list_adapter;


/**
 * Created by user on 2017-07-10.
 */

public class D_Day_Item {
    private String ddayStr ;
    private String titleStr ;
    private long descStr ;
    private int hjyear;
    private int hjmonth;
    private int hjday;

    public void setDday(String dday) { ddayStr = dday ;  }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(long desc) {
        descStr = desc ;
    }
    public void setYEAR(int year) { hjyear = year ;  }
    public void setMONTH(int month) { hjmonth = month ;  }
    public void setDAY(int day) { hjday = day ;  }

    public String getDday() {
        return this.ddayStr ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public long getDesc() { return this.descStr ; }
    public int getYEAR() { return this.hjyear ; }
    public int getMONTH() { return this.hjmonth ; }
    public int getDAY() { return this.hjday ; }
}
