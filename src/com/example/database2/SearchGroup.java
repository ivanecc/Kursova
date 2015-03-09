package com.example.database2;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchGroup extends Activity {
	
	ActionBarDrawerToggle mDrawerToggle;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_group);
        
        initDrawer();
        
        String[] ss = getResources().getStringArray(R.array.ForGroup);
        ListView ls = (ListView) findViewById(R.id.list_menu);
        ls.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item, ss));
        ls.setOnItemClickListener(new DrawerItemClickListener());
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	Intent intent;
	        switch(position)
	        {
	        case 0:
	        	 intent = new Intent(SearchGroup.this, MainActivity.class);
	    	   	startActivity(intent);
	    	   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2); 
	    	   	 finish();
	        	break;
	        case 1:
	        	 intent = new Intent(SearchGroup.this, SearchName.class);
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
	
	public void Img1(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "перша");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img2(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "друга");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img3(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "випічка");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img4(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "рибна");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img5(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "салат");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img6(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "десерт");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img7(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "соус");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
	}
	
	public void Img8(View v)
	{
		Intent intent = new Intent(SearchGroup.this, SearchGroupDishes.class);
		intent.putExtra("type", "напій");
	       startActivity(intent);       
	       overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);
	       this.finish();
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
     
        // Просто вызов
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
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
