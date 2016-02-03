package com.example.database2;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchGroupDishes extends ListActivity {
	
	private static final String DB_NAME = "mydatabase.db";
	
    
	private SQLiteDatabase db;
	private ListView listView;
	private ArrayList<String> dishes;
	String type = new String();
	//////////////////////////////////////////

	ActionBarDrawerToggle mDrawerToggle;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_group_dishes);
        initDrawer();
        
        type=getIntent().getExtras().getString("type");
        
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        db = dbOpenHelper.openDataBase();    
        
        fillDish(); setUpList();        
        
        String[] ss = getResources().getStringArray(R.array.ForGroupDishes);
        ListView ls = (ListView) findViewById(R.id.list_menu);

        ls.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item, ss));
        ls.setOnItemClickListener(new DrawerItemClickListener());
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
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_without_search, menu);
        
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
     
        // ������ �����
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }
    

    
    
    private void initDrawer() {
    	DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer);
     
        if (mDrawer == null)
            return;
     
        // �������� drawer toggle ��� ���������� ����������� ������
         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 
            R.drawable.ic_drawer, R.string.opened, R.string.closed);
     
        // �������� ��� drawer-� ��� ���������
        mDrawer.setDrawerListener(mDrawerToggle);
     
        // ��� ������� ������� ���� � ��� �� �����������
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
     
        // ������� ������ �� action bar
        this.getActionBar().setDisplayHomeAsUpEnabled(true);
       this.getActionBar().setHomeButtonEnabled(true);
    }


@Override
protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
 
    if (mDrawerToggle != null)
        mDrawerToggle.syncState();
}




private class DrawerItemClickListener implements ListView.OnItemClickListener {
@Override
public void onItemClick(AdapterView parent, View view, int position, long id) {
	Intent intent;
    switch(position)
    {
    case 0:
    	 intent = new Intent(SearchGroupDishes.this, SearchGroup.class);
          startActivity(intent);       
          overridePendingTransition(R.animator.animation_form2_to_form1_in, R.animator.animation_form2_to_form1_out);
          finish(); 	 
   	break;
   	
    case 1:
    	 intent = new Intent(SearchGroupDishes.this, SearchName.class);
	   	startActivity(intent);
	   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2); 
	   	 finish();
    	break;
    case 2:
    	intent = new Intent(SearchGroupDishes.this, MainActivity.class);
	   	startActivity(intent);
	   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2); 
	   	 finish();
	   	 break;
    case 3:
	   	 finish();
    	break;
    	
    
    }
}
}
}
