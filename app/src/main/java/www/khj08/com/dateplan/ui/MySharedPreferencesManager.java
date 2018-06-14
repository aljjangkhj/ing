package www.khj08.com.dateplan.ui;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 2017-06-30.
 */

public class MySharedPreferencesManager {

    //사랑을 시작한날을 저장할 장소
    public static void setStartLoveDay(int year, int month, int day, Context refContext) {
        SharedPreferences refSharedPreferences = refContext.getSharedPreferences("StartLoveData", Context.MODE_PRIVATE);
        //3. edit()함수를 실행해서 프레퍼런스 파일에 데이터를 쓸 준비를 합니다.
        SharedPreferences.Editor refEditor = refSharedPreferences.edit();
        //4.putString 함수를 사용해서 사용자가 입력한 이름을 프레퍼런스에 저장
        //if(year > 0) {
        refEditor.putString("ymd", year + "년 " + month + "월 " + day + "일 부터 ~잉ing");
        refEditor.commit();
        Log.v("mylog", "이름 저장 완료");
    }

    //사랑을 시작한날을 불러오는 함수
    public static String getStartLoveDay(Context refContext) {
        //1.프레퍼런스로부터 데이터(이름)을 읽어와서 EditText 창에 출력하기
        SharedPreferences refTempSharedPreferences = refContext.getSharedPreferences("StartLoveData", Context.MODE_PRIVATE);
        //2. 프레퍼런스 파일에 저장되어있는 데이터 가져오기
        String year = refTempSharedPreferences.getString("ymd", "설정 → 만남 시작한날 정하기");
        return year;
    }
    //오늘 날짜 시간변환된 함수를 저장하는 함수
    public static void setToday(long today, Context context){
        SharedPreferences refSharedPreferences = context.getSharedPreferences("todayData2", Context.MODE_PRIVATE);
        SharedPreferences.Editor refEditor = refSharedPreferences.edit();
        refEditor.putLong("today", today);
        refEditor.commit();
    }
    //오늘 날짜 시간변환된 함수를 불러오는 함수
    public static long getToday(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("todayData2", Context.MODE_PRIVATE);
        long today = sharedPreferences.getLong("today", 0L);
        return today;
    }
    public static  void setLoveDay(long loveDay, Context context){
        SharedPreferences refSharedPreferences = context.getSharedPreferences("lovedayData2", Context.MODE_PRIVATE);
        SharedPreferences.Editor refEditor = refSharedPreferences.edit();
        refEditor.putLong("loveday", loveDay);
        refEditor.commit();
    }
    public static long getLoveDay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("lovedayData2", Context.MODE_PRIVATE);
        long lovedate = sharedPreferences.getLong("loveday", 0L);
        return lovedate;
    }

    //D-Day 날짜 저장 장소
    public static void setDdaySave(int dday, Context context) {
        SharedPreferences refSharedPreferences = context.getSharedPreferences("DdayData", Context.MODE_PRIVATE);
        SharedPreferences.Editor refEditor = refSharedPreferences.edit();
        refEditor.putString("dday", "D+" + dday + " ~잉");
        refEditor.commit();
        Log.v("mylog", "dday 저장완료");
    }

    //D-Day 날짜 불러오기 함수
    public static String getDdaySave(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("DdayData", Context.MODE_PRIVATE);
        String dday = sharedPreferences.getString("dday", "만나기 시작한 날짜를 설정해주세요");
        return dday;

    }
    /*//dday를 int 정수로만 저장하는 함수
    public static void setDdaySaveint(int dday, Context context) {
        SharedPreferences refSharedPreferences = context.getSharedPreferences("DdayDataint", Context.MODE_PRIVATE);
        SharedPreferences.Editor refEditor = refSharedPreferences.edit();
        refEditor.putInt("ddayint", dday);
        refEditor.commit();
        Log.v("mylog", "dday 저장완료");
    }
    //dday를 정수로만 불러오는 함수
    public static int getDdaySaveint(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("DdayDataint", Context.MODE_PRIVATE);
        int dday = sharedPreferences.getInt("ddayint", 0);
        return dday;
    }*/
    //사진1 저장함수
    public static void setManPicture(String str, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("ManPictureData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img1str", str);
        editor.commit();
        Toast.makeText(context, "사진1이 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
    //사진1불러오기함수
    public static String getManPicture(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ManPictureData", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("img1str", "사진1을 설정해주세요");
        return str;
    }
    //사진2 저장함수
    public static void setWomanPicture(String str, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("WomanPictureData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img2str", str);
        editor.commit();
        Toast.makeText(context, "사진2가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
    //사진2 불러오기함수
    public static String getWomanPicture(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WomanPictureData", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("img2str", "사진2를 설정해주세요");
        return str;
    }
    //체크박스 저장함수
    public static void setCheckbox(Boolean check, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkbox", check);
        editor.commit();
    }
    //체크박스 불러오기함수
    public static Boolean getCheckbox(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", setActivity.MODE_PRIVATE);
        Boolean check = pref.getBoolean("checkbox", false);
        return check;
    }
    //저장할 연도를 int 함수로 저장하는함수
    public static void setYear(int year, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("yearData",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("year", year);
        editor.commit();
    }
    //저장할 월(Month)를 int 함수로 저장하는함수
    public static void setMonth(int month, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("monthData",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("month", month);
        editor.commit();
    }
    //저장할 날(day)를 int 함수로 저장하는함수
    public static void setDay(int day, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("dayData",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("day", day);
        editor.commit();
    }
    //연도를 불러오는 함수
    public static int getYear(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("yearData",context.MODE_PRIVATE);
        int year = sharedPreferences.getInt("year", 0);
        return year;
    }
    //월을 불러오는 함수
    public static int getMonth(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("monthData",context.MODE_PRIVATE);
        int month = sharedPreferences.getInt("month", 0);
        return month;
    }
    //날을 불러오는함수
    public static int getDay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("dayData",context.MODE_PRIVATE);
        int day = sharedPreferences.getInt("day", 0);
        return day;
    }
    //사진1 editText 저장함수
    public static void setPic01(String str, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("picNameData01",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("picName01",str);
        editor.commit();
    }
    //사진2 editText 저장함수
    public static void setPic02(String str, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("picNameData02",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("picName02",str);
        editor.commit();
    }
    //사진1 editText 불러오기 함수
    public static String getPic01(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("picNameData01",context.MODE_PRIVATE);
        String picName01 = sharedPreferences.getString("picName01","사진1");
        return  picName01;
    }
    //사진2 editText 불러오기 함수
    public static String getPic02(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("picNameData02",context.MODE_PRIVATE);
        String picName01 = sharedPreferences.getString("picName02","사진2");
        return  picName01;
    }
    //메인앱바 사진을 저장하는 함수
    public static void setMainPicture(String str, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mainPictureData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mainPic", str);
        editor.commit();
        Toast.makeText(context, "프로필 사진이 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
    //메인앱바 사진을 불러오기함수
    public static String getMainPicture(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mainPictureData", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("mainPic", "사진2를 설정해주세요");
        return str;
    }
    //명언 남기기 editText 저장함수
    public static void setMainEdit(String str, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("mainEditData",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mainEdit",str);
        editor.commit();
    }
    //명언 남기기 editText 불러오기 함수
    public static String getMainEdit(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("mainEditData",context.MODE_PRIVATE);
        String picName01 = sharedPreferences.getString("mainEdit","한 줄 명언을 남겨보세요!");
        return  picName01;
    }

    public static void saveColor(int color,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("colorData",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("color",color);
        editor.commit();
    }
    /*public static void saveColor(String color,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("colorData2",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("color2",color);
        editor.commit();
    }*/
    public static int getSaveColor(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("colorData",context.MODE_PRIVATE);
        int saveColor = sharedPreferences.getInt("color", 0);
        return saveColor;
    }
}
