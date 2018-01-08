package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Approver2 extends AppCompatActivity {

    public static final String LISTN = "listn";
    // Declare Variables
    private ListView listView;
    ArrayList<DataSearch> dataList;
    DatabaseReference databaseReference;
    SearchView seachView;
    private String temp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        databaseReference = FirebaseDatabase.getInstance().getReference("Data");
        listView = (ListView) findViewById(R.id.list_view);
        dataList = new ArrayList<>();
        seachView = (SearchView) findViewById(R.id.search);

        final ArrayAdapter adapter = new ArrayAdapter(Approver2.this,R.layout.list_item_search,R.id.siteName,dataList);
        listView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshoot : dataSnapshot.getChildren()) {
                    DataSearch data = dataSnapshoot.getValue(DataSearch.class);
                    dataList.add(data);
                    Log.d("COBA","datalist"+dataList);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        seachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent ia = new Intent(Approver2.this, DetailApprove.class);
                ia.putExtra(LISTN, dataList.get(i).toString());
                startActivity(ia);

            }
        });
    }
}
