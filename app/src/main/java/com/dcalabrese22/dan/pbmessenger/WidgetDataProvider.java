package com.dcalabrese22.dan.pbmessenger;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.dcalabrese22.dan.pbmessenger.helpers.PbAvatarGetter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by dan on 10/9/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<PbConversation> mConversations;
    private List<String> fakeData = new ArrayList<>();

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mConversations = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        makeFakeData();
    }

    @Override
    public void onDataSetChanged() {
        makeFakeData();
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
//        PbConversation conversation = mConversations.get(position);
//        String title = conversation.getTitle();
//        String user = conversation.getUser();
//        String lastMessage = conversation.getLastMessage();
//        String url = conversation.getUserImage();
//        view.setTextViewText(R.id.tv_conversation_user, user);
//        view.setTextViewText(R.id.tv_conversation_subject, title);
//        view.setTextViewText(R.id.tv_conversation_last_message, lastMessage);
        view.setTextViewText(R.id.tv_conversation_last_message, fakeData.get(position));
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
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference conversationReference = FirebaseDatabase.getInstance().getReference()
                .child("conversations")
                .child(uid);

        conversationReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PbConversation conversation = dataSnapshot.getValue(PbConversation.class);
                mConversations.add(conversation);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                PbConversation conversation = dataSnapshot.getValue(PbConversation.class);
                String idToFind = conversation.getId();
                for (ListIterator<PbConversation> listIterator = mConversations.listIterator();
                        listIterator.hasNext();) {
                    String conversationId = listIterator.next().getId();
                    if (conversationId.equals(idToFind)) {
                        listIterator.remove();
                    }
                    listIterator.add(conversation);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                PbConversation conversation = dataSnapshot.getValue(PbConversation.class);
                String idToFind = conversation.getId();
                for (ListIterator<PbConversation> listIterator = mConversations.listIterator();
                     listIterator.hasNext();) {
                    String conversationId = listIterator.next().getId();
                    if (conversationId.equals(idToFind)) {
                        listIterator.remove();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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
