package id.sch.smktelkom_mlg.privateassignment.xirpl320.loginrole;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by erlangga on 7/24/2017.
 */

public class ImageListAdapter extends ArrayAdapter<UserInformation> {


    private Activity context;
    private int resource;
    private List <UserInformation> listData;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String rolee;

    public ImageListAdapter(Context context, int resource, List<UserInformation> objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        this.resource= resource;
        listData = objects;



    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();

        final View v = inflater.inflate(resource,null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName);
        TextView tvRole = (TextView) v.findViewById(R.id.tvImageSite);
        Button bLanjut = (Button) v.findViewById(R.id.bLanjut);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        final String[] role = new String[1];
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userID);


        final String Role = listData.get(position).getRole();

        tvName.setText(listData.get(position).getName());
        tvRole.setText( Role );
        final String logas = Role.toString();
        Log.d("COBADEBUG","logas:"+logas);

        bLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(logas.equals("Edit"))
                    {
                        Intent intent = new Intent(context, Edit.class);
                        context.startActivity(intent);
                    }
                    if (logas.equals("Viewer")){
                        Intent intent = new Intent(context, Search.class);
                        context.startActivity(intent);
                    }
                    if (logas.equals("Approver")){
                        Intent intent = new Intent(context, Search3.class);
                        context.startActivity(intent);
                    }

            }
        });

        return v;
    }

}
