package com.timetocook.ListImage;

import java.util.ArrayList;

import com.example.database2.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DishAdapter extends BaseAdapter {
 private static ArrayList<Dish> itemDetailsrrayList;
 
 private Integer[] imgid = {

   };
 
 private LayoutInflater l_Inflater;

 public DishAdapter(Context context, ArrayList<Dish> results) {
  itemDetailsrrayList = results;
  l_Inflater = LayoutInflater.from(context);
 }

 public int getCount() {
  return itemDetailsrrayList.size();
 }

 public Object getItem(int position) {
  return itemDetailsrrayList.get(position);
 }

 public long getItemId(int position) {
  return position;
 }

 public View getView(int position, View convertView, ViewGroup parent) {
  ViewHolder holder;
  if (convertView == null) {
   convertView = l_Inflater.inflate(R.layout.list_image, null);
   holder = new ViewHolder();
   holder.txt_itemName = (TextView) convertView.findViewById(R.id.txtTitle);
   holder.itemImage = (ImageView) convertView.findViewById(R.id.imgIcon);

   convertView.setTag(holder);
  } else {
   holder = (ViewHolder) convertView.getTag();
  }
  
  holder.txt_itemName.setText(itemDetailsrrayList.get(position).getName());
  holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber() - 1]);

  return convertView;
 }

 static class ViewHolder {
  TextView txt_itemName;
  TextView txt_itemDescription;
  TextView txt_itemPrice;
  ImageView itemImage;
 }
}