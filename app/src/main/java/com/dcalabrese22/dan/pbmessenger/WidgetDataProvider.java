package com.dcalabrese22.dan.pbmessenger;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dan on 10/9/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext = null;
    private ArrayList<PbConversation> mConversations = new ArrayList<>();;
    private List<String> fakeData = new ArrayList<>();
    private AppWidgetManager mManager;
    private CountDownLatch mCountDownLatch;



    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mManager = AppWidgetManager.getInstance(mContext);

    }

    @Override
    public void onCreate() {
        getDataFromFirebase();
        Log.d("mConversations", mConversations.toString());
    }

    @Override
    public void onDataSetChanged() {
        mCountDownLatch = new CountDownLatch(1);
        getDataFromFirebase();
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference conversationReference = FirebaseDatabase.getInstance().getReference()
                .child("conversations")
                .child(uid);
        Log.d("reference", conversationReference.toString());

        conversationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PbConversation c = dataSnapshot.getValue(PbConversation.class);
                String user = c.getUser();
                String lastMessage = c.getLastMessage();
                String title = c.getTitle();
                String url = c.getUserImage();
                String id = c.getId();
                Log.d("Conversation", user + " " + lastMessage + " " + title);
                PbConversation toAdd = new PbConversation(id, title, user, lastMessage, url);
                mConversations.add(toAdd);
                int[] ids = mManager.getAppWidgetIds(new ComponentName(mContext, WidgetDataProvider.class));
                mManager.notifyAppWidgetViewDataChanged(ids, R.id.lv_widget_conversations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        conversationReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                PbConversation c = dataSnapshot.getValue(PbConversation.class);
//                String user = c.getUser();
//                String lastMessage = c.getLastMessage();
//                String title = c.getTitle();
//                String url = c.getUserImage();
//                String id = c.getId();
//                Log.d("Conversation", user + " " + lastMessage + " " + title);
//                PbConversation toAdd = new PbConversation(id, title, user, lastMessage, url);
//                mConversations.add(toAdd);
//                int[] ids = mManager.getAppWidgetIds(new ComponentName(mContext, WidgetDataProvider.class));
//                mManager.notifyAppWidgetViewDataChanged(ids, R.id.lv_widget_conversations);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void makeFakeData() {
        fakeData.clear();
        for (int i = 0; i < 10; i++) {
            fakeData.add("ListView item " + i);
        }

    }
}
