package www.khj08.com.dateplan.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.list_adapter.SQLiteDBListView;

public class DateListView extends BaseActivity {

    private SQLiteDBManager mSQLiteDBManager = null;
    private ListView listview01;
    private SQLiteDBListView listViewAdapter;
    private int idResult;
    private Bitmap bitmap;
    private TextView font1;
    private TextView font2;
    private TextView font3;
    private String mRefDate;
    String DateDelete;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.datelistview);
        init_autoscreen(1);
        listViewAdapter = new SQLiteDBListView();
        listview01 = (ListView) this.findViewById(R.id.datelistview01);
        listview01.setAdapter(listViewAdapter);
        font1 = (TextView) this.findViewById(R.id.fonts01);
        font2 = (TextView) this.findViewById(R.id.fonts02);
        font3 = (TextView) this.findViewById(R.id.fonts03);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf");
        font1.setTypeface(typeface);
        font2.setTypeface(typeface);
        font3.setTypeface(typeface);
        final List<String> users = new ArrayList<>();

        mSQLiteDBManager = SQLiteDBManager.getInstance(this);
        String[] columns = new String[]{"_id", "date", "title", "bestphoto"};
        Cursor c = mSQLiteDBManager.query(columns, null, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                mRefDate = c.getString(1);
                String mRefTitle = c.getString(2);
                String mRefbestphoto = c.getString(3);

                bitmap = StringToBitMap(mRefbestphoto);
                listViewAdapter.addItem(id, bitmap, mRefDate, mRefTitle);
                users.add(c.getString(1));
            }
            Log.v("mylog","CXHR2- "+ users);
            c.close();
        } else {
            Toast.makeText(this, "일기를 쓰지않아 등록이 되어있지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        listview01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id = listViewAdapter.getItemId(position);
//                idResult = (int) id + 1;
                String [] additem = new String[users.size()];
                for(int i = 0; i < users.size(); i++){
                    additem[i] = users.get(i).toString();
                    String selection = "date='" + additem[i] + "'";
                    String[] columns = new String[]{"date"};
                    Cursor c = mSQLiteDBManager.query(columns, selection, null, null, null, null);
                    if(id == i) {
                        if (c != null) {
                            while (c.moveToNext()) {
                                mRefDate = c.getString(0);
                                Log.v("mylog", "date: " + mRefDate);

                                AlertDialog.Builder alert = new AlertDialog.Builder(DateListView.this);
                                alert.setIcon(R.mipmap.mainlogo01);
                                alert.setMessage(mRefDate + "의 일기입니다.");
                                DateDelete = mRefDate;
                                alert.setTitle("일기 불러오기/삭제하기");
                                alert.setPositiveButton("불러오기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(DateListView.this, LoadDatePickerActivity.class);
                                        intent.putExtra("위치값", mRefDate);
                                        Log.v("mylog", "mRefDate: " + mRefDate);
                                        //   Toast.makeText(DateListView.this, "위치값은 " + idResult, Toast.LENGTH_SHORT).show();
                                        MoveToActivity(intent);
                                        finish();
                                    }
                                });
                                alert.setNegativeButton("삭제하기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(DateListView.this);
                                        alert.setTitle("일기를 삭제합니다.");
                                        alert.setIcon(R.mipmap.deletecolor);
                                        alert.setMessage("정말로 삭제 하시겠습니까?!" + "\n" + "삭제된 데이터는 복구할 수 없습니다.");
                                        alert.setPositiveButton("응!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(DateListView.this, "삭제합니다.", Toast.LENGTH_SHORT).show();
                                                String deleteManager = "date = '" + DateDelete + "';";
                                                mSQLiteDBManager.delete(deleteManager, null);
                                                finish();
                                            }
                                        });
                                        alert.setNegativeButton("아니!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(DateListView.this, "추억을 간직하세요♥", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        alert.show();
                                    }
                                });
                                alert.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alert.show();
                            }
                            c.close();
                        }
                    }
                }
//                Log.v("mylog","additem: "+ additem[0]);
//                Log.v("mylog","additem: "+ additem[1]);
//                Log.v("mylog","additem: "+ additem[2]);
                // Toast.makeText(DateListView.this, "포지션값은: "+id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.synkList) {
//            listViewAdapter.notifyDataSetChanged();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.loadlist, menu);
//        return true;
//    }
}


