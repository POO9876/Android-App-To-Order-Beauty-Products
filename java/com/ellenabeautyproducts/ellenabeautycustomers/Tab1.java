package com.ellenabeautyproducts.ellenabeautycustomers;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


//Our class extending fragment
public class Tab1 extends Fragment implements SearchView.OnQueryTextListener{


    private static final String URL_DATA = "http://192.168.56.1/elenaz/get_staff.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private PendingAdapter myAdapter;
    private List<ListItem> listItems;
    String searchString="";


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        loadRecyclerView();

        return rootView;
    }
    private void loadRecyclerView()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PENDING_ORDERS, new Response.Listener<String>() {
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
                    adapter = new PendingAdapter(listItems,getActivity());
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



    //Search Method
    private List<ListItem>filter(List<ListItem>models,String query)
    {
        query = query.toLowerCase();
        this.searchString = query;

        final List<ListItem>filteredModelList = new ArrayList<>();
        for(ListItem model:models)
        {
            String text = model.getCustomer().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        adapter = new PendingAdapter(filteredModelList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<ListItem> filteredModelList = filter(listItems,newText);
        if(filteredModelList.size()>0)
        {
            PendingAdapter.setFilter(filteredModelList);
            return true;
        }
        else
        {
            Toast.makeText(getActivity(),"Not Found",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        searchitem.getActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        TextView searchText = (TextView)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchText.setTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHintTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHint("Search Product");
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}