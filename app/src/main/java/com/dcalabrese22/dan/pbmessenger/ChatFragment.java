package com.dcalabrese22.dan.pbmessenger;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
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
import android.widget.ImageButton;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.dcalabrese22.dan.pbmessenger.Objects.PbMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatFragment extends Fragment {

    private String mMessageId;
    private String mCorrespondent;
    private FirebaseRecyclerAdapter<PbMessage, ChatViewHolder> mAdapter;
    private ImageButton mButtonSend;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        mMessageId = getArguments().getString(MainActivity.MESSAGE_ID_KEY);
        mCorrespondent = getArguments().getString(MainActivity.MESSAGE_USER_KEY);
        Log.d("MESSAGE ID: ", mMessageId);
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        mButtonSend = (ImageButton) rootView.findViewById(R.id.button_send);
        final EditText reply = (EditText) rootView.findViewById(R.id.et_reply);
        reply.setHint("Send reply to " + mCorrespondent);

        reply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mButtonSend.setColorFilter(getResources().getColor(R.color.colorPrimaryDark, null));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("conversations")
                .child("messages")
                .child(mMessageId);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_chat);
        LinearLayoutManager ll = new LinearLayoutManager(getActivity());


        recyclerView.setLayoutManager(ll);

        mAdapter = new FirebaseRecyclerAdapter<PbMessage, ChatViewHolder>(
                PbMessage.class,
                R.layout.chat,
                ChatViewHolder.class,
                reference
        ) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, PbMessage model, int position) {
                viewHolder.setChatBody(model.getBody());
                viewHolder.setChatSender(model.getSender());
                viewHolder.setTimeSent(model.getDate());
            }
        };

        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

}
