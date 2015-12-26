package com.example.myhp.hitthebasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by my hp on 12/26/2015.
 */
public class Start extends Activity implements View.OnClickListener {
    Button a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitit);
        a=(Button)findViewById(R.id.button2);
        b=(Button)findViewById(R.id.button);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button2:
            Class ch= null;
            try {
                ch = Class.forName("com.example.myhp.hitthebasket.Hitit");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent p=new Intent(Start.this,ch);
                startActivity(p);
            break;
            case R.id.button:
                finish();
        }
    }

}
