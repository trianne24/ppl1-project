package com.anjass.raihan.monica20.Home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjass.raihan.monica20.Authentication.LandingActivity;
import com.anjass.raihan.monica20.CreateCommittee;
import com.anjass.raihan.monica20.FragmentMainActivity;
import com.anjass.raihan.monica20.R;
import com.anjass.raihan.monica20.Splash;
import com.anjass.raihan.monica20.TestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ImageButton icon_close;
    private NavigationView navigationView;
    private View header;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    String userData_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Firebase code
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // In context code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (currentUser != null)
            userData_string = "Userdata \n" +
                "Email: " +currentUser.getEmail();
        else
            userData_string = "Account signed out";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, userData_string, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Close button
        header = navigationView.getHeaderView(0);
        icon_close = (ImageButton) header.findViewById(R.id.icon_close);
        icon_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        // Link button to TEDxUnpad
        LinearLayout item2 = (LinearLayout) header.findViewById(R.id.item2);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), FragmentMainActivity.class);
                    startActivity(i);
                    drawer.closeDrawer(GravityCompat.START);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout createCommittee = (LinearLayout) header.findViewById(R.id.itemNew);
        createCommittee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), CreateCommittee.class);
                    startActivity(i);
                    drawer.closeDrawer(GravityCompat.START);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* Override methods for Drawer Logic */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            // Handle the logout action
            mAuth.signOut();

            finish();
            startActivity(new Intent(getApplicationContext(), LandingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_out) {
            // Handle the logout action

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
