package com.amodi.socialnetnewsfeed;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.amodi.socialnetnewsfeed.adapter.FeedListAdapter;
import com.amodi.socialnetnewsfeed.adapter.SpinnerAdapter;
import com.amodi.socialnetnewsfeed.app.AppController;
import com.amodi.socialnetnewsfeed.data.FeedItem;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

  private static final String TAG = MainActivity.class.getSimpleName();
  private ListView _listView;
  private FeedListAdapter _feedListAdapter;
  private List<FeedItem> _feedItems;
  private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
  private ArrayList<String> _actionBarItems;


  @SuppressLint("NewApi")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    _listView = (ListView) findViewById(R.id.list);
    _feedItems = new ArrayList<FeedItem>();
    _feedListAdapter = new FeedListAdapter(this, _feedItems);
    _listView.setAdapter(_feedListAdapter);
    ActionBar actionBar = getActionBar();

    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CD1C27")));
    actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));


    Cache cache = AppController.getInstance().getRequestQueue().getCache();
    Cache.Entry entry = cache.get(URL_FEED);
    if(entry != null) {
      try {
        String data = new String(entry.data, "UTF-8");
        try{
          parseJsonFeed(new JSONObject(data));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    } else {
      JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_FEED, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
          VolleyLog.d(TAG, "Response" + jsonObject.toString());
          if(jsonObject != null) {
            parseJsonFeed(jsonObject);
          }
        }
      }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
          VolleyLog.d(TAG, "Error: "+ error);
        }
      });
      AppController.getInstance().addToRequestQueue(jsonReq);
    }

    // action bar stuff
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    _actionBarItems = new ArrayList<String>();
    _actionBarItems.add("Item1");
    _actionBarItems.add("Item2");
    _actionBarItems.add("Item3");


    actionBar.setListNavigationCallbacks(new SpinnerAdapter(this, _actionBarItems), this);



  }

  private void parseJsonFeed(JSONObject response) {
    try{
      JSONArray feedArray = response.getJSONArray("feed");
      for(int i=0; i<feedArray.length() ; i++) {
        JSONObject feedObj = (JSONObject) feedArray.get(i);
        FeedItem item = new FeedItem();
        item.setId(feedObj.getInt("id"));
        item.setName(feedObj.getString("name"));

        String image = feedObj.isNull("image") ? null : feedObj.getString("image");
        item.setImage(image);
        item.setStatus(feedObj.getString("status"));
        item.setProfilePic(feedObj.getString("profilePic"));
        item.setTimeStamp(feedObj.getString("timeStamp"));

        String feedUrl = feedObj.isNull("url") ? null : feedObj.getString("url");
        item.setUrl(feedUrl);
        _feedItems.add(item);
      }

      _feedListAdapter.notifyDataSetChanged();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      if (id == R.id.action_settings) {
          return true;
      }
      
      return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(int i, long l) {
    Toast.makeText(getBaseContext(), "You have selected " + i, Toast.LENGTH_SHORT).show();
    return false;
  }
}
