package com.dcalabrese22.dan.pbmessenger.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;


public class NewMessageFragment extends Fragment {

    private AutoCompleteTextView mName;


    public NewMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_message, container, false);
        mName = (AutoCompleteTextView) rootView.findViewById(R.id.et_new_message_to);
        final EditText subject = (EditText) rootView.findViewById(R.id.et_new_message_subject);
        final EditText body = (EditText) rootView.findViewById(R.id.et_new_message_body);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_new_message);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("conversations");

        final DatabaseReference uidReference = reference.child(userId);
        final DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mName.getText().toString().equals("") || subject.getText().toString().equals("")
                        || body.getText().toString().equals("")) {
                    Toast.makeText(getContext(), R.string.missing_fields, Toast.LENGTH_SHORT).show();
                } else {
                    String key = uidReference.push().getKey();
                    Long timeStamp = new Date().getTime();
                    PbConversation conversation = new PbConversation(null,
                            subject.getText().toString(),mName.getText().toString(),
                            body.getText().toString(), "null", "sent", timeStamp, key);

                    uidReference.child(key).setValue(conversation);
//                    Date now = Calendar.getInstance().getTime();
//                    Long time = new Date().getTime();
//
//                    PbMessage message = new PbMessage("0",
//                            body.getText().toString(),
//                            now.toString(),
//                            FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0],
//                            "sent",
//                            time);
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("0", message);
//                    AppWidgetManager manager = AppWidgetManager.getInstance(getContext());
//                    int[] ids = manager.getAppWidgetIds(new ComponentName(getContext()
//                            .getPackageName(), PbAppWidget.class.getName()));
//                    manager.notifyAppWidgetViewDataChanged(ids, R.id.lv_widget_conversations);
//                    messagesRef.child(key).updateChildren(map);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        final List<String> autoCompleteNames = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        
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
