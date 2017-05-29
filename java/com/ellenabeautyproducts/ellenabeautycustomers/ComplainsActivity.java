package com.ellenabeautyproducts.ellenabeautycustomers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class ComplainsActivity extends AppCompatActivity {

    private EditText titleET,complainET;
    private Button sendB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Complains");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_complains);


        titleET = (EditText)findViewById(R.id.editTextComplainTitle);
        complainET = (EditText)findViewById(R.id.editTextComplainContent);
        sendB = (Button)findViewById(R.id.buttonSendComplain);
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}
