package com.ellenabeautyproducts.ellenabeautycustomers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TextView messages;
    private NavigationView navigationView;
    private ImageView orderIV,viewIV,confirmIV,complainsIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


        orderIV = (ImageView)findViewById(R.id.orderimageView);
        orderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,OrderItems.class));
            }
        });

        viewIV = (ImageView)findViewById(R.id.viewImageView);
        viewIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewActivity.class));
            }
        });

        confirmIV = (ImageView)findViewById(R.id.confirmImageView);
        confirmIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DeliveryActivity.class));
            }
        });
        complainsIV = (ImageView)findViewById(R.id.complainImageView);
        complainsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ComplainsActivity.class));
            }
        });


        //initialize the counter text views
        messages = (TextView)MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_messages));


        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        mToolbar.setTitle("Ellena");
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPref = this.getSharedPreferences("SINGLE_TAP_TARGET", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        boolean isSingleTapFinished = sharedPref.getBoolean("isFinished",false);
        if(!isSingleTapFinished) {


            TapTargetView.showFor(this,
                    TapTarget.forView(findViewById(R.id.fab),
                            "We Are Always At Your Service.",
                            "Tap the button below and give us a direct call our customer service are waiting for your call.")
                            .transparentTarget(true),
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional

                            editor.putBoolean("isFinished",true);
                            editor.commit();
                        }
                    });

        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Floating Action Button Click",Toast.LENGTH_LONG).show();
            }
        });

        setNavigationViewListner();
        initializeCountDrawer();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                Toast.makeText(getApplicationContext(),"You Clicked Home",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_account:
                Toast.makeText(getApplicationContext(),"You Clicked Account",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(getApplicationContext(),"You Clicked Settings",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;

            default:
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    //If you need the dynamic badge values, like updating the value from an API call or SQLite database, create a reusable method and update it on the OnStart() or OnResume() method of your Activity.
    private void initializeCountDrawer() {
        messages.setGravity(Gravity.CENTER_VERTICAL);
        messages.setTypeface(null, Typeface.BOLD);
        messages.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        messages.setText("99+");




    }

}
