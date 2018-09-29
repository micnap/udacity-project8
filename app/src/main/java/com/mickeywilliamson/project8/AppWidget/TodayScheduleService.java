package com.mickeywilliamson.project8.AppWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TodayScheduleService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new TodayScheduleRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}
