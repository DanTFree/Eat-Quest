package com.example.nearbyrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class second extends AppCompatActivity {
    public Spinner spinner1, spinner2, spinner3;
    private Button btnMap, btnList;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }



    public void addListenerOnSpinnerItemSelection() {
        // spinner1 =  findViewById(R.id.spinner1);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectListener());

        spinner2 =  findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectListener());

        spinner3 =  findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new CustomOnItemSelectListener());


    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        // spinner1 =  findViewById(R.id.spinner1);

        spinner2 =  findViewById(R.id.spinner2);

        spinner3 = findViewById(R.id.spinner3);


        btnMap =  findViewById(R.id.btnMap);

        btnList =  findViewById(R.id.btnList);

        btnMap.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Toast.makeText(second.this, "OnClickListener : " +
//                                "\nSpinner 1 : " +
//                                String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : " +
                                String.valueOf(spinner2.getSelectedItem()) +
                                "\nSpinner 3 : " +
                                String.valueOf(spinner3.getSelectedItem())
                        , Toast.LENGTH_SHORT).show();


                if (v.getId() == btnMap.getId()) {
                    Intent intent = new Intent(second.this, MainActivity.class);
                    intent.putExtra("data",
                            // String.valueOf(String.valueOf(spinner1.getSelectedItem()))
                            String.valueOf(String.valueOf(spinner2.getSelectedItem())) +
                                    String.valueOf(String.valueOf(spinner3.getSelectedItem())));
                    startActivity(intent);
                }
            }
        });

        btnList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {



                Toast.makeText(second.this, "OnClickListener : " +
                                //   "\nSpinner 1 : "+
                                // String.valueOf(spinner1.getSelectedItem())+
                                "\nSpinner 2 : "+
                                String.valueOf(spinner2.getSelectedItem())  +
                                "\nSpinner 3 : "+
                                String.valueOf(spinner3.getSelectedItem())
                        ,Toast.LENGTH_SHORT).show();



                if(v.getId() == btnList.getId()) {
                    Intent intent = new Intent(second.this, RecyclerViewActivity.class);
                    intent.putExtra("data",
                            //     String.valueOf( String.valueOf(spinner1.getSelectedItem())+
                            String.valueOf(spinner2.getSelectedItem())  +
                                    String.valueOf(spinner3.getSelectedItem())
                    );
                    startActivity(intent);
                }


            }

        });
    }

    private class CustomOnItemSelectListener implements android.widget.AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}

