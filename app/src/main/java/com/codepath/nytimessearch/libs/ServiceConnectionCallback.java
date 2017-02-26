package com.codepath.nytimessearch.libs;

import android.support.customtabs.CustomTabsClient;

/**
 * Created by keyulun on 2017/2/25.
 */

public interface ServiceConnectionCallback {
   /**
    * Called when the service is connected.
    * @param client a CustomTabsClient
    */
   void onServiceConnected(CustomTabsClient client);

   /**
    * Called when the service is disconnected.
    */
   void onServiceDisconnected();
}
