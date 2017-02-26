package com.codepath.nytimessearch.models;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;

import com.bumptech.glide.Glide;
import com.codepath.nytimessearch.View.DynamicHeightImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keyulun on 2017/2/19.
 */

public class Article implements Parcelable {
   String web_url;
   Headline headline;
   private List<Multimedia> multimedia = null;

   public Article() {
   }

   public String getWebUrl() {
      return web_url;
   }

   public List<Multimedia> getMultimedia() {
      return multimedia;
   }

   public String getImageUrl(){
      String image_url = "http://www.nytimes.com/" + multimedia.get(0).getUrl();
      return image_url;
   }

   public Headline getHeadline() {
      return headline;
   }

   public void setWeb_url(String web_url) {
      this.web_url = web_url;
   }

   public void setHeadline(Headline headline) {
      this.headline = headline;
   }

   public void setMultimedia(List<Multimedia> multimedia) {
      this.multimedia = multimedia;
   }

   @BindingAdapter({"bind:imageUrl"})
   public static void loadImage(DynamicHeightImageView view, String imageUrl) {
      view.setImageResource(0);
      Glide.with(view.getContext()).load(imageUrl).into(view);
   }



   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.web_url);
      dest.writeParcelable(this.headline, flags);
      dest.writeList(this.multimedia);
   }

   protected Article(Parcel in) {
      this.web_url = in.readString();
      this.headline = in.readParcelable(Headline.class.getClassLoader());
      this.multimedia = new ArrayList<Multimedia>();
      in.readList(this.multimedia, Object.class.getClassLoader());
   }

   public static final Creator<Article> CREATOR = new Creator<Article>() {
      @Override
      public Article createFromParcel(Parcel source) {
         return new Article(source);
      }

      @Override
      public Article[] newArray(int size) {
         return new Article[size];
      }
   };
}
