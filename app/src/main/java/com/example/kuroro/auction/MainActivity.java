package com.example.kuroro.auction;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;
    TextView textName, textEmail;
    ImageView profileImage;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    ConstraintLayout lyt;

    FloatingActionButton fabicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        lyt = findViewById(R.id.contentmain);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lyt.removeAllViews();

        ShowAllBid showallbidfrag = new ShowAllBid();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
        transaction.add(R.id.contentmain,showallbidfrag,"Upload Fragment");
        transaction.commit();

        fabicon = findViewById(R.id.fab);
        fabicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.removeAllViews();
                UploadBidding uploadbidfrag = new UploadBidding();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.contentmain,uploadbidfrag,"Upload Fragment");
                transaction.commit();
            }
        });

        textName = navigationView.getHeaderView(0).findViewById(R.id.textView1);
        textEmail = navigationView.getHeaderView(0).findViewById(R.id.textView2);
        profileImage = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        final String email = auth.getCurrentUser().getEmail();

        String currentuser = auth.getCurrentUser().getUid();
        Query search = mDatabaseRef.child("userinfo").orderByChild("userID").equalTo(currentuser);
        search.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    UserList user = snapshot.getValue(UserList.class);
                    textName.setText(user.getUserName());
                    textEmail.setText(email);
                    Glide.with(getApplicationContext()).load(user.getUserPhoto()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }
    public void displaySelectedScreen(int id){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        switch (id){
            case R.id.viewitems:
                lyt.removeAllViews();

                ShowAllBid showallbidfrag = new ShowAllBid();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.contentmain,showallbidfrag,"Upload Fragment");
                transaction.commit();

                break;
            case R.id.managebid:

                break;

            case R.id.recentoffer:

                break;

            case R.id.notification:

                break;

            case R.id.bidding:

                break;

            case R.id.bidding2:

                break;
            case R.id.bidding3:

                break;

            case R.id.bidding4:

                break;

            case R.id.Communication1:

                break;
            case R.id.Communication2:

                break;
            case R.id.Communication3:

                break;
        }
    }
}
