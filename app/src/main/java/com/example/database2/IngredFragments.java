package com.example.database2;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class IngredFragments extends Fragment {

	private static final String DB_NAME = "mydatabase.db";
	private SQLiteDatabase db;
	
	
	private ArrayList<String> ind_name = new ArrayList<String>();
	private ArrayList<String> ind_count = new ArrayList<String>();
	private ArrayList<Integer> ind_id = new ArrayList<Integer>();
	private SharedPreferences mSettings;
	int id;

public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.ingred_fragment, container, false);
    
    ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(getActivity(), DB_NAME);
    db = dbOpenHelper.openDataBase();
    
    mSettings = getActivity().getSharedPreferences("ka", Context.MODE_PRIVATE);
    
        id = mSettings.getInt("kaka4", 0);      
    
    
	Cursor ingredientsCursor = db.rawQuery("SELECT id_ingred,quantity FROM ingredients WHERE id_dish = ?", new String[] {Integer.toString(id)});
	ingredientsCursor.moveToFirst();
	if(!ingredientsCursor.isAfterLast()) {
		do {				
			ind_id.add(Integer.parseInt(ingredientsCursor.getString(0)));
			ind_count.add(ingredientsCursor.getString(1));
		} while (ingredientsCursor.moveToNext());
	}
	ingredientsCursor.close();
	
	for(int i=0;i<ind_id.size();i++)
	{
	Cursor ingredientsCursor2 = db.rawQuery("SELECT name FROM ingred_name WHERE _id = ?", new String[] {Integer.toString(ind_id.get(i))});
	ingredientsCursor2.moveToFirst();
	if(!ingredientsCursor2.isAfterLast()) {
		do {				
			ind_name.add(ingredientsCursor2.getString(0));
		} while (ingredientsCursor2.moveToNext());
	}
	ingredientsCursor2.close();
	}
	ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	
	for(int i=0;i<ind_name.size();i++)
	{
		map = new HashMap<String, String>();
		map.put("Name", ind_name.get(i));
		map.put("Count", ind_count.get(i));
		myArrList.add(map);
	}

	SimpleAdapter adapter = new SimpleAdapter(getActivity(), myArrList,
            R.layout.row, new String[] { "Name", "Count" }, new int[] { R.id.NameIngrid, R.id.CountIngrid });
	ListView list = (ListView) view.findViewById(R.id.list);
	list.setAdapter(adapter);
    
    return view;

}



}