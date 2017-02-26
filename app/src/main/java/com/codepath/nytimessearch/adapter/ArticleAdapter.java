package com.codepath.nytimessearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.nytimessearch.BR;
import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.View.ViewHolderForImage;
import com.codepath.nytimessearch.View.ViewHolderForText;
import com.codepath.nytimessearch.models.Article;

import java.util.List;

/**
 * Created by keyulun on 2017/2/25.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

   private List<Article> mArticles;

   private Context mContext;

   private OnRecyclerViewItemClickListener mOnItemClickListener = null;

   private static final int HAS_IMAGE = 1;

   private static final int NO_IMAGE = 0;

   public ArticleAdapter(Context context, List<Article> articles) {
      mArticles = articles;
      mContext = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      RecyclerView.ViewHolder viewHolder;

      Context context = parent.getContext();
      LayoutInflater inflater = LayoutInflater.from(context);

      switch (viewType) {
         case HAS_IMAGE:
            View v1 = inflater.inflate(R.layout.item_article_result, parent, false);
            v1.setOnClickListener(this);
            viewHolder = new ViewHolderForImage(v1);
            break;
         case NO_IMAGE:
            View v2 = inflater.inflate(R.layout.viewholder_for_text, parent, false);
            viewHolder = new ViewHolderForText(v2);
            v2.setOnClickListener(this);
            break;
         default:
            View v3 = inflater.inflate(R.layout.viewholder_for_text, parent, false);
            v3.setOnClickListener(this);
            viewHolder = new ViewHolderForText(v3);
            break;
      }

      return viewHolder;
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      switch (holder.getItemViewType()) {
         case HAS_IMAGE:
            ViewHolderForImage vh1 = (ViewHolderForImage) holder;
            configureViewHolderForImage(vh1, position);
            break;
         case NO_IMAGE:
            ViewHolderForText vh2 = (ViewHolderForText) holder;
            configureViewHolderForText(vh2, position);
            break;
         default:
            ViewHolderForText vhDefault = (ViewHolderForText) holder;
            configureViewHolderForText(vhDefault, position);
            break;
      }

      Article article = mArticles.get(position);

      holder.itemView.setTag(article);
   }

   private void configureViewHolderForText(ViewHolderForText vh2, int position) {
      Article article = mArticles.get(position);

      vh2.getBinding().setVariable(BR.article, article);
      vh2.getBinding().executePendingBindings();
   }

   private void configureViewHolderForImage(ViewHolderForImage vh1, int position) {
      Article article = mArticles.get(position);
      /*if (article != null) {
         Multimedia multimedia = (article.getMultimedia().get(0));
         String thumbnail = null;
         int height = multimedia.getHeight();
         int width = multimedia.getWidth();
         vh1.getImageView().setImageResource(0);
         vh1.getImageView().setHeightRatio(((double) height)/width);
         Log.d("image", multimedia.getUrl());
         thumbnail = article.getThumbNail(multimedia.getUrl());

         if (!TextUtils.isEmpty(thumbnail)) {
            Log.d("set Image", "set the image");
            Glide.with(getContext()).load(thumbnail).into(vh1.getImageView());
         }*/

         vh1.getBinding().setVariable(BR.article, article);
         vh1.getBinding().executePendingBindings();
     // }
   }

   @Override
   public int getItemCount() {
      return mArticles.size();
   }

   @Override
   public int getItemViewType(int position) {
      Article article = mArticles.get(position);

      if (article.getMultimedia().size() > 0) {
         return HAS_IMAGE;
      }
      else {
         return NO_IMAGE;
      }
   }

   @Override
   public void onClick(View v) {
      if (mOnItemClickListener != null) {
         mOnItemClickListener.onItemClick(v, (Article) v.getTag());
      }
   }

   public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
      this.mOnItemClickListener = listener;
   }

   public Context getContext() {
      return mContext;
   }

   public static interface OnRecyclerViewItemClickListener {
      void onItemClick(View view, Article article);
   }
}
