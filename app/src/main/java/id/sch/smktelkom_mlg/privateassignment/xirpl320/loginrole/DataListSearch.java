package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by erlangga on 8/1/2017.
 */

public class DataListSearch extends ArrayAdapter<DataSearch> {

    private Activity context;
    private List<DataSearch> dataList;
    private TextView textSite;

    public DataListSearch(Activity context,List<DataSearch>dataList)
    {
        super(context,R.layout.list_item_search,dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_search,null,true);
        textSite = (TextView) listViewItem.findViewById(R.id.siteName);
        DataSearch data = dataList.get(position);
        textSite.setText(data.getDataSite());
        Intent intent = new Intent(context,MainActivity.class);
        return listViewItem;


    }

}
