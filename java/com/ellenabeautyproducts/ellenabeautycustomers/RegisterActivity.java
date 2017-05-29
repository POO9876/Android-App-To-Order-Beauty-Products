package com.ellenabeautyproducts.ellenabeautycustomers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText fnameT,lnameT,emailT,phoneT,passwordT;
    private Button registerB;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        fnameT = (EditText)findViewById(R.id.fnameRegister);
        lnameT = (EditText)findViewById(R.id.lnameRegister);
        emailT = (EditText)findViewById(R.id.emailRegister);
        phoneT = (EditText)findViewById(R.id.phoneRegister);
        passwordT = (EditText)findViewById(R.id.passwordRegister);

        registerB = (Button)findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);

         registerB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               registerUsers();
             }
         });

    }



    private void registerUsers()
    {
        final String fname = fnameT.getText().toString().trim();
        final String lname = lnameT.getText().toString().trim();
        final String email = emailT.getText().toString().trim();
        final String pnumber = phoneT.getText().toString().trim();
        final String password = passwordT.getText().toString().trim();



            progressDialog.setMessage("Loading....");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        if(!jsonObject.getBoolean("error"))
                        {
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params = new HashMap<>();
                    params.put("fname",fname);
                    params.put("lname",lname);
                    params.put("email",email);
                    params.put("pnumber",pnumber);
                    params.put("password",password);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }



}
