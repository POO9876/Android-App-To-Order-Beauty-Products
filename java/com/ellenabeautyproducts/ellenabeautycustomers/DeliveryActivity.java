package com.ellenabeautyproducts.ellenabeautycustomers;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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

public class DeliveryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DeliveryAdapter MyAdapter;
    private List<ListItem> listItems;
    String searchString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Confirm Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_delivery);




        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewDelivery);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        loaddelivery();
    }


    private void loaddelivery()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data please wait...");
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
                    adapter = new DeliveryAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);
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
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<ListItem>filteredModelList = filter(listItems,newText);
        if(filteredModelList.size()>0)
        {
            DeliveryAdapter.setFilter(filteredModelList);
            return true;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //search method

    private List<ListItem> filter(List<ListItem> models, String query) {

        query = query.toLowerCase();
        this.searchString = query;

        final List<ListItem> filteredModelList = new ArrayList<>();
        for (ListItem model : models) {
            final String text = model.getCustomer().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        adapter = new DeliveryAdapter(filteredModelList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView searchText = (TextView)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchText.setTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHintTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHint("Search Product");
        searchView.setOnQueryTextListener(this);

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
