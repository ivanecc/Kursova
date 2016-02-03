package com.example.database2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class Rezept_Dish extends FragmentActivity {
    private static final String DB_NAME = "mydatabase.db";
    private SQLiteDatabase db;
    ListView listView;

    private ArrayList<String> ind_name;
    private ArrayList<String> ind_count = new ArrayList<String>();
    Integer ind_id;

    String dish_name, rezept, foto, color;

    private TabHost mTabHost;

    private ViewPager mViewPager;

    private TabsAdapter mTabsAdapter;

    private SharedPreferences mSettings;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_dish);
        
        /*TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("������");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("�����䳺���");
        tabSpec.setContent(R.id.tab2);        
        tabHost.addTab(tabSpec);
        */
        //tabHost.setCurrentTabByTag("tag2");

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup();


        mViewPager = (ViewPager) findViewById(R.id.pager);


        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);


        mTabsAdapter.addTab(mTabHost.newTabSpec("rezept").setIndicator("������"), RezeptFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("ingred").setIndicator("�����䳺���"), IngredFragments.class, null);


        if (savedInstanceState != null) {

            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));

        }


        dish_name = getIntent().getExtras().getString("dish");
        color = getIntent().getExtras().getString("color");


        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        db = dbOpenHelper.openDataBase();

        getDish();
        //   getIndig();
        //setUpList();

        mSettings = getSharedPreferences("ka", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("kaka1", dish_name);
        editor.putString("kaka2", rezept);
        editor.putString("kaka3", foto);
        editor.putInt("kaka4", ind_id);
        editor.apply();


    }

	/*private void getCount()
	{Cursor friendCursor = null;
		for(int i=0;i<ind_name.size();i++)
		{
		friendCursor = db.rawQuery("SELECT quantity FROM ingredients WHERE name ='"+ind_name.get(i)+"' AND id_dish = '"+ind_id+"'", null);
		friendCursor.moveToFirst();
		if(!friendCursor.isAfterLast()) {
			do {				
					ind_count.add(friendCursor.getString(0));				   
			} while (friendCursor.moveToNext());
		}
		}
		friendCursor.close();	
	}*/
	
	/*private void setUpList() {
		getCount();
		ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		
		for(int i=0;i<ind_name.size();i++)
		{
			map = new HashMap<String, String>();
			map.put("Name", ind_name.get(i));
			map.put("Count", ind_count.get(i));
			myArrList.add(map);
		}
		
		
		
		
		SimpleAdapter adapter = new SimpleAdapter(this, myArrList,
	            R.layout.row, new String[] { "Name", "Count" }, new int[] { R.id.NameIngrid, R.id.CountIngrid });
		//setListAdapter(adapter);
	
	}*/
	
	/*public void getIndig()
	{
		ind_name = new ArrayList<String>();
		Cursor ingredientsCursor = db.rawQuery("SELECT name FROM ingredients WHERE id_dish = ?", new String[] {Integer.toString(ind_id)});
		ingredientsCursor.moveToFirst();
		if(!ingredientsCursor.isAfterLast()) {
			do {				
				ind_name.add(ingredientsCursor.getString(0));					   
			} while (ingredientsCursor.moveToNext());
		}
		ingredientsCursor.close();
		}*/

    public void getDish() {
        Cursor friendCursor = db.rawQuery("SELECT recipe,foto,_id FROM dish WHERE dish_name ='" + dish_name + "'", null);
        friendCursor.moveToFirst();
        if (!friendCursor.isAfterLast()) {
            do {
                rezept = friendCursor.getString(0);
                foto = friendCursor.getString(1);
                String idha = friendCursor.getString(2);
                ind_id = Integer.parseInt(idha);
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        android.app.ActionBar bar = getActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        // �������� ��� ���������� ������ ����
        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
