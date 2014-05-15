package com.example.madlib;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            
        	items = Utils.LoadStory(getResources().getAssets(),Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
        	
        }
        
    }

    @Override
    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.fragment_item_detail, container, false);
        
        int[] counts= {6000,0,0};
        //String lastComponent="";
        for(UIElement e:items)
        {
        	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        	
        	if (e.getComponent().equals("input")){
        		//use spinner and follow previous item
        		Spinner spinner = new Spinner(getActivity());
        		spinner.findViewById(R.layout.spinner);
        		spinner.setId(counts[0]);
        		//if(lastComponent.equals("title"))
        			//lp.addRule(RelativeLayout.BELOW,counts[0]-1);
        		lp.addRule(RelativeLayout.BELOW,counts[0]-1);
        		spinner.setLayoutParams(lp);
        		
        		spinner.setContentDescription(e.getContent());
        		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.array.product_arrays) ;
        		adapter.setDropDownViewResource(R.layout.simple_spinner);
        		spinner.setAdapter(adapter);
        		//lastComponent = e.getComponent();
        		counts[0]++;
        		view.addView(spinner);
        		
        	}
        	else if(e.getComponent().equals("title")){
        		//alight textarea to center
        		TextView txt = new TextView (getActivity());
        		txt.setText(e.getContent());
        		lp.addRule(RelativeLayout.BELOW,counts[0]-1);     		
	            txt.setLayoutParams(lp);
	            txt.setId(counts[0]);
	            txt.setWidth(300);        		
        		counts[0]++;
        		view.addView(txt);
        	}
        	else{
        		//static content should follow previous item
	            //((TextView) view.findViewById(R.id.item_detail)).setText(content);
	            TextView txt = new TextView(getActivity());
	            txt.setText(e.getContent());
	            //if(lastComponent.equals("title"))
        		//	lp.addRule(RelativeLayout.BELOW,counts[0]-1);
	        	
	            lp.addRule(RelativeLayout.BELOW,counts[0]-1);
        		
	            txt.setLayoutParams(lp);
	            txt.setId(counts[0]);
	            txt.setWidth(300);
	            counts[0]++;
	            view.addView(txt);
        	}
        }   
        return view;
        
    }
}
