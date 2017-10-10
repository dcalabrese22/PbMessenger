package com.dcalabrese22.dan.pbmessenger;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dan on 10/9/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext = null;
    private ArrayList<PbConversation> mConversations;
    private List<String> fakeData = new ArrayList<>();
    private FirebaseListAdapter<PbConversation> mAdapter;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mConversations = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        getDataFromFirebase();
        Log.d("mConversations", mConversations.toString());

    }

    @Override
    public void onDataSetChanged() {
        getDataFromFirebase();
        Log.d("mConversations", mConversations.toString());

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mConversations.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_conversation);
        PbConversation conversation = mConversations.get(position);
        String title = conversation.getTitle();
        String user = conversation.getUser();
        String lastMessage = conversation.getLastMessage();
        String url = conversation.getUserImage();
        view.setTextViewText(R.id.widget_conversation_user, user);
        view.setTextViewText(R.id.widget_conversation_subject, title);
        view.setTextViewText(R.id.widget_conversation_last_message, lastMessage);

        return view;
    }



    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void getDataFromFirebase() {
        mConversations.clear();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference conversationReference = FirebaseDatabase.getInstance().getReference()
                .child("conversations")
                .child(uid);

        conversationReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while (dataSnapshotIterator.hasNext()) {
                    DataSnapshot child = dataSnapshotIterator.next();
                    PbConversation toAdd = child.getValue(PbConversation.class);
                    if (!mConversations.contains(toAdd)) {
                        mConversations.add(toAdd);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void makeFakeData() {
        fakeData.clear();
        for (int i = 0; i < 10; i++) {
            fakeData.add("ListView item " + i);
        }

    }
}
