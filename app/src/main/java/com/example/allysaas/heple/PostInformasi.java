package com.example.allysaas.heple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class PostInformasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_informasi);
    }

    public void chooseStatusBerjualan(View v){
        boolean choosen = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.radio_menetap:
                if(choosen){

                }
                break;
            case R.id.radio_berkeliling:
                if(choosen){

                }
        }
    }
}
