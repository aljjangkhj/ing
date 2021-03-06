package www.khj08.com.dateplan.ui.list_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.ui.MySharedPreferencesManager;
import www.khj08.com.dateplan.utils.AutoScreen;

/**
 * Created by Administrator on 2017-07-18.
 */

public class CalcResultAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<CalcResultItem> CalcResultListView = new ArrayList<CalcResultItem>();
    private TextView mRefDateList,mRefMyManMoney,mRefMyWomanMoney,mRefMyResultMoney,first_name_calc,second_name_calc;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return CalcResultListView.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos = position;
        final Context context = parent.getContext();
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_calc_result02, parent, false);
            AutoScreen.Adjust(context,convertView,1);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        mRefDateList = (TextView) convertView.findViewById(R.id.MyDateList) ;
        mRefMyManMoney = (TextView) convertView.findViewById(R.id.MyResultMan) ;
        mRefMyWomanMoney = (TextView)convertView.findViewById(R.id.myResultWoman);
        mRefMyResultMoney = (TextView)convertView.findViewById(R.id.myResultMoney);
        first_name_calc = (TextView)convertView.findViewById(R.id.first_name_calc);
        second_name_calc = (TextView)convertView.findViewById(R.id.second_name_calc);

        CalcResultItem MyCalcResultItem = CalcResultListView.get(position);
        // 아이템 내 각 위젯에 데이터 반영
        mRefDateList.setText(MyCalcResultItem.getMydateList());
        mRefMyManMoney.setText(MyCalcResultItem.getMyManMoney());
        mRefMyWomanMoney.setText(MyCalcResultItem.getMyWomanMoney());
        mRefMyResultMoney.setText(MyCalcResultItem.getMyResultMoney());
        if (!MySharedPreferencesManager.getPic01(context).equals("길동")){
            first_name_calc.setText(MySharedPreferencesManager.getPic01(context)+"의 지출:");
        }
        if (!MySharedPreferencesManager.getPic02(context).equals("둘리")){
            second_name_calc.setText(MySharedPreferencesManager.getPic02(context)+"의 지출:");
        }
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return CalcResultListView.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String datelist, String ManMoney, String WomanMoney, String resultMoney){
        CalcResultItem item = new CalcResultItem();

        item.setMydateList(datelist);
        item.setMyManMoney(ManMoney);
        item.setMyWomanMoney(WomanMoney);
        item.setMyResultMoney(resultMoney);

        CalcResultListView.add(item);
    }
}
