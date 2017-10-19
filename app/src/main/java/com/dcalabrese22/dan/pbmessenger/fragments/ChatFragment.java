package com.dcalabrese22.dan.pbmessenger.fragments;


import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dcalabrese22.dan.pbmessenger.ChatViewHolder;
import com.dcalabrese22.dan.pbmessenger.MainActivity;
import com.dcalabrese22.dan.pbmessenger.Objects.PbMessage;
import com.dcalabrese22.dan.pbmessenger.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ChatFragment extends Fragment {

    private String mMessageId;
    private String mCorrespondent;
    private FirebaseRecyclerAdapter<PbMessage, RecyclerView.ViewHolder> mAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        mMessageId = getArguments().getString(MainActivity.MESSAGE_ID_KEY);
        mCorrespondent = getArguments().getString(MainActivity.MESSAGE_USER_KEY);
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        final FloatingActionButton mButtonSend = (FloatingActionButton) rootView.findViewById(R.id.button_send);
        mButtonSend.setBackgroundTintList(ColorStateList.valueOf(
                getResources().getColor(R.color.fabDisabled, null)
        ));
        mButtonSend.setEnabled(false);


        final EditText reply = (EditText) rootView.findViewById(R.id.et_reply);
        reply.setHint("Send reply to " + mCorrespondent);

        reply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (reply.getText().toString().length() > 0) {

                    mButtonSend.setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.colorAccent, null)
                    ));
                    mButtonSend.setEnabled(true);
                } else {
                    mButtonSend.setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.fabDisabled, null)
                    ));
                    mButtonSend.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("messages")
                .child(mMessageId);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference messagRef = reference.child("messages").child(mMessageId);
                Query lastIdQuery = messagRef.orderByKey().limitToLast(1);
                DatabaseReference conversationRef = reference.child("conversations").child(FirebaseAuth
                        .getInstance().getCurrentUser().getUid()).child(mMessageId);
                Map<String, Object> map = new HashMap<>();
                map.put("lastMessage", reply.getText().toString());
                map.put("lastMessageType", "sent");
                conversationRef.updateChildren(map);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PbMessage message = snapshot.getValue(PbMessage.class);
                            final String lastKey = message.getMessageId();
                            String nextKey = Integer.toString(Integer.parseInt(lastKey) + 1);
                            String body = reply.getText().toString();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String name = user.getEmail().split("@")[0];
                            Log.d("User name: ", name);
                            Date now = Calendar.getInstance().getTime();
                            Log.d("Now: ", now.toString());
                            PbMessage newMessage = new PbMessage(nextKey, body, now.toString(), name, "sent");
                            Map<String, Object> m = new HashMap<>();
                            m.put(nextKey, newMessage);
                            messagRef.updateChildren(m);
                            reply.getText().clear();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };

                lastIdQuery.addListenerForSingleValueEvent(valueEventListener);




            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_chat);
        LinearLayoutManager ll = new LinearLayoutManager(getActivity());
        ll.setStackFromEnd(true);

        recyclerView.setLayoutManager(ll);


        mAdapter = new FirebaseRecyclerAdapter<PbMessage, RecyclerView.ViewHolder>(
                PbMessage.class,
                R.layout.chat_incoming,
                RecyclerView.ViewHolder.class,
                reference
        ) {
            private static final int VIEWTYPE_INCOMING = 100;
            private static final int VIEWTYPE_OUTGOING = 200;


            @Override
            protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, PbMessage model,
                                              int position) {

                ((ChatViewHolder) viewHolder).setChatBody(model.getBody());

            }

            @Override
            public int getItemViewType(int position) {
                PbMessage message = getItem(position);
                if (message.getType().equals("sent")) {
                    return VIEWTYPE_OUTGOING;
                } else {
                    return VIEWTYPE_INCOMING;
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case VIEWTYPE_OUTGOING:
                        View outgoing = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.chat_outgoing, parent, false);
                        return new ChatViewHolder(outgoing);
                    case VIEWTYPE_INCOMING:
                        View incoming = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.chat_incoming, parent, false);
                        return new ChatViewHolder(incoming);
                    default:
                        return super.onCreateViewHolder(parent, viewType);
                }
            }
        };

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
