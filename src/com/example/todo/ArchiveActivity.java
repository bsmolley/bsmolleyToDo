package com.example.todo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ArchiveActivity extends Fragment{
	
	// Setting up the array/adapter that will interact with the listview
	static ListView archList;
	static ArrayList<String> theArchList = new ArrayList<String>();
	static ArrayAdapter<String> adapter;
	
    static int archItems = 0;
    static int archChecked = 0;
    static int archUnchecked = 0; 
	
	// Constructor
	public ArchiveActivity(){		
	}
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	// Set up the view for the tab
        View archiveView = inflater.inflate(R.layout.archive_main, container, false);
        
     	// Set up the resources used in the layout
    	final Button archUn = (Button) archiveView.findViewById(R.id.archUnBtn);
    	final Button archDel = (Button) archiveView.findViewById(R.id.archDelBtn);
    	archList = (ListView) archiveView.findViewById(R.id.archListView);
    	
        // Set up and adapter for use with our list 
    	adapter = new ArrayAdapter<String>(getActivity(), 
    			android.R.layout.simple_list_item_multiple_choice, theArchList);

    	archList.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	
    	archList.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String toDo = (String) archList.getAdapter().getItem(position);
				if (toDo.contains("[X] ")){
					String newToDo = toDo.replace("[X] ", "[  ] ");
					theArchList.remove(position);
					theArchList.add(position, newToDo);
				}
				else{
					String newToDo = toDo.replace("[  ] ", "[X] ");
					theArchList.remove(position);
					theArchList.add(position, newToDo);
				}

				adapter.notifyDataSetChanged();
				
				return true;
			}
    		
    	});
    	               	
    	archUn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int listSize = archList.getCount();
				SparseBooleanArray checkedItems = archList.getCheckedItemPositions();
				ToDoActivity.grabArchItems(checkedItems, theArchList, listSize);	
				checkedItems = archList.getCheckedItemPositions();
				delArch(checkedItems, adapter, listSize); 
				
				adapter.notifyDataSetChanged();	
				
			}
    		
    	});
    	
    	archDel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int listSize = archList.getCount();
				SparseBooleanArray checkedItems = archList.getCheckedItemPositions();
				delArch(checkedItems, adapter, listSize);          
                adapter.notifyDataSetChanged();	
				
			}
    		
    	});  	
    	
        return archiveView;
    }
    
    public void delArch(SparseBooleanArray array, ArrayAdapter<String> adapter, int size){
		for(int i = size - 1; i >= 0; i--){
            if(array.get(i)){
                adapter.remove(theArchList.get(i));
                //archItems -= 1;
            }
        }
		array.clear();
    }
    
    public static void grabToDoItems(SparseBooleanArray array, ArrayList<String> toDoList, int size){  	
		for(int i = size - 1; i >= 0; i--){
           if(array.get(i)){
				theArchList.add(toDoList.get(i));
				adapter.notifyDataSetChanged();
           }
        }
    }

	public static int grabArchiveHistory() {
		archItems = archList.getCount();
		return archItems;
	}

	public static int grabArchChecked() {
		int size = theArchList.size();
		archChecked = 0;
		
		for (int i = size - 1; i >= 0; i--){
			if (theArchList.get(i).contains("[X] ")){
				archChecked += 1;
			}
		}
		return archChecked;
	}

	public static int grabArchUnchecked() {
		archItems = archList.getCount();
		archUnchecked = archItems - archChecked;
		return archUnchecked;
	}

	public static ArrayList<String> grabArchList() {
		return theArchList;
	}
    
}
