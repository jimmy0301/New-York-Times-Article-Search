package com.codepath.nytimessearch.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.codepath.nytimessearch.R;

import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends DialogFragment {
   // TODO: Rename parameter arguments, choose names that match
   // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

   private String arts = null;
   private String FS = null;
   private String SP = null;
   private String beginDate = null;
   private String sortMethod = null;

   public FilterFragment() {
      // Required empty public constructor
   }

   /**
    * Use this factory method to create a new instance of
    * this fragment using the provided parameters.
    *
    *
    * @return A new instance of fragment FilterFragment.
    */
   // TODO: Rename and change types and number of parameters
   public static FilterFragment newInstance(String beginDate, String sortMethod, String arts, String FS, String SP) {
      FilterFragment fragment = new FilterFragment();
      Bundle args = new Bundle();
      args.putString("date", beginDate);
      args.putString("sort", sortMethod);
      args.putString("arts", arts);
      args.putString("fs", FS);
      args.putString("sp", SP);
      fragment.setArguments(args);
      return fragment;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getArguments() != null) {
         arts = getArguments().getString("arts");
         FS = getArguments().getString("fs");
         sortMethod = getArguments().getString("sort");
         beginDate = getArguments().getString("date");
         SP = getArguments().getString("sp");
      }
   }

   @NonNull
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      LayoutInflater inflater = getActivity().getLayoutInflater();
      Dialog dialog = super.onCreateDialog(savedInstanceState);
      dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

      final View view = inflater.inflate(R.layout.dialog_filter, null);

      final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
      ArrayAdapter<CharSequence> sortList = ArrayAdapter.createFromResource(getActivity(), R.array.sort_method,
              android.R.layout.simple_spinner_dropdown_item);

      final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
      final CheckBox ckArts = (CheckBox) view.findViewById(R.id.ckArts);
      final CheckBox ckFS = (CheckBox) view.findViewById(R.id.ckFS);
      final CheckBox ckSP = (CheckBox) view.findViewById(R.id.ckSP);

      spinner.setAdapter(sortList);

      if (beginDate != null) {
         datePicker.updateDate(parseInt(beginDate.substring(0, 4)), parseInt(beginDate.substring(4, 6)) - 1, parseInt(beginDate.substring(6, 8)));
      }

      if (sortMethod != null) {
         Log.d("OnCreateDialog", sortMethod);
         for (int i = 0; i < sortList.getCount(); i++) {
            if (sortMethod.compareTo(sortList.getItem(i).toString()) == 0) {
               spinner.setSelection(i);
               break;
            }
         }
      }

      if (arts != null) {
         ckArts.setChecked(true);
      }

      if (FS != null) {
         ckFS.setChecked(true);
      }

      if (SP != null) {
         ckSP.setChecked(true);
      }

      builder.setView(view);

      builder.setPositiveButton(R.string.dialog_ok, (dialog12, which) -> {

         boolean isArtsChecked = ckArts.isChecked();
         boolean isFSChecked = ckFS.isChecked();
         boolean isSPChecked = ckSP.isChecked();

         if (isArtsChecked) {
            arts = "\"" + ckArts.getText().toString() + "\"";
         }
         else {
            arts = null;
         }

         if (isFSChecked) {
            FS = "\"" + ckFS.getText().toString() + "\"";
         }
         else {
            FS = null;
         }

         if (isSPChecked) {
            SP = "\"" + ckSP.getText().toString() + "\"";
         }
         else {
            SP = null;
         }

         beginDate = ""+ datePicker.getYear();

         if (datePicker.getMonth() + 1 < 10) {
            beginDate = beginDate + "0" + (datePicker.getMonth() + 1);
         }
         else {
            beginDate = beginDate + (datePicker.getMonth() + 1);
         }

         if (datePicker.getDayOfMonth() < 10) {
            beginDate = beginDate + "0" + datePicker.getDayOfMonth();
         }
         else {
            beginDate = beginDate + datePicker.getDayOfMonth();
         }

         sortMethod = spinner.getSelectedItem().toString();

         Log.d("FilterFragment", sortMethod);

         OnFragmentInteractionListener activity = (OnFragmentInteractionListener) getActivity();
         activity.onFragmentInteraction(beginDate, sortMethod, arts, FS, SP);
      }).setNegativeButton(R.string.dialog_cancel, (dialog1, id) -> FilterFragment.this.getDialog().cancel());

      return builder.create();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_filter, container, false);

      return view;
   }

   @Override
   public void onDetach() {
      super.onDetach();
   }

   /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
   public interface OnFragmentInteractionListener {
      // TODO: Update argument type and name
      void onFragmentInteraction(String beginDate, String sortMethod, String arts, String FS, String SP);
   }
}
