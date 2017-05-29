package com.ellenabeautyproducts.ellenabeautycustomers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OrderItems extends AppCompatActivity {
     private Spinner spinner;

     private Button orderB;
     private EditText quantityET,dateET,productET;
     private JSONArray items;
     private ArrayList<String>country;
     int year_x,day_x,month_x;
     static final int DIALOG_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order");

        setContentView(R.layout.activity_order_items);


        //calendar input
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        month_x = cal.get(Calendar.MONTH);

        spinner = (Spinner)findViewById(R.id.spinner);
        quantityET = (EditText)findViewById(R.id.editTextQuantityOrder);
        dateET  = (EditText)findViewById(R.id.editTextOrderRequiredDate);
        orderB = (Button)findViewById(R.id.buttonOrder);
        productET = (EditText)findViewById(R.id.editTextProductSelected);
        country = new ArrayList<String>();

        loadspinner();
        Listener();


        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

        orderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeorder();
            }
        });


    }

    private void loadspinner()
    {
        StringRequest loadSpinner = new StringRequest(Request.Method.GET, OrderConfig.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject json = null;

                try {
                    json = new JSONObject(response);


                    items = json.getJSONArray(OrderConfig.JSON_ARRAY);

                    getRank(items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(loadSpinner);
    }
    private void getRank(JSONArray array)
    {
        for(int i=0; i<array.length();i++)
        {
            try {
                JSONObject o = array.getJSONObject(i);
                country.add(o.getString(OrderConfig.TAG_PRODUCT_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(OrderItems.this,android.R.layout.simple_spinner_dropdown_item,country));
    }
    private void Listener()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String product_name = spinner.getSelectedItem().toString();
                productET.setText(product_name);
                Toast.makeText(getApplicationContext(),"You Selected:"+ product_name,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Calendar
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID)
        {
            return new DatePickerDialog(OrderItems.this,dpickerListener,year_x,month_x,day_x);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year + 1;
            month_x = month + 1;
            day_x = dayOfMonth;
            dateET.setText(year_x + "/" + month_x + "/" + day_x);
            Toast.makeText(getApplicationContext(),year_x + "/" + month_x + "/" + day_x,Toast.LENGTH_LONG).show();

        }
    };

    private void makeorder()
    {

        final String customer_name = SharedPrefManager.getInstance(this).getKeyUserFname();
        final String customer_email = SharedPrefManager.getInstance(this).getKeyUserEmail();
        final String product_name = productET.getText().toString().trim();
        final String quantity_ordered = quantityET.getText().toString().trim();
        final String date_of_delivery = dateET.getText().toString().trim();
        StringRequest orderproducts = new StringRequest(Request.Method.POST, Constants.URL_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(OrderItems.this);
                    builder.setTitle("Success").setMessage(jsonObject.getString("message")).setIcon(android.R.drawable.ic_dialog_info).
                    setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    }).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("customer_name",customer_name);
                params.put("customer_email",customer_email);
                params.put("product_name",product_name);
                params.put("quantity_ordered",quantity_ordered);
                params.put("date_of_delivery",date_of_delivery);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(orderproducts);
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
