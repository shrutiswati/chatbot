package com.shrutiswati.banasthalibot.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.shrutiswati.banasthalibot.R;
import com.shrutiswati.banasthalibot.db.tables.ChatTable;
import com.shrutiswati.banasthalibot.db.tables.UserTable;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
   Button b1,b2,b3,b4;



    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<ChatTable> results=realm.where(ChatTable.class).findAll();
        final ChatTable user=new ChatTable();
        View v= inflater.inflate(R.layout.fragment_settings, container, false);
        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        b1=(Button)view.findViewById(R.id.about_us1);

        b2=(Button)view.findViewById(R.id.background_color1);
        b3=(Button)view.findViewById(R.id.shruti);
        b4=(Button)view.findViewById(R.id.clear_chat_history);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AboutUs.class);
                startActivity(intent);


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder=new AlertDialog.Builder(v.getContext());
                LayoutInflater factory=LayoutInflater.from(v.getContext());
               // final View view1= factory.inflate(R.layout.fragment_settings,null);
               // a_builder.setView(view1);
                a_builder.setMessage("Are you sure you want to logout?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
                AlertDialog alert =a_builder.create();
                alert.show();

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    results.deleteAllFromRealm();
                    realm.commitTransaction();
                    realm.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor("#02AAB0"), Color.parseColor("#00CDAC")});
        view.findViewById(R.id.container_settings).setBackground(gradientDrawable);

    }

}





