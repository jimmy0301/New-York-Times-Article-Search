package com.codepath.nytimessearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by keyulun on 2017/2/23.
 */

public class Legacy implements Parcelable {
   String wide;
   String wideheight;
   String widewidth;

   public String getWide() {
      return wide;
   }

   public String getWideheight() {
      return wideheight;
   }

   public String getWidewidth() {
      return widewidth;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.wide);
      dest.writeString(this.wideheight);
      dest.writeString(this.widewidth);
   }

   public Legacy() {
   }

   protected Legacy(Parcel in) {
      this.wide = in.readString();
      this.wideheight = in.readString();
      this.widewidth = in.readString();
   }

   public static final Parcelable.Creator<Legacy> CREATOR = new Parcelable.Creator<Legacy>() {
      @Override
      public Legacy createFromParcel(Parcel source) {
         return new Legacy(source);
      }

      @Override
      public Legacy[] newArray(int size) {
         return new Legacy[size];
      }
   };
}
