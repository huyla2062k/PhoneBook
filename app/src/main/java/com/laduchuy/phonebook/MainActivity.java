package com.laduchuy.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{
    ImageView btnAdd;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getFragment(MainFragment.newInstance());
        btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FixFragment.newInstance());
            }
        });



    }

    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }
}