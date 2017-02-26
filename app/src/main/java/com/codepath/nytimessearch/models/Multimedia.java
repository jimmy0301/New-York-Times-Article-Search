package com.codepath.nytimessearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by keyulun on 2017/2/23.
 */

public class Multimedia implements Parcelable {
   int width;
   String url;
   int height;
   String subtype;
   Legacy legacy;
   String type;

   public int getHeight() {
      return height;
   }

   public int getWidth() {
      return width;
   }

   public String getUrl() {
      return url;
   }

   public String getSubtype() {
      return subtype;
   }

   public Legacy getLegacy() {
      return legacy;
   }

   public String getType() {
      return type;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.width);
      dest.writeString(this.url);
      dest.writeInt(this.height);
      dest.writeString(this.subtype);
      dest.writeParcelable(this.legacy, flags);
      dest.writeString(this.type);
   }

   public Multimedia() {
   }

   protected Multimedia(Parcel in) {
      this.width = in.readInt();
      this.url = in.readString();
      this.height = in.readInt();
      this.subtype = in.readString();
      this.legacy = in.readParcelable(Legacy.class.getClassLoader());
      this.type = in.readString();
   }

   public static final Parcelable.Creator<Multimedia> CREATOR = new Parcelable.Creator<Multimedia>() {
      @Override
      public Multimedia createFromParcel(Parcel source) {
         return new Multimedia(source);
      }

      @Override
      public Multimedia[] newArray(int size) {
         return new Multimedia[size];
      }
   };
}
