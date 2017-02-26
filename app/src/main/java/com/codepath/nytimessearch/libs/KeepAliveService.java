package com.codepath.nytimessearch.libs;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by keyulun on 2017/2/25.
 */

public class KeepAliveService extends Service {
   private static final Binder sBinder = new Binder();

   @Override
   public IBinder onBind(Intent intent) {
      return sBinder;
   }
}
