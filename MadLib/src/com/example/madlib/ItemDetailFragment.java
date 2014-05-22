package com.example.madlib;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Spinner;

import com.example.madlib.xml.Utils;
import com.example.madlib.xml.UIElement;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
 
    /**
     * The content this fragment is presenting.
     */
    private ArrayList<UIElement> items;
    private Context context;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment(){
    	
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            
        	items = Utils.LoadStory(getResources().getAssets(),Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
        	
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	context = container.getContext();
    	WrapPanel wrap = new WrapPanel(context);
    	//int height = 0;
        RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        for(UIElement e:items)
        {
        	
        	if (e.getComponent().equals("input")){
        		Spinner spinner = new Spinner(context);
        		spinner.setLayoutParams(lp);
        		int id = getResources().getIdentifier(e.getContent()+"_arrays", "array", context.getPackageName());
        		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,id,android.R.layout.simple_spinner_item) ;
        		spinner.setAdapter(adapter);
        		wrap.addView(spinner);
//        		height = spinner.getHeight();
        		
        	}
        	else if(e.getComponent().equals("title")){
        		
        		TextView txt = new TextView (getActivity());
        		txt.setText(e.getContent());
	           
        		txt.setLayoutParams(lp);
	            
	            
	            //skip adding for now
	            //wrap.addView(txt);
        	}
        	else{
        		for(String word:e.getContent().split(" ")){
        		TextView txt = new TextView(context);
        		txt.setText(word+" ");
	            //lp.height = height;
	            lp.addRule(RelativeLayout.CENTER_VERTICAL);
	            
	            wrap.addView(txt);
        		}
        	}
        }
        //container.addView(wrap);
        return wrap;
        
    }
}
