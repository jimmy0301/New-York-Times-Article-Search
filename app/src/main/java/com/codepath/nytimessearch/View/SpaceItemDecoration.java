package com.codepath.nytimessearch.View;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by keyulun on 2017/2/25.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
   private final int mSpace;
   public SpaceItemDecoration(int space) {
      this.mSpace = space;
   }
   @Override
   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
      outRect.left = mSpace;
      outRect.right = mSpace;
      outRect.bottom = mSpace;
      // Add top margin only for the first item to avoid double space between items
      if (parent.getChildAdapterPosition(view) == 0)
         outRect.top = mSpace;
   }
}
