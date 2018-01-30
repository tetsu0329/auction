package com.example.kuroro.auction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LandingPage extends AppCompatActivity {

    Button login, register;
    Notification.Builder notification;
    private static final int uniqueID = 45612;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        login = findViewById(R.id.button5);
        register = findViewById(R.id.button4);
        auth =  FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, Login.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, Register.class));
            }
        });
        if(null != auth.getCurrentUser()){
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

                        Intent intent = new Intent(LandingPage.this, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(LandingPage.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
                    Intent intent = new Intent(LandingPage.this, MainActivity.class);
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
        }
        else{
            //do nothing
        }
    }
}
