package com.dcalabrese22.dan.pbmessenger.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dcalabrese22.dan.pbmessenger.ConversationViewHolder;
import com.dcalabrese22.dan.pbmessenger.MultiSelectFirebaseRecyclerAdapter;
import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.dcalabrese22.dan.pbmessenger.R;
import com.dcalabrese22.dan.pbmessenger.helpers.RecyclerItemClickListener;
import com.dcalabrese22.dan.pbmessenger.interfaces.MessageExtrasListener;
import com.dcalabrese22.dan.pbmessenger.interfaces.OnRecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class MessagesListFragment extends Fragment {

    private String mUserId;
    private MultiSelectFirebaseRecyclerAdapter mAdapter;
    private MessageExtrasListener mListener;
    private boolean mIsMultiSelectMode = false;
    private RecyclerView mRecyclerView;
    private ActionMode mActionMode;
    private ArrayList<PbConversation> mSelectedConversations = new ArrayList<>();
    private HashMap<Integer, ConversationViewHolder> mViewHolders = new HashMap<>();

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

        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress_loading_messages);
        progressBar.setVisibility(View.VISIBLE);
        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.message_list_fab);
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

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_conversations);
        mAdapter = new MultiSelectFirebaseRecyclerAdapter(context, PbConversation.class,
                R.layout.conversation, ConversationViewHolder.class,
                reference, mListener, progressBar, mSelectedConversations);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView,
                new OnRecyclerItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (mIsMultiSelectMode) {
                            multiSelect(view, position);
                            Log.d("MessageListFrag onClick", String.valueOf(mIsMultiSelectMode));
                        } else {
                            PbConversation itemClicked = mAdapter.getItem(position);
                            String id = itemClicked.getId();
                            String user = itemClicked.getUser();
                            mListener.getMessageUser(user);
                            mListener.sendMessageId(id);
                        }
                    }

                    @Override
                    public void OnItemLongClick(View view, int position) {
                        Log.d("MessageListFrag onLong", String.valueOf(mIsMultiSelectMode));

                        if (!mIsMultiSelectMode) {
                            mIsMultiSelectMode = true;

                            if (mActionMode == null) {
                                mActionMode = getActivity().startActionMode(mActionModeCallBack);
                            }
                        }

                    }
                }));
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(ll);

        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    private Menu mContextMenu;
    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu, menu);
            mContextMenu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mIsMultiSelectMode = false;
            mSelectedConversations = new ArrayList<>();

        }
    };

    public void multiSelect(View view, int position) {
        ConversationViewHolder viewHolder = (ConversationViewHolder) mRecyclerView
                .getChildViewHolder(view);
        if (mActionMode != null) {
            PbConversation selected = mAdapter.getItem(position);
            if (mSelectedConversations.contains(selected)) {
                mSelectedConversations.remove(selected);
                mViewHolders.remove(position);
                viewHolder.flipAvatar(view);
                view.setActivated(false);
                Log.d("Removed", mSelectedConversations.toString());
            } else {
                mSelectedConversations.add(selected);
                mViewHolders.put(position, viewHolder);
                viewHolder.flipAvatar(view);
                view.setActivated(true);
                Log.d("Added", mSelectedConversations.toString());
            }
            mActionMode.setTitle("" + mSelectedConversations.size());
        }
    }

    public void unCheckedSelected() {
        for (ConversationViewHolder holder : mViewHolders.values()) {
            
        }
    }


}
