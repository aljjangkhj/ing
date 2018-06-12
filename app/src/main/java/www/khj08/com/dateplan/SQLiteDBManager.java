package www.khj08.com.dateplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteDBManager {

    static final String DB_DIARY = "Diary.db";
    static final String TABLE_DIARY = "Diary";
    static final int DB_VERSION = 1;

    Context mContext = null;

    private static SQLiteDBManager mSQLiteDBManager = null;
    private SQLiteDatabase mDatabase = null;

    public static SQLiteDBManager getInstance(Context context) {

        if (mSQLiteDBManager == null) {
            mSQLiteDBManager = new SQLiteDBManager(context);
        }
        return mSQLiteDBManager;
    }

    private SQLiteDBManager(Context context) {
        mContext = context;

        mDatabase = context.openOrCreateDatabase(DB_DIARY, Context.MODE_PRIVATE, null);

        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DIARY + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT UNIQUE, " +
                "starttime TEXT, " +
                "endtime TEXT, " +
                "title TEXT, " +
                "content TEXT, " +
                "resulthour TEXT, " +
                "manmoney TEXT, " +
                "womanmoney TEXT, " +
                "resultmoney TEXT," +
                "finalminute INTEGER," +
                "bestphoto TEXT ); ");

        Log.v("mylog","SQLite Diary테이블 생성");

        /*addRowValue.put("manmoney", strManMoney);
        addRowValue.put("womanmoney",strWomanMoney);
        addRowValue.put("resultmoney",numberstr);*/
    }


    public long insert(ContentValues addRowValue) {

        // Log.v("mylog","SQLite Insert함수");
        return mDatabase.insert(TABLE_DIARY, null, addRowValue);
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        /*Cursor c = null;
        c = mDatabase.rawQuery("SELECT * FROME " + TABLE_DIARY + "WHERE date=" + "'" +  "'",  null);*/
        //Log.v("mylog", "c 값은 " + c);

        return mDatabase.query(TABLE_DIARY,columns,selection,selectionArgs,groupBy,having,orderBy);
    }
    public int  delete(String whereClause, String[] whereArgs){

        return mDatabase.delete(TABLE_DIARY, whereClause, whereArgs);
    }

    public int update(ContentValues updateRowValue, String whereClause,String[] whereArgs){

        return mDatabase.update(TABLE_DIARY, updateRowValue,whereClause,whereArgs);
    }
}

