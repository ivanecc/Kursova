package com.example.database2;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database2.entity.Dish;
import com.example.database2.entity.LinkList;
import com.example.database2.entity.ResponseCode;
import com.example.database2.entity.Wrapper;
import com.example.database2.roboCLass.BaseSpiceActivity;
import com.example.database2.task.GetPeopleDataRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

public class SearchName extends BaseSpiceActivity implements OnQueryTextListener {
	
	private static final String DB_NAME = "mydatabase.db";
	
    
	private SQLiteDatabase db;
	private ListView listView;
    private Button updateButton;
    GetPeopleDataRequest RequestGetUser;
	private ArrayList<String> dishes;
	private ArrayList<String> falsh = new ArrayList<String>();
	SharedPreferences sPref;
	String countDishOld;

    GetUsersDataRequestListener getUsersDataRequestListener = new GetUsersDataRequestListener();
    static ArrayList<Dish> arr_p;
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

        updateButton = (Button) findViewById(R.id.updateDish);


        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getSpiceManager().execute(RequestGetUser, getUsersDataRequestListener);
            }
        };

        updateButton.setOnClickListener(oclBtnOk);


        RequestGetUser = new GetPeopleDataRequest(LinkList.passToAllUser);


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
		//listView.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item_white_text, dishes));
        	
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
         MenuItem searchItem = (MenuItem) menu.findItem(R.id.action_search);
		 MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

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
            searchView.setQueryHint("vvedit");
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
	     
	        // ������ �����
	        if (mDrawerToggle != null)
	            mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    
	    public boolean onQueryTextSubmit(String text) {   
	    	falsh.clear();

	    	//String arr = text;//.split(" ");
	        char[] c; 
	        StringBuilder stringBuilder = new StringBuilder();
	        
	            c = text.toCharArray();
	            c[0] = Character.toUpperCase(c[0]);
	            stringBuilder.append(c);
	            stringBuilder.append(" ");
	        
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
	    			//listView.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item_white_text, falsh));
			
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
					Intent intent = new Intent(SearchName.this, Rezept_Dish.class);
				   	 intent.putExtra("dish", ((TextView) view).getText());
				       startActivity(intent);
								
				}
			});
	    	   	} else 
	    	   	{
	    	   		Toast.makeText(getApplicationContext(),
	        				"ͳ���� �� ��������",
	        				 Toast.LENGTH_LONG).show();
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
	     
	        // �������� drawer toggle ��� ���������� ����������� ������
	         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 
	            R.drawable.ic_drawer, R.string.opened, R.string.closed);
	     
	        // �������� ��� drawer-� ��� ���������
	        mDrawer.setDrawerListener(mDrawerToggle);
	     
	        // ��� ������� ������� ���� � ��� �� �����������
	        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
	     
	        // ������� ������ �� action bar

                this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                this.getSupportActionBar().setHomeButtonEnabled(true);

			sPref = getPreferences(MODE_PRIVATE);
			countDishOld = sPref.getString("k1", "");
			Toast.makeText(this, "Text open " + countDishOld, Toast.LENGTH_SHORT).show();

			sPref = getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor ed = sPref.edit();
			ed.putString("k1", "0");
			ed.commit();
	    }


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	 
	    if (mDrawerToggle != null)
	        mDrawerToggle.syncState();
	}

    public final class GetUsersDataRequestListener implements RequestListener<Wrapper> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(SearchName.this, "Request_Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final Wrapper result) {
            switch (result.code) {
                case ResponseCode.GET_USER_DATA_OK:
                    arr_p = (ArrayList<Dish>) result.obj;
					refreshDish(arr_p);
                    break;
            }
        }
    }

	public void refreshDish(ArrayList<Dish> arrayDish)
	{
		if(arrayDish.size()>Integer.parseInt(countDishOld)){

			saveCountDish(String.valueOf(arrayDish.size()));
			refreshDB(arrayDish);
		}

	}

	public void saveCountDish(String s){
		sPref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor ed = sPref.edit();
		ed.putString("k1", s);
		ed.commit();
		Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
	}

	public void refreshDB(ArrayList<Dish> arrayDish){
		ContentValues cv;
		for(int i = 0;i<arrayDish.size();i++) {
			cv = new ContentValues();
			cv.put("dish_name",arrayDish.get(i).dish_name);
			cv.put("recipe",arrayDish.get(i).recipe );
			cv.put("foto","fff");
			cv.put("type",arrayDish.get(i).type);
			db.insert("dish",null,cv);
		}
int kkk;
	}
}
