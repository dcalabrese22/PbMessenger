package com.dcalabrese22.dan.pbmessenger.fragments;

import android.content.Context;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dcalabrese22.dan.pbmessenger.ConversationViewHolder;
import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.dcalabrese22.dan.pbmessenger.R;
import com.dcalabrese22.dan.pbmessenger.interfaces.ConversationClickListener;
import com.dcalabrese22.dan.pbmessenger.interfaces.MessageExtrasListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MessagesListFragment extends Fragment {

    private String mUserId;
    private FirebaseRecyclerAdapter<PbConversation, ConversationViewHolder> mAdapter;
    MessageExtrasListener mListener;

    public MessagesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (MessageExtrasListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_messages_list, container, false);

        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress_loading_messages);
        progressBar.setVisibility(View.VISIBLE);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.message_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewMessageFragment fragment = new NewMessageFragment();
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        final Context context = getContext();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("conversations")
                .child(mUserId);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_conversations);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(ll);

        mAdapter = new FirebaseRecyclerAdapter<PbConversation, ConversationViewHolder>(
                PbConversation.class,
                R.layout.conversation,
                ConversationViewHolder.class,
                reference
        ) {
            @Override
            protected void populateViewHolder(ConversationViewHolder viewHolder, PbConversation model, int position) {
                viewHolder.setSubject(model.getTitle());
                viewHolder.setUser(model.getUser());
                viewHolder.setLastMessage(model.getLastMessage());
                viewHolder.setAvatar(model.getUserImage(), context);
            }

            @Override
            public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ConversationViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new ConversationClickListener() {
                    @Override
                    public void onConversationClick(View view, int position) {

                        PbConversation model = getItem(position);
                        String id = model.getId();
                        String user = model.getUser();
                        Log.d("MESSAGE ID: ", id);
                        mListener.getMessageUser(user);
                        mListener.sendMessageId(id);

                    }
                });

                return viewHolder;
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }
        };

        recyclerView.setAdapter(mAdapter);
        

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

}
