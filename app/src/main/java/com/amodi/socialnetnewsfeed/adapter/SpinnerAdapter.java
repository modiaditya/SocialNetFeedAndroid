package com.amodi.socialnetnewsfeed.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.amodi.socialnetnewsfeed.R;
import java.util.ArrayList;


/**
 * Created by amodi on 7/21/14.
 */
public class SpinnerAdapter extends BaseAdapter {

  private ArrayList<String> items;
  private Context context;

  public SpinnerAdapter(Context context, ArrayList<String> items) {
    this.items = items;
    this.context = context;
  }


  @Override
  public int getCount() {
    return items.size();
  }

  @Override
  public Object getItem(int i) {
    return items.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    if(view == null) {
      LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.spinner_list, null);
    }
    TextView name = (TextView) view.findViewById(R.id.name);
    name.setText(items.get(i));
    return view;
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getView(position, convertView, parent);
  }
}
