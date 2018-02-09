package com.example.kuroro.auction;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
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
import com.google.firebase.database.ChildEventListener;
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
    Notification.Builder notification;
    private static final int uniqueID = 45612;

    FloatingActionButton fabicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        lyt = findViewById(R.id.contentmain);


        notification = new Notification.Builder(getApplicationContext());
        notification.setAutoCancel(true);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pushnotif");
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("pushnotif").child(auth.getCurrentUser().getUid());
        Query search5 = databaseReference.child(auth.getCurrentUser().getUid()).orderByChild("notifUser").equalTo(auth.getCurrentUser().getUid());
        search5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int incid = uniqueID + 1;
                    PushNotifList pushNotification = snapshot.getValue(PushNotifList.class);
                    String pd = pushNotification.getNotifComment();

                    //Toast.makeText(getApplicationContext(), pd, Toast.LENGTH_SHORT).show();

                    notification.setSmallIcon(R.drawable.logo);
                    notification.setTicker(pd);
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("Online Auction");
                    notification.setContentText(pd);

                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pendingIntent);

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(incid, notification.build());

                    snapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(getApplicationContext(),"Change",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(getApplicationContext(),"Move",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                lyt.removeAllViews();

                ManageOwn manageOwn = new ManageOwn();
                android.support.v4.app.FragmentManager manager2 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction2 = manager2.beginTransaction().addToBackStack(null);
                transaction2.add(R.id.contentmain,manageOwn,"Own Upload Fragment");
                transaction2.commit();
                break;

            case R.id.recentoffer:

                break;

            case R.id.notification:
                lyt.removeAllViews();

                OwnPushNotif ownPushNotif = new OwnPushNotif();
                android.support.v4.app.FragmentManager manager6 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction6 = manager6.beginTransaction().addToBackStack(null);
                transaction6.add(R.id.contentmain,ownPushNotif,"Notification Fragment");
                transaction6.commit();
                break;

            case R.id.bidding:
                lyt.removeAllViews();

                ShowEnglishBid showEnglishBid = new ShowEnglishBid();
                android.support.v4.app.FragmentManager manager3 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction3 = manager3.beginTransaction().addToBackStack(null);
                transaction3.add(R.id.contentmain,showEnglishBid,"English Bidding Fragment");
                transaction3.commit();
                break;

            case R.id.bidding2:
                lyt.removeAllViews();

                ShowDutchBid showDutchBid = new ShowDutchBid();
                android.support.v4.app.FragmentManager manager4 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction4 = manager4.beginTransaction().addToBackStack(null);
                transaction4.add(R.id.contentmain,showDutchBid,"Dutch Bidding Fragment");
                transaction4.commit();
                break;
            case R.id.bidding3:
                lyt.removeAllViews();

                ShowSealedBid showSealedBid = new ShowSealedBid();
                android.support.v4.app.FragmentManager manager5 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction5 = manager5.beginTransaction().addToBackStack(null);
                transaction5.add(R.id.contentmain,showSealedBid,"Sealed Bidding Fragment");
                transaction5.commit();
                break;

            case R.id.bidding4:
                lyt.removeAllViews();

                showBuyMe buyMeNow = new showBuyMe();
                android.support.v4.app.FragmentManager manager8 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction8 = manager8.beginTransaction().addToBackStack(null);
                transaction8.add(R.id.contentmain,buyMeNow,"Buy Me Now Fragment");
                transaction8.commit();
                break;

            case R.id.Communication1:

                break;
            case R.id.Communication2:
                lyt.removeAllViews();

                ContactUs contactUs = new ContactUs();
                android.support.v4.app.FragmentManager manager7 = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction7 = manager7.beginTransaction().addToBackStack(null);
                transaction7.add(R.id.contentmain,contactUs,"Contact Us fragment");
                transaction7.commit();
                break;

            case R.id.Communication3:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
        }
    }
}
