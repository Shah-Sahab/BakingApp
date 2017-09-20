package com.bakingapp.src.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Psych on 9/17/17.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }
}
