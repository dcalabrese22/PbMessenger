package com.dcalabrese22.dan.pbmessenger;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.dcalabrese22.dan.pbmessenger.fragments.MessagesListFragment;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dan on 10/9/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext = null;
    private ArrayList<PbConversation> mConversations = new ArrayList<>();
    private List<String> fakeData = new ArrayList<>();
    private AppWidgetManager mManager;
    private int[] mWidgetIds;
    private String mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private  DatabaseReference mReference = FirebaseDatabase.getInstance().getReference()
            .child("conversations")
            .child(mUserId);




    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mManager = AppWidgetManager.getInstance(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetIds = mManager.getAppWidgetIds(new ComponentName(mContext.getPackageName(), PbAppWidget.class.getName()));
        mManager.notifyAppWidgetViewDataChanged(mWidgetIds, R.id.lv_widget_conversations);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Query query = mReference;
        final ArrayList<Long> l = new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                l.add(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return l.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_conversation);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PbConversation c = snapshot.getValue(PbConversation.class);
                    Log.d("PbConvo", c.toString());
                    view.setTextViewText(R.id.widget_conversation_user, c.getUser());
                    view.setTextViewText(R.id.widget_conversation_subject, c.getTitle());
                    view.setTextViewText(R.id.widget_conversation_last_message, c.getLastMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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


    public void makeFakeData() {
        fakeData.clear();
        for (int i = 0; i < 10; i++) {
            fakeData.add("ListView item " + i);
        }

    }
}
