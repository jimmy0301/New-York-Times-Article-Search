package com.codepath.nytimessearch.View;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by keyulun on 2017/2/25.
 */

public class ViewHolderForImage extends RecyclerView.ViewHolder {
   private ViewDataBinding binding;

   public ViewHolderForImage(View itemView) {
      super(itemView);

      binding = DataBindingUtil.bind(itemView);
   }

   public ViewDataBinding getBinding() {
      return binding;
   }
}
