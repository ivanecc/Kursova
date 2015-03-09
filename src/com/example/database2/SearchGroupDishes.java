package com.example.database2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchGroupDishes extends ListActivity {
	
	private static final String DB_NAME = "mydatabase.db";
	
    
	private SQLiteDatabase db;
	private ListView listView;
	private ArrayList<String> dishes;
	String type = new String();
	//////////////////////////////////////////

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_group_dishes);
        
        type=getIntent().getExtras().getString("type");
        
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        db = dbOpenHelper.openDataBase();    
        
        fillDish(); setUpList();        
    }	
	
		private void fillDish() 
		{
			
			dishes = new ArrayList<String>();
							
			Cursor friendCursor = db.rawQuery("SELECT dish_name FROM dish WHERE type ='"+type+"'", null);
			friendCursor.moveToFirst();
			if(!friendCursor.isAfterLast()) {
				do {
					String name = friendCursor.getString(0);
					dishes.add(name);
				} while (friendCursor.moveToNext());
			}
			friendCursor.close();
		}
		
	private void setUpList() {		
		setListAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, dishes));
		listView = getListView();
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
				Intent intent = new Intent(SearchGroupDishes.this, Rezept_Dish.class);
			   	 intent.putExtra("dish", ((TextView) view).getText());
			   	intent.putExtra("color", "orange");
			       startActivity(intent);
							
			}
		});
	}	
	public void Back(View v)
	{
		Intent intent = new Intent(SearchGroupDishes.this, SearchGroup.class);
   	  dishes.clear();
       startActivity(intent);       
       overridePendingTransition(R.animator.animation_form2_to_form1_in, R.animator.animation_form2_to_form1_out);
       this.finish();
	}
}
