package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StreamDownloadTask;

import java.util.List;

/**
 * Created by erlangga on 7/24/2017.
 */

public class ImageListAdapterSearch2 extends ArrayAdapter<ImageUploadSearch> {

    private String name,url;

    private Activity context;
    private int resource;
    private List <ImageUploadSearch> listImage;
    String ImgUrl;
    DatabaseReference databaseRef;

    public ImageListAdapterSearch2(Context context, int resource, List<ImageUploadSearch> objects) {
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
        TextView tvStatus = (TextView) v.findViewById(R.id.tvImageStatus);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        ImgUrl = listImage.get(position).getUrl();

        tvName.setText(listImage.get(position).getName());;
        tvStatus.setText("Site :"+listImage.get(position).getStatus());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);

        return v;
    }
    public static StreamDownloadTask getStream(String url) {
        return FirebaseStorage.getInstance().getReferenceFromUrl(url).getStream();
    }



}
