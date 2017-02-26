package com.codepath.nytimessearch.activities;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.libs.CustomTabActivityHelper;
import com.codepath.nytimessearch.models.Article;

public class ArticleActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_article);

      Article article = getIntent().getParcelableExtra("article");

      String url = article.getWebUrl();
      int requestCode = 100;
      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

      builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));


      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_TEXT, url);

      PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      Bitmap bitmap = getBitmap(getApplicationContext(), R.drawable.ic_share_white_24dp);

      builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
      CustomTabsIntent customTabsIntent = builder.build();

      CustomTabActivityHelper.openCustomTab(this, customTabsIntent, Uri.parse(url), (activity, uri) -> {
         Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
         activity.startActivity(intent1);
      });
   }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
   private static Bitmap getBitmap(Context context, int drawableId) {
      Drawable drawable = ContextCompat.getDrawable(context, drawableId);
      if (drawable instanceof VectorDrawable) {
         VectorDrawable vectorDrawable = (VectorDrawable) drawable;
         Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                 vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(bitmap);
         vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
         vectorDrawable.draw(canvas);
         return bitmap;
      }
      return null;
   }
}
