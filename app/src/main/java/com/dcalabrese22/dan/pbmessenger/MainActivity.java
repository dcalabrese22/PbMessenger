package com.dcalabrese22.dan.pbmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dcalabrese22.dan.pbmessenger.fragments.ChatFragment;
import com.dcalabrese22.dan.pbmessenger.fragments.MessagesListFragment;
import com.dcalabrese22.dan.pbmessenger.interfaces.MessageExtrasListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements MessageExtrasListener {

    public static final String MESSAGE_ID_KEY = "message_id_key";
    public static final String MESSAGE_USER_KEY = "message_user_key";
    private String mCorrespondent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MessagesListFragment fragment = new MessagesListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment)
                .commit();

        Log.d("MAIN", FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    public void sendMessageId(String id) {
        Log.d("MESSAGE ID: ", id);
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_ID_KEY, id);
        bundle.putString(MESSAGE_USER_KEY, mCorrespondent);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void getMessageUser(String user) {
        mCorrespondent = user;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
