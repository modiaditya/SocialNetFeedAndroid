package com.amodi.socialnetnewsfeed.app;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import com.amodi.socialnetnewsfeed.volley.LruBitmapCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by amodi on 7/18/14.
 */
public class AppController extends Application {

  public static final String TAG = AppController.class.getSimpleName();

  private RequestQueue mRequestQueue;
  private ImageLoader mImageLoader;
  LruBitmapCache mLruBitmapCache;

  private static AppController mInstance;

  @Override
  public void onCreate() {
    Log.d(TAG, "inside create");
    super.onCreate();
    mInstance = this;
    Log.d(TAG, "inside create and value of mInstance " + mInstance);
  }

  public static synchronized AppController getInstance() {
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
    if(mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    return mRequestQueue;
  }

  public ImageLoader getImageLoader() {
    Log.d(TAG, "inside getImageLoader");
    getRequestQueue();
    if (this.mImageLoader == null) {
      getLruBitmapCache();
      this.mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
    }

    return this.mImageLoader;
  }

  public LruBitmapCache getLruBitmapCache() {
    if (mLruBitmapCache == null)
      mLruBitmapCache = new LruBitmapCache();
    return this.mLruBitmapCache;
  }

  public <T> void addToRequestQueue(Request<T> req, String tag) {
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(req);
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }

}
