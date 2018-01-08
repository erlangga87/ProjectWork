package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class Edit extends AppCompatActivity{

    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int PICK_CAMERA_REQUEST = 233;
    private ImageView imageV;
//    private Button bUpload,bPilih,bShow;
    private Spinner sKet,sSite;
    private Uri filePath;
    ArrayList<ImageUploadEdit> dataList;
    ArrayList<keterangan>dataKet;
    public static final String status="Wait";

    private StorageReference storageReference;
    private DatabaseReference databaseReference,dRef,dataRef;

    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";
    private String imageExt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        dRef=FirebaseDatabase.getInstance().getReference("Data");
        dataRef = FirebaseDatabase.getInstance().getReference("keterangan");


        imageV = (ImageView) findViewById(R.id.iView);
  //      bUpload = (Button) findViewById(R.id.bUpload);
    //    bPilih = (Button) findViewById(R.id.bPilih);
      //  bShow = (Button) findViewById(R.id.bShow);
        sKet = (Spinner) findViewById(R.id.sKeterangan);
        sSite = (Spinner) findViewById(R.id.sSite);
        dataList = new ArrayList<>();
        dataKet = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(Edit.this,android.R.layout.simple_spinner_item,dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSite.setAdapter(adapter);
        final ArrayAdapter adapterr = new ArrayAdapter(Edit.this,android.R.layout.simple_spinner_item,dataKet);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sKet.setAdapter(adapterr);

        //bUpload.setOnClickListener(this);
        //bPilih.setOnClickListener(this);
        //bShow.setOnClickListener(this);


        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshoot : dataSnapshot.getChildren()) {
                    ImageUploadEdit data = dataSnapshoot.getValue(ImageUploadEdit.class);
                    dataList.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshoot : dataSnapshot.getChildren()) {
                    keterangan dataa = dataSnapshoot.getValue(keterangan.class);
                    dataKet.add(dataa);
                }
                adapterr.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.approve:

                        break;

                    case R.id.List:
                        Intent intent1 = new Intent(Edit.this, Search2.class);
                        startActivity(intent1);
                        break;

                }


                return false;
            }
        });*/




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.choose:
                SelectImage();
                return true;
            case R.id.upload:
                uploadFile();
                return true;
            case R.id.list:
                Intent intent = new Intent(Edit.this, Search.class);
                startActivity(intent);
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    private void fileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Pilih gambar"),PICK_IMAGE_REQUEST);
    }
    private void cameraChooser()
    {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, PICK_CAMERA_REQUEST);
    }
    private void SelectImage()
    {
        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
        builder.setTitle("Tambah Gambar");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           if (items[i].equals("Camera")){
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intent,PICK_CAMERA_REQUEST);
           }
           else if (items[i].equals("Gallery")){
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent,"Pilih gambar"),PICK_IMAGE_REQUEST);
           }
           else if(items[i].equals("Cancel"))
           {
               dialogInterface.dismiss();
           }
            }
        });
        builder.show();
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference abc = storageReference.child(sSite.getSelectedItem().toString());
            final String jos =  System.currentTimeMillis() + "." + getImageExt(filePath);
            StorageReference riversRef = abc.child(FB_STORAGE_PATH +jos);
            Log.d("ghghgh","ref"+riversRef);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            String uploadId = databaseReference.push().getKey();
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            ImageUploadEdit imageUpload = new ImageUploadEdit(sKet.getSelectedItem().toString(),taskSnapshot.getDownloadUrl().toString(),sSite.getSelectedItem().toString(),status.toString(),uploadId.toString(), jos);

                            //save database

                            databaseReference.child(uploadId).setValue(imageUpload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null )
        {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageV.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null )
        {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageV.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void showFile() {
        Intent i = new Intent(Edit.this, ImageListActivityEdit.class);
        startActivity(i);
    }


    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));


    }
}
