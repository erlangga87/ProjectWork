package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivityEdit extends AppCompatActivity{

    private DatabaseReference databaseReference;
    private List<ImageUploadEdit> imgList;
    private ListView lv;
    private ImageListAdapterEdit adapter;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_edit);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        //progres dialog ketika loading image
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading list image..");
        progressDialog.show();


        databaseReference = FirebaseDatabase.getInstance().getReference(Edit.FB_DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageUploadEdit img = snapshot.getValue(ImageUploadEdit.class);
                    imgList.add(img);
                }
                adapter = new ImageListAdapterEdit(ImageListActivityEdit.this, R.layout.image_item_edit, imgList);
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
