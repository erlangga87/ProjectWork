package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DetailSearchApprove extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<ImageUploadApp> imgList;
    private ListView lv;
    private ImageListAdapterApp2 adapter;
    private ProgressDialog progressDialog;
    ArrayList arrayList, imgName;
    public static final String FB_DATABASE_PATH = "image";
    String imge;
    private boolean boolean_save;
    private boolean boolean_permission;
    Bitmap bitmap;
    public static int REQUEST_PERMISSIONS = 1;
    //    Button btnDownload;
    private MarshMallowPermission marshMallowPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search_approve);
        //      btnDownload = (Button) findViewById(R.id.btnDownload) ;
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        arrayList = new ArrayList();
        imgName = new ArrayList();
        //progres dialog ketika loading image
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading list image..");
        progressDialog.show();
        Intent intent = getIntent();
        final String wkwk = intent.getStringExtra(Search.LISTN);
        arrayList.add(wkwk);
        Log.d("COBA", "wkwk" + wkwk);
        adapter = new ImageListAdapterApp2(DetailSearchApprove.this, R.layout.image_item_approve3, imgList);
        lv.setAdapter(adapter);
        this.setTitle(wkwk);
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Malang/image/1503543025677.jpg");


        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        Query quer = databaseReference.orderByChild("dataSite").equalTo(wkwk);

        //quer.startAt()
        quer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageUploadApp img = snapshot.getValue(ImageUploadApp.class);
                    imge = snapshot.getValue(ImageUploadEdit.class).getImgName();
                    imgList.add(img);
                    imgName.add(imge);
                    Log.d("ASASZX", "list" + imgName);
                    Log.d("JKJK", "Aqaq" + imgList);
                }
                adapter.notifyDataSetChanged();
                Log.d("popo", "lol" + adapter);
                //set adapter

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        marshMallowPermission = new MarshMallowPermission(DetailSearchApprove.this);



        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage(MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_LOAD_PROFILE);
        }

        /*btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage=FirebaseStorage.getInstance();
                // Create a storage reference from our app
//                StorageReference storageRef = storage.getReferenceFromUrl("gs://daftar-a20bf.appspot.com/");
                StorageReference imagesRef = storageRef.child("image");
                String fileName = "1502337244224.jpg";
                StorageReference spaceRef = imagesRef.child(fileName);
                String path = spaceRef.getPath();
                String name = spaceRef.getName();
                StorageReference storageRef = storage.getReference();
                StorageReference mountainsRef = storageRef.child("1502337244224.jpg");
                StorageReference mountainImagesRef = storageRef.child("image/1502337244224.jpg");


                mountainImagesRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        String path= Environment.getExternalStorageDirectory()+"/"+ "bismillah";
                        try {
                            FileOutputStream fos=new FileOutputStream(path);
                            fos.write(bytes);
                            fos.close();
                            Toast.makeText(DetailSearch.this, "Success!!!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(DetailSearch.this, e.toString(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(DetailSearch.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_LOAD_PROFILE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted successfully
                } else {
                    //permission denied  StorageReference islandRef = storageRef.child("malang/image/1503630088012.jpg");
                }
                break;
        }
    }
}