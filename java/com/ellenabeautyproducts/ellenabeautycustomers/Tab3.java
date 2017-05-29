package com.ellenabeautyproducts.ellenabeautycustomers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


//Our class extending fragment
public class Tab3 extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private DisappAdapter myAdapter;
    private List<ListItem> listItems;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);


        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerViewApproved);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();
        loadRecyclerView();

        return rootView;

    }
    private void loadRecyclerView()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.APPROVED_ORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("orders");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        ListItem item = new ListItem(o.getString("customer_name"),o.getString("product_name"),o.getString("image"),o.getString("date_of_delivery"),o.getString("quantity_ordered"));
                        listItems.add(item);
                    }
                    adapter = new DisappAdapter(listItems,getActivity());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}