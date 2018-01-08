package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Approver extends AppCompatActivity{

    private DatabaseReference databaseReference;
    private List<ImageUploadApp> imgList;
    private ListView lv;
    private ImageListAdapterApp adapter;
    private ProgressDialog progressDialog;
    public static final String FB_DATABASE_PATH="image";
    //BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_app);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        //progres dialog ketika loading image
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading list image..");
        progressDialog.show();
        Intent intent = getIntent();
        String wkwk = intent.getStringExtra(ImageListAdapterApp.STS);
        Log.d("COBA","App"+wkwk);
        /*bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.approve){
                    Intent intent = new Intent(Approver.this, Approver.class);
                    startActivity(intent);

                }
                else if (item.getItemId()==R.id.List){
                    Intent intent = new Intent(Approver.this, Search2.class);
                    startActivity(intent);

                }
                return false;
            }
        });*/


        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                imgList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageUploadApp img = snapshot.getValue(ImageUploadApp.class);
                    imgList.add(img);
                }
                adapter = new ImageListAdapterApp(Approver.this, R.layout.image_item_app, imgList);
                //set adapter
                lv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });


    }

}