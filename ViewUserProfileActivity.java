package xyz.itechsyst.celebron;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import xyz.itechsyst.celebron.AccountActivity.LoginActivity;
import xyz.itechsyst.celebron.AccountActivity.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewUserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private Button btnview;
    private TextView username,t1;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        toolbar = (Toolbar) findViewById(R.id.action_toolbar);
        btnview = (Button) findViewById(R.id.view_button);
        username =  (TextView) findViewById(R.id.user_name);
        t1 = (TextView) findViewById(R.id.test);

        //action bar

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        toolbar.setTitleTextAppearance(this,R.style.TextApperance_TabsFont);


        //firebase auth & database access
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =firebaseDatabase.getReference(auth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                username.setText(userProfile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( ViewUserProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseauth) {
                FirebaseUser user =firebaseauth.getCurrentUser();
                if(user==null) {
                    startActivity(new Intent(ViewUserProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        username.setVisibility(View.GONE);
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t1.isShown()){
                    t1.setVisibility(View.GONE);
                    username.setVisibility(View.VISIBLE);
                }
                else{
                    username.setVisibility(View.GONE);
                    t1.setVisibility(View.VISIBLE);
                }

            }
        });


    }




    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {

                startActivity(new Intent(ViewUserProfileActivity.this, LoginActivity.class));
                finish();
            }
        }


    };
}

