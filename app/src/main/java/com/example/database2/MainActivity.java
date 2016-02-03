package com.example.database2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements OnQueryTextListener {

	ListView lvMain;
	private ArrayList<String> ind = new ArrayList<String>();
	private ArrayList<String> ind_true;
	private ArrayList<Integer> ind_id;
	private ArrayList<String> falsh = new ArrayList<String>();
	
	private ArrayList<String> arr_check = new ArrayList<String>();
	
	ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        initDrawer();
        read();
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvMain.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, ind));
        
        String[] ss = getResources().getStringArray(R.array.ForIngrid);
        ListView ls = (ListView) findViewById(R.id.list_menu);
        ls.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item, ss));
        ls.setOnItemClickListener(new DrawerItemClickListener());
        //Add_edit();
    }
  
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	Intent intent;
	        switch(position)
	        {
	        case 0:
	        	 intent = new Intent(MainActivity.this, SearchName.class);
	    	   	startActivity(intent);
	    	   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2); 
	    	   	 finish();
	        	break;
	        case 1:
	        	 intent = new Intent(MainActivity.this, SearchGroup.class);
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
    
    public void read()
    {    	    	
    	String DB_NAME = "mydatabase.db";
    	ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
    	SQLiteDatabase db = dbOpenHelper.openDataBase();
		Cursor friendCursor = null;						
		friendCursor = db.rawQuery("SELECT name FROM ingred_name", null);
		friendCursor.moveToFirst();
		if(!friendCursor.isAfterLast()) {
			do {
				String name = friendCursor.getString(0);
				ind.add(name);
			} while (friendCursor.moveToNext());
		}	
	
		friendCursor.close();
    }
    
   /* public void Add_edit()
    {
    	
    	/*TranslateAnimation anim = new TranslateAnimation(0, 0, 0,50);
		anim.setDuration(1000);
		buton.startAnimation(anim);						
		//buton.setTranslationY(buton.getTranslationY()+50);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.button_move);
		buton.startAnimation(anim);	
		
		Spec row = GridLayout.spec(count, 1); 
        Spec column = GridLayout.spec(0, 1);
        GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, column);

    	LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	for(int i = 0;i<ind.size();i++)
    	{
    		EditText edi = new EditText(this);
            edi.setText(ind.get(i));
            edi.setId(i);
            edi.setTextSize(50);
            Layout.addView(edi,layparams);
            Layout.addv
            CheckBox ch = new CheckBox(this);
            layparams.rightMargin=10;
            layparams.leftMargin=5;
           // layparams.gravity=Gravity.RIGHT;
            Layout.addView(ch,layparams);
            
    	}
    }*/
    
    public void Search(View v)
    {    	
    	ind_true=new ArrayList<String>();
    	SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
    	if (sbArray.size()==0) 
    	{
    		Toast.makeText(getApplicationContext(),
    				"q1",
    				 Toast.LENGTH_SHORT).show();
    		return;
    	}
        for (int i = 0; i < sbArray.size(); i++) {
          int key = sbArray.keyAt(i);
          if (sbArray.get(key))
        	  if(sbArray.get(key)==true)
        	  {
        	  if((falsh.isEmpty()) && (sbArray.get(key)==true))
        	  {
            ind_true.add(ind.get(key));
        	  } else if((!falsh.isEmpty()) && (sbArray.get(key)==true))
        		  ind_true.add(falsh.get(key));
        	  }
        	  else if(sbArray.get(key)==false) continue;        	  
          
        }
          
        boolean b=false;
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)==true) b=true;
        }
            	
       
            	if(b)
    	if(getId())
    	{
    		Toast.makeText(getApplicationContext(),
    				"q2",
    				 Toast.LENGTH_SHORT).show();
    		return;
    	}
    	if(ind_true.isEmpty() || (ind_true.size()==0)){
        	Toast.makeText(getApplicationContext(),
    				"q3",
    				 Toast.LENGTH_SHORT).show();
    	} else 
        {Intent intent = new Intent(MainActivity.this, Dishes.class);
   	 intent.putExtra("indigridi", ind_true);
   	 intent.putExtra("count", ind_true.size());
   	 intent.putExtra("id", ind_id);
   	startActivity(intent);
   	
   	 overridePendingTransition(R.animator.animation_form1_to_form2, R.animator.animation_form1_to_form2_2);  
   	 this.finish();
        }
    }
  
    private boolean getId()
    {
    	
	 String DB_NAME = "mydatabase.db";
    	ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
    	SQLiteDatabase db = dbOpenHelper.openDataBase();
    	SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();        
		Cursor friendCursor = null;
	    ind_id = new ArrayList<Integer>();
		try
		{
		for(int i=0;i<sbArray.size();i++)
		{					
		friendCursor = db.rawQuery("SELECT id_dish FROM ingredients WHERE id_ingred = (SELECT _id from ingred_name WHERE name = '"+ ind_true.get(i)+"')", null);
		friendCursor.moveToFirst();
		if(!friendCursor.isAfterLast()) {
			do {
				String name = friendCursor.getString(0);
				if(!ind_id.contains(Integer.parseInt(name)))
				ind_id.add(Integer.parseInt(name));				
			} while (friendCursor.moveToNext());
		}
		
			}
		}catch(Exception e){}
		friendCursor.close();
		 if(ind_id.isEmpty() || (ind_id.size()==0)) return true; else return false;
		
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final ArrayAdapter<String> arr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ind);
        MenuItem searchItem = menu.findItem(R.id.action_search);

		MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {         	
            	
            //	Button b = (Button) findViewById(R.id.next);
        //        b.setEnabled(true);
            	
            	lvMain = (ListView) findViewById(R.id.lvMain);  
                SparseBooleanArray realy_true = lvMain.getCheckedItemPositions();
                
            	
                for (int i = 0; i < realy_true.size(); i++) 
                {
                	int key = realy_true.keyAt(i);
                    if (realy_true.get(key))      
                    {
                	  arr_check.add(falsh.get(realy_true.keyAt(i)));
                } else if (!realy_true.get(key) && (arr_check.contains(falsh.get(realy_true.keyAt(i)))))
            	{
            		arr_check.remove(falsh.get(realy_true.keyAt(i)));
            		
            	}
                }
                
                lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);                
                lvMain.setAdapter(arr);
                
                for(int j=0;j<arr_check.size();j++)
                    for(int i=0;i<ind.size();i++)
                    {
                    	if (ind.get(i)==arr_check.get(j))
                  	  lvMain.setItemChecked(i, true);
                    }
                
                falsh.clear();
                arr_check.clear();
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });
        
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("введіть назву");
        searchView.setOnQueryTextListener(this);
        
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {    
    //	Button b = (Button) findViewById(R.id.next);
     //   b.setEnabled(false);
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
		boolean check_falsh=false;
		ArrayList<String> falsh_falsh = new ArrayList<String>();
		if(falsh.isEmpty()){
			check_falsh=true;		
		} else 
		{
			for(int i = 0; i<falsh.size();i++)
			{
				falsh_falsh.add(falsh.get(i));
			}
			falsh.clear();
			check_falsh=false;
		}
    	String ss = text.toLowerCase();
    	for(int i=0;i<ind.size();i++)
    	{
    		if(ind.get(i).contains(ss)){
    			
    		falsh.add(ind.get(i));
    		}
    	
    	}
    	try {
    	   	if(!falsh.isEmpty()){    	   		
    	   		
    	        lvMain = (ListView) findViewById(R.id.lvMain);
        SparseBooleanArray realy_true = lvMain.getCheckedItemPositions();

        for (int i = 0; i < realy_true.size(); i++) {
          int key = realy_true.keyAt(i);
          if (realy_true.get(key) && (check_falsh==true))   
          {
        	  arr_check.add(ind.get(realy_true.keyAt(i)));
        } else 
        	if (realy_true.get(key) && (check_falsh==false))  
        	arr_check.add(falsh_falsh.get(realy_true.keyAt(i)));
        	else if (!realy_true.get(key) && (arr_check.contains(falsh_falsh.get(realy_true.keyAt(i)))))
        	{
        		arr_check.remove(falsh_falsh.get(realy_true.keyAt(i)));
        		
        	}
        }
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvMain.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, falsh));
        
        for(int j=0;j<arr_check.size();j++)
        for(int i=0;i<falsh.size();i++)
        {
        	if (falsh.get(i)==arr_check.get(j))
      	  lvMain.setItemChecked(i, true);
        }
        
    	   	}
     else
     {
    	 Toast.makeText(getApplicationContext(),
 				"q5",
 				 Toast.LENGTH_LONG).show();
    	 
    	 lvMain = (ListView) findViewById(R.id.lvMain);
         SparseBooleanArray realy_true = lvMain.getCheckedItemPositions();
         
         for (int i = 0; i < realy_true.size(); i++) {
           int key = realy_true.keyAt(i);
           if (realy_true.get(key) && (check_falsh==true))   
           {
         	  arr_check.add(ind.get(realy_true.keyAt(i)));
         } else 
         	if (realy_true.get(key) && (check_falsh==false))  
         	arr_check.add(falsh_falsh.get(realy_true.keyAt(i)));
         	else if (!realy_true.get(key) && (arr_check.contains(falsh_falsh.get(realy_true.keyAt(i)))))
         	{
         		arr_check.remove(falsh_falsh.get(realy_true.keyAt(i)));
         		
         	}
         }
         lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
         lvMain.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, ind));
         
         for(int j=0;j<arr_check.size();j++)
         for(int i=0;i<ind.size();i++)
         {
         	if (ind.get(i)==arr_check.get(j))
       	  lvMain.setItemChecked(i, true);
         }
         

         
     	   	}
    	}catch(Exception e){}
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
    }


@Override
protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
 
    if (mDrawerToggle != null)
        mDrawerToggle.syncState();
}
    
}