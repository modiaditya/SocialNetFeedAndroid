package com.amodi.socialnetnewsfeed.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.amodi.socialnetnewsfeed.R;
import com.amodi.socialnetnewsfeed.app.AppController;
import com.amodi.socialnetnewsfeed.data.FeedItem;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;


/**
 * Created by amodi on 7/18/14.
 */
public class FeedListAdapter extends BaseAdapter {

  private Activity activity;
  private LayoutInflater inflater;
  private List<FeedItem> feedItems;
  ImageLoader imageLoader = AppController.getInstance().getImageLoader();

  public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
    this.activity = activity;
    this.feedItems = feedItems;
  }

  @Override
  public int getCount() {
    return feedItems.size();
  }

  @Override
  public Object getItem(int i) {
    return feedItems.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    if(inflater == null){
      inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    if(view == null) {
      view = inflater.inflate(R.layout.feed_item, null);
    }

    if(imageLoader == null) {
      imageLoader = AppController.getInstance().getImageLoader();
    }

    TextView name = (TextView) view.findViewById(R.id.name);
    TextView timeStamp = (TextView)view.findViewById(R.id.timestamp);
    TextView statusMsg = (TextView) view.findViewById(R.id.txtStatusMsg);
    TextView url = (TextView)view.findViewById(R.id.txtUrl);

    NetworkImageView profilePic = (NetworkImageView) view.findViewById(R.id.profilePic);
    ImageView imageView = (ImageView)view.findViewById(R.id.feedImage1);

    FeedItem item = feedItems.get(i);

    name.setText(item.getName());

    CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getTimeStamp()),
        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    timeStamp.setText(timeAgo);

    if(!TextUtils.isEmpty(item.getStatus())) {
      statusMsg.setText(item.getStatus());
      statusMsg.setVisibility(View.VISIBLE);
    } else {
      statusMsg.setVisibility(View.GONE);
    }

    if(item.getUrl() != null) {
      url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"+item.getUrl() + "</a>"));
      url.setMovementMethod(LinkMovementMethod.getInstance());
      url.setVisibility(View.VISIBLE);
    } else {
      url.setVisibility(View.GONE);
    }

    profilePic.setImageUrl(item.getProfilePic(), imageLoader);

    if(item.getImage() != null){
      //imageView.setImageURI(item.getUrl(), imageLoader);
    }

    return view;
  }
}
