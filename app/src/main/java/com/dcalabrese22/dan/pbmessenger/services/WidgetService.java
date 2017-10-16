package com.dcalabrese22.dan.pbmessenger.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.dcalabrese22.dan.pbmessenger.WidgetDataProvider;

/**
 * Created by dan on 10/9/17.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
