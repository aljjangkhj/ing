package www.khj08.com.dateplan;

/**
 * Created by Administrator on 2017-07-18.
 */

public class CalcResultItem {
    //String datelist, String ManMoney, String WomanMoney, String resultMoney
    private String datelist;
    private String MyManMoney;
    private String MyWomanMoney;
    private String MyResultMoney;

public void setMydateList(String mydate) {
        datelist = mydate;
    }

    public void setMyManMoney(String ManMoney) {
        MyManMoney = ManMoney;
    }

    public void setMyWomanMoney(String WomanMoney) {
        MyWomanMoney = WomanMoney;
    }

    public void setMyResultMoney(String ResultMoney) {
        MyResultMoney = ResultMoney;
    }

    public String getMydateList() {
        return this.datelist;
    }

    public String getMyManMoney() {
        return this.MyManMoney;
    }

    public String getMyWomanMoney() {
        return this.MyWomanMoney;
    }

    public String getMyResultMoney() {
        return this.MyResultMoney;
    }


}
