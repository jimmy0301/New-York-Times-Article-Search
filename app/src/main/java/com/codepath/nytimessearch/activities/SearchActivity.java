package com.codepath.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.View.SpaceItemDecoration;
import com.codepath.nytimessearch.adapter.ArticleAdapter;
import com.codepath.nytimessearch.databinding.ActivitySearchBinding;
import com.codepath.nytimessearch.fragments.FilterFragment;
import com.codepath.nytimessearch.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.nytimessearch.models.Article;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements FilterFragment.OnFragmentInteractionListener {

   RecyclerView rvResults;
   SearchView searchView;
   FilterFragment filterFragment;
   StaggeredGridLayoutManager staggeredGridLayoutMgr;
   String act_query = null;
   String act_date = null;
   String act_arts = null;
   String act_FS = null;
   String act_SP = null;
   String act_sort = null;
   String act_newDesk = null;

   List<Article> list;
   ArticleAdapter rvAdapter;
   private EndlessRecyclerViewScrollListener scrollListener;
   private ActivitySearchBinding binding;
   final String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
      Toolbar toolbar = binding.toolbar;
      setSupportActionBar(toolbar);

      setupViews();
   }

   private void setupViews() {
      rvResults = binding.rvResults;
      list = new ArrayList<Article>();

      staggeredGridLayoutMgr = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
      rvResults.setLayoutManager(staggeredGridLayoutMgr);
      rvAdapter = new ArticleAdapter(this, list);
      rvResults.setAdapter(rvAdapter);
      SpaceItemDecoration decoration = new SpaceItemDecoration(16);
      rvResults.addItemDecoration(decoration);

      setupRvListener();
   }

   private void setupRvListener() {
      rvAdapter.setOnItemClickListener(new ArticleAdapter.OnRecyclerViewItemClickListener() {
         @Override
         public void onItemClick(View view, Article article) {
            Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
            i.putExtra("article", article);

            startActivity(i);
         }
      });

      scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutMgr) {
         @Override
         public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            loadNextDataFromApi(page);
         }
      };

      rvResults.addOnScrollListener(scrollListener);
   }

   private void loadNextDataFromApi(int page) {

      AsyncHttpClient client = new AsyncHttpClient();

      RequestParams params = new RequestParams();
      params.put("api-key", "e3a2b41e25e04fdfb2e4a051f4064956");
      params.put("page", page);
      if (act_date != null) {
         Log.d("loadNextData", act_date);
         params.put("begin_date", act_date);
      }

      act_query = searchView.getQuery().toString();

      if (act_query != null && !TextUtils.isEmpty(act_query)) {
         params.put("q", act_query);
      }

      client.get(url, params, new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            try {
               Gson gson = new GsonBuilder().create();
               Article [] articles = gson.fromJson(response.getJSONObject("response").getJSONArray("docs").toString(), Article[].class);
               for (Article a: articles) {
                  list.add(a);
               }

               int curSize = rvAdapter.getItemCount();
               rvAdapter.notifyItemRangeChanged(curSize, articles.length);
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }
      });
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu_search, menu);
      MenuItem searchItem = menu.findItem(R.id.action_search);
      searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
      searchItem.expandActionView();

      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {

            list.clear();
            if (!isNetworkAvailable()) {
               Toast.makeText(getApplicationContext(), "There is no network connection", Toast.LENGTH_LONG).show();
               return false;
            }
            AsyncAPI(query, act_date, act_sort, act_newDesk);
            searchView.clearFocus();
            return true;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
            return false;
         }
      });

      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      if (id == R.id.action_filter) {
         filterFragment = FilterFragment.newInstance(act_date, act_sort, act_arts, act_FS, act_SP);
         FragmentManager fm = getSupportFragmentManager();

         act_date = null;
         act_sort = null;
         act_arts = null;
         act_SP = null;
         act_FS = null;
         act_newDesk = null;

         filterFragment.show(fm, "Filter Select");

         return true;
      }

      return super.onOptionsItemSelected(item);
   }

   public void onFragmentInteraction(String beginDate, String sortMethod, String arts, String FS, String SP) {
      Log.d("onFragmentInteraction", beginDate);
      Log.d("onFragmentInteraction", "Arts: " + arts);
      Log.d("onFragmentInteraction", "FS: " + FS);
      Log.d("onFragmentInteraction", "SP: " + SP);

      if (arts != null || FS != null || SP != null)
         act_newDesk = "news_desk:(";

      if (arts != null && FS != null && SP != null) {
         act_newDesk = act_newDesk + arts + " " + FS + " " + SP + ")";
         act_arts = arts;
         act_FS = FS;
         act_SP = SP;
      }
      else if (arts != null && FS != null && SP == null) {
         act_newDesk = act_newDesk + arts + " " + FS + ")";
         act_arts = arts;
         act_FS = FS;
      }
      else if (arts != null && SP != null && FS == null) {
         act_newDesk = act_newDesk + arts + " " + SP + ")";
         act_arts = arts;
         act_SP = SP;
      }
      else if (arts != null && FS == null && SP == null) {
         act_newDesk = act_newDesk + arts + ")";
         act_arts = arts;
      }
      else if (FS != null && arts == null && SP == null) {
         act_newDesk = act_newDesk + FS + ")";
         act_FS = FS;
      }
      else if (FS != null && SP != null && arts == null) {
         act_newDesk = act_newDesk + FS + " " + SP + ")";
         act_SP = SP;
         act_FS = FS;
      }

      if (act_newDesk != null)
         Log.d("onFragmentInteraction", act_newDesk);

      if (!isNetworkAvailable()) {
         Toast.makeText(getApplicationContext(), "There is no network connection", Toast.LENGTH_LONG).show();
         return;
      }

      AsyncAPI(searchView.getQuery().toString(), beginDate, sortMethod, act_newDesk);

   }

   private void AsyncAPI(String queryTerm, String beginDate, String sortMethod, String fq) {

      AsyncHttpClient client = new AsyncHttpClient();

      RequestParams params = new RequestParams();
      params.put("api-key", "e3a2b41e25e04fdfb2e4a051f4064956");
      params.put("page", 0);

      if (beginDate != null) {
         act_date = beginDate;
         params.put("begin_date", beginDate);
      }
      if (fq != null) {
         Log.d("newDesk", fq);
         act_newDesk = fq;
         params.put("fq", act_newDesk);
      }

      if (sortMethod != null) {
         act_sort = sortMethod;
         params.put("sort", sortMethod);
      }

      if (queryTerm != null && !TextUtils.isEmpty(queryTerm)) {
         params.put("q", queryTerm);
      }

      Log.d("parameter", params.toString());

      client.get(url, params, new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            Gson gson = new GsonBuilder().create();
            Article [] articles = new Article[0];
            try {
               articles = gson.fromJson(response.getJSONObject("response").getJSONArray("docs").toString(), Article[].class);
            } catch (JSONException e) {
               e.printStackTrace();
            }
            list.clear();
            for (Article a: articles) {
               list.add(a);
            };
            rvAdapter.notifyDataSetChanged();
            scrollListener.resetState();
         }

         @Override
         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d("onFailure", "status code:" + statusCode);
            super.onFailure(statusCode, headers, throwable, errorResponse);
         }
      });
   }

   private Boolean isNetworkAvailable() {
      ConnectivityManager connectivityManager
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
   }
}
