package com.codepath.nytimessearch.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by keyulun on 2017/2/23.
 */

public class Headline implements Parcelable {
   private String main;
   private String print_headline;
   private Map<String, Object> additionalProperties = new HashMap<String, Object>();

   public void setPrint_headline(String print_headline) {
      this.print_headline = print_headline;
   }

   public String getPrint_headline() {
      return print_headline;
   }

   public String getMain() {
      return main;
   }

   public void setMain(String main) {
      this.main = main;
   }

   public Map<String, Object> getAdditionalProperties() {
      return this.additionalProperties;
   }

   public void setAdditionalProperty(String name, Object value) {
      this.additionalProperties.put(name, value);
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.main);
      dest.writeInt(this.additionalProperties.size());
      dest.writeMap(additionalProperties);
   }

   public Headline() {
   }

   protected Headline(Parcel in) {
      this.main = in.readString();
      int additionalPropertiesSize = in.readInt();
      this.additionalProperties = new HashMap<String, Object>(additionalPropertiesSize);
      for (int i = 0; i < additionalPropertiesSize; i++) {
         String key = in.readString();
         Object value = in.readParcelable(Object.class.getClassLoader());
         this.additionalProperties.put(key, value);
      }
   }

   public static final Parcelable.Creator<Headline> CREATOR = new Parcelable.Creator<Headline>() {
      @Override
      public Headline createFromParcel(Parcel source) {
         return new Headline(source);
      }

      @Override
      public Headline[] newArray(int size) {
         return new Headline[size];
      }
   };
}
