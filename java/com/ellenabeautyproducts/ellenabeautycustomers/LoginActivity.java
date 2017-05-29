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
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText,passwordEditText;
    private Button loginB;
    private TextView registerUserTV;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        //initialize widgets
         if(SharedPrefManager.getInstance(this).isLoggedIn())
         {
             finish();;
             startActivity(new Intent(LoginActivity.this,MainActivity.class));
             return;
         }

        emailEditText = (EditText)findViewById(R.id.emailLogin);
        passwordEditText = (EditText)findViewById(R.id.passwordLogin);
        loginB = (Button)findViewById(R.id.buttonLogin);
        registerUserTV = (TextView)findViewById(R.id.registerTextView);

        progressDialog = new ProgressDialog(this);


        registerUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


    }
    private  void Login()
    {
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();


        StringRequest loginRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error"))
                    {
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj.getInt("user_id"),obj.getString("fname"),obj.getString("email"));
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else
                    {
                        final android.support.v7.app.AlertDialog.Builder  builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Alert Dialog").setMessage(obj.getString("message")).setIcon(android.R.drawable.ic_dialog_alert).
                                setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder.setCancelable(true);
                                    }
                                }).show();
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(loginRequest);

    }
}
