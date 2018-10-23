package com.bzh.sportrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.bzh.sportrecord.module.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /*@Inject
    Chef chef;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,HomeActivity.class));
    }

    public void showCook(View view){
        /*Toast.makeText(this, chef.cook(), Toast.LENGTH_SHORT).show();
        System.out.println(chef.cook());*/
    }
}
