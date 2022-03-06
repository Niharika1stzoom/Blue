package com.niharika.android.reminder.provider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class reminderService extends Service {
    public reminderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}