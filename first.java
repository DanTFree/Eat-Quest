package com.example.eatquest3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class first extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        button =  findViewById(R.id.first);


        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == button.getId()) {
            Intent intent = new Intent(this, second.class);
            startActivity(intent);
        }
    }
}
