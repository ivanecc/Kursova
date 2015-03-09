package com.example.database2;

import java.util.ArrayList;


import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchName extends ActionBarActivity implements OnQueryTextListener {
	
	private static final String DB_NAME = "mydatabase.db";
	
    
	private SQLiteDatabase db;
	private ListView listView;
	private ArrayList<String> dishes;
	private ArrayList<String> falsh = new ArrayList<String>();
	//////////////////////////////////////////
	int count=0;
	ActionBarDrawerToggle mDrawerToggle;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_name);
        
        initDrawer();
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        db = dbOpenHelper.openDataBase();    
        
        String[] ss = getResources().getStringArray(R.array.ForName);
        ListView ls = (ListView) findViewById(R.id.list_menu);
        ls.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item, ss));
        ls.setOnItemClickListener(new DrawerItemClickListener());
        
        fillDish();  setUpList();    
    }	
		
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	Intent intent;
	        switch(position)
	        {
	        case 0:
	        	 intent = new Intent(SearchName.this, MainActivity.class);
	    	   	startActivity(intent);
	    	   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2); 
	    	   	 finish();
	        	break;
	        case 1:
	        	 intent = new Intent(SearchName.this, SearchGroup.class);
	    	   	startActivity(intent);
	    	   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2); 
	    	   	 finish();
	        	break;
	        case 2:
	        	finish();
	        	break;
	        }
	    }
	}
	
	private void fillDish() 
		{
			Cursor friendCursor = null;
			dishes = new ArrayList<String>();					
			friendCursor = db.rawQuery("SELECT dish_name FROM dish", null);
			friendCursor.moveToFirst();
			if(!friendCursor.isAfterLast()) {
				do {
					String name = friendCursor.getString(0);
					dishes.add(name);
				} while (friendCursor.moveToNext());
			}
			friendCursor.close();
		}
		
	private void setUpList() 
	{
		listView = (ListView) findViewById(R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dishes));
        	
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
				Intent intent = new Intent(SearchName.this, Rezept_Dish.class);
			   	 intent.putExtra("dish", ((TextView) view).getText());
			   	intent.putExtra("color", "green");
			       startActivity(intent);
							
			}
		});
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.main, menu);
	        final ArrayAdapter<String> arr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dishes);
	        MenuItem searchItem = menu.findItem(R.id.action_search);	        
	        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

	            @Override
	            public boolean onMenuItemActionExpand(MenuItem item) {
	                // Do whatever you need
	                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
	            }

	            @Override
	            public boolean onMenuItemActionCollapse(MenuItem item) {
	            	falsh.clear();
	            	listView = (ListView) findViewById(R.id.list);
	        		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	        		listView.setAdapter(arr);
			
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
					Intent intent = new Intent(SearchName.this, Rezept_Dish.class);
				   	 intent.putExtra("dish", ((TextView) view).getText());
				   	intent.putExtra("color", "green");
				       startActivity(intent);
								
				}
			});
	                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
	            }
	        });
	        
	        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	        searchView.setQueryHint("Пошук");
	        searchView.setOnQueryTextListener(this);
	        
	        return true;
	    }

	 public boolean onOptionsItemSelected(MenuItem item) {    
	    	if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))
	            return true;
	    	return super.onOptionsItemSelected(item);
	    }

	    public boolean onQueryTextChange(String text_new) {
	        return true;
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	     
	        // Просто вызов
	        if (mDrawerToggle != null)
	            mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    
	    public boolean onQueryTextSubmit(String text) {   
	    	falsh.clear();

	    	String[] arr = text.split(" ");
	        char[] c; 
	        StringBuilder stringBuilder = new StringBuilder();
	        for (String s : arr) {
	            c = s.toCharArray();
	            c[0] = Character.toUpperCase(c[0]);
	            stringBuilder.append(c);
	            stringBuilder.append(" ");
	        }
	    	String ss = stringBuilder.toString().trim();
	        
	    	
	    	for(int i=0;i<dishes.size();i++)
	    	{
	    		if(dishes.get(i).contains(ss))
	    		{	    			
	    		falsh.add(dishes.get(i));
	    		}
	    	
	    	}
	    	   	if(!falsh.isEmpty()){
	    	   		
	    	   		listView = (ListView) findViewById(R.id.list);
	    			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	    			listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, falsh));
			
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
					Intent intent = new Intent(SearchName.this, Rezept_Dish.class);
				   	 intent.putExtra("dish", ((TextView) view).getText());
				       startActivity(intent);
								
				}
			});
	    	   	} else 
	    	   	{
	    	   		listView = (ListView) findViewById(R.id.list);
	    			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	    			listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dishes));
			
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
					Intent intent = new Intent(SearchName.this, Rezept_Dish.class);
				   	 intent.putExtra("dish", ((TextView) view).getText());
				       startActivity(intent);
								
				}
			});
	    	   	}
	        return true;
	    }
	
	    
	    
	    private void initDrawer() {
	    	DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer);
	     
	        if (mDrawer == null)
	            return;
	     
	        // Создадим drawer toggle для управления индикатором сверху
	         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 
	            R.drawable.ic_drawer, R.string.opened, R.string.closed);
	     
	        // Назначим его drawer-у как слушателя
	        mDrawer.setDrawerListener(mDrawerToggle);
	     
	        // Для красоты добавим тень с той же гравитацией
	        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
	     
	        // Включим кнопки на action bar
	        this.getActionBar().setDisplayHomeAsUpEnabled(true);
	       this.getActionBar().setHomeButtonEnabled(true);
	    }


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	 
	    if (mDrawerToggle != null)
	        mDrawerToggle.syncState();
	}
}
