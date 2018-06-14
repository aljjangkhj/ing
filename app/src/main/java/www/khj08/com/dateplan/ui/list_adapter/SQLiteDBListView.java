package www.khj08.com.dateplan.ui.list_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.list_adapter.SQLiteDBListViewItem;

/**
 * Created by user on 2017-07-17.
 */

public class SQLiteDBListView extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<SQLiteDBListViewItem> SQLListViewitem = new ArrayList<SQLiteDBListViewItem>() ;
    private TextView Date_List;
    private TextView Date_Title;
    private TextView Date_Content;
    private ImageView myImageView;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return SQLListViewitem.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos = position;
        final Context context = parent.getContext();
        //Toast.makeText(context, "Position: "+pos, Toast.LENGTH_SHORT).show();
        //Log.v("mylog","position: "+pos);
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.datelistview02, parent, false);
        }
        ViewGroup.LayoutParams params = convertView.getLayoutParams();

        params.height = 200;
        convertView.setLayoutParams(params);
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        Date_List = (TextView) convertView.findViewById(R.id.Date_list_TextView) ;
        Date_Title = (TextView) convertView.findViewById(R.id.Date_title_TextView) ;
        myImageView = (ImageView)convertView.findViewById(R.id.Date_list_ImageVIew);

       // Date_Content = (TextView) convertView.findViewById(R.id.Date_content_TextView);
        SQLiteDBListViewItem sqLiteDBListViewItem01 = SQLListViewitem.get(position);
        // 아이템 내 각 위젯에 데이터 반영
        Date_List.setText(sqLiteDBListViewItem01.getDatelist());
        Date_Title.setText(sqLiteDBListViewItem01.getDateTitle());
        myImageView.setImageBitmap(sqLiteDBListViewItem01.getMyimageview());
        Date_Title.setSelected(true);
       // Date_Content.setText(sqLiteDBListViewItem01.getDateContent());
       // Date_Content.setSelected(true);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return SQLListViewitem.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int id, Bitmap myBitmap,String datelist, String datetitle) {
        SQLiteDBListViewItem item = new SQLiteDBListViewItem();

        item.setImageview(myBitmap);
        item.setid(id);
        item.setDatelist(datelist);
        item.setDateTitle(datetitle);
       // item.setDateContent(datecontent);
        SQLListViewitem.add(item);
    }
}