package com.dcalabrese22.dan.pbmessenger.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.dcalabrese22.dan.pbmessenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NewMessageFragment extends Fragment {

    private AutoCompleteTextView mName;


    public NewMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_message, container, false);
        mName = (AutoCompleteTextView) rootView.findViewById(R.id.et_new_message_to);

        final List<String> autoCompleteNames = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("conversations");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();
        Query userNameQuery = reference.child(userId);
        userNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PbConversation conversation = snapshot.getValue(PbConversation.class);
                    String userName = conversation.getUser();
                    autoCompleteNames.add(userName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, autoCompleteNames);
        mName.setAdapter(adapter);




        return rootView;
    }

}
