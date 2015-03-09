package com.example.database2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RezeptFragment extends Fragment {

String r,n;
private SharedPreferences mSettings;
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    

}

@Override

public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.rezept_fragment, container, false);
    mSettings = getActivity().getSharedPreferences("ka", Context.MODE_PRIVATE);
    if (mSettings.contains("kaka")) {
        // Получаем число из настроек
        n = mSettings.getString("kaka1", "Suka");
        r = mSettings.getString("kaka2", "Suka");
        // Выводим на экран данные из настроек
        TextView t1 = (TextView) view.findViewById(R.id.dish_name);
        t1.setText(n);
        TextView t2 = (TextView) view.findViewById(R.id.dish_rezept);
        t2.setText(r);
        ImageView img = (ImageView) view.findViewById(R.id.dish_image);
        img.setImageResource(getResources().getIdentifier(mSettings.getString("kaka3", "Suka"), "drawable", getActivity().getPackageName()));
    }
    
    
    
  /* Bundle bundlqe = getArguments();
    if (bundlqe != null)
    {
    n=getArguments().getString("kaka1");
  //  r=bundle.getString("kaka2");
    TextView t223 = (TextView) getActivity().findViewById(R.id.dish_name);
    //t1.setText("ka");
    TextView t2 = (TextView) view.findViewById(R.id.dish_rezept);
    t2.setText(r);
}*/


    return view;

}

public void kalaka()
{
	

}

}