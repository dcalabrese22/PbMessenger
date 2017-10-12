package com.dcalabrese22.dan.pbmessenger;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
        mWidgetIds = mManager.getAppWidgetIds(new ComponentName(mContext.getPackageName(), PbAppWidget.class.getName()));

    }

    @Override
    public void onCreate() {

        mConversations.clear();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PbConversation c = child.getValue(PbConversation.class);
                    mConversations.add(c);
                    mManager.notifyAppWidgetViewDataChanged(mWidgetIds, R.id.lv_widget_conversations);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDataSetChanged() {
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

        view.setTextViewText(R.id.widget_conversation_user, mConversations.get(position).getUser());
        view.setTextViewText(R.id.widget_conversation_subject,
                mConversations.get(position).getTitle());
        view.setTextViewText(R.id.widget_conversation_last_message,
                mConversations.get(position).getLastMessage());

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
