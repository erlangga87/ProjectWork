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

import java.util.List;

/**
 * Created by erlangga on 7/24/2017.
 */

public class ImageListAdapterEdit extends ArrayAdapter<ImageUploadEdit> {

    private String name,url;

    private Activity context;
    private int resource;
    private List <ImageUploadEdit> listImage;

    public ImageListAdapterEdit(Context context, int resource, List<ImageUploadEdit> objects) {
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
        ImageView img = (ImageView) v.findViewById(R.id.imgView);

        tvName.setText(listImage.get(position).getName());
        tvSite.setText("Site :"+listImage.get(position).getDataSite());
        tvStatus.setText("Status :"+listImage.get(position).getStatus());

        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        return v;
    }

}
