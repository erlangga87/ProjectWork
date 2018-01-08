package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by erlangga on 7/24/2017.
 */

public class ImageListAdapterApp extends ArrayAdapter<ImageUploadApp> {

    public static final String STS = "sts";
    public static final String ASE = "ase";
    private String name,url;

    private Activity context;
    private int resource;
    private List <ImageUploadApp> listImage;
    String ImgUrl;
    DatabaseReference databaseRef;

    public ImageListAdapterApp(Context context, int resource, List<ImageUploadApp> objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        this.resource= resource;
        listImage = objects;


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource,null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        TextView tvSite = (TextView) v.findViewById(R.id.tvImageSite);
        TextView tvStatus = (TextView) v.findViewById(R.id.tvImageStatus);
        Button bAppr = (Button) v.findViewById(R.id.bAppr);
        Button bRejt = (Button) v.findViewById(R.id.bRejt);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        ImgUrl = listImage.get(position).getUrl();



        tvName.setText(listImage.get(position).getName());
        tvSite.setText("Site :"+listImage.get(position).getDataSite());
        tvStatus.setText("Status :"+listImage.get(position).getStatus());


        final String name = listImage.get(position).getName();
        final String key = listImage.get(position).getKey();
        final String dataSite = listImage.get(position).getDataSite();

        final String url = listImage.get(position).getUrl();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        databaseRef = FirebaseDatabase.getInstance().getReference("image");

        Glide.with(context).load(listImage.get(position).getUrl()).into(img);

        bAppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "Approved";
                ImageUploadApp dataa = new ImageUploadApp(status,url.toString(),name.toString(),dataSite.toString(),key.toString());
                databaseRef.child(key).setValue(dataa);
            }
        });
        bRejt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshoot :dataSnapshot.getChildren())
                        {
                            dataSnapshoot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Message","onSuccess : Delete File");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Message", "onFailure: did not delete file");
                    }
                });


            }
        });

        return v;
    }



}
