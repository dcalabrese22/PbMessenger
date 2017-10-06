package com.dcalabrese22.dan.pbmessenger.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.dcalabrese22.dan.pbmessenger.interfaces.OnRecyclerItemClickListener;

/**
 * Created by dcalabrese on 10/6/2017.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnRecyclerItemClickListener mClickListener;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerItemClickListener clickListener) {
        mClickListener = clickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childview = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childview != null && mClickListener != null) {
                    mClickListener.OnItemLongClick(childview, recyclerView.getChildAdapterPosition(childview));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childview = rv.findChildViewUnder(e.getX(), e.getY());
        if (childview != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mClickListener.onItemClick(childview, rv.getChildAdapterPosition(childview));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
