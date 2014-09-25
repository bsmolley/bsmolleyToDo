package com.example.todo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Fragment {
		
    // Set up ArrayAdapter for use with the listview
	static ListView toDoList;
    static ArrayList<String> theToDoList = new ArrayList<String>();
    static ArrayAdapter<String> adapter;
    
    static int totalToDoItems = 0;
    static int toDoChecked = 0;
    static int toDoUnchecked = 0;  
  
	// The constructor
	public ToDoActivity(){		
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	// Used code from http://www.cs.dartmouth.edu/~campbell/cs65/lecture08/lecture08.html Sept 17, 2014
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	// Set up the view for the tab
        View todoView = inflater.inflate(R.layout.to_do_main, container, false);
        
    	// Set up the resources used in the layout
    	final Button addBtn = (Button) todoView.findViewById(R.id.todoAddBtn);
    	final Button delBtn = (Button) todoView.findViewById(R.id.todoDelBtn);
    	final Button arcBtn = (Button) todoView.findViewById(R.id.todoArcBtn);
    	final EditText toDoItem = (EditText) todoView.findViewById(R.id.todoMess);
    	toDoList = (ListView) todoView.findViewById(R.id.todoList);
    	
        // Set up and adapter for use with our list 
    	// Used list adapter code from http://sunil-android.blogspot.ca/2013/08/actionbar-tab-listfragment-in-android.html Sept 16, 2014
    	// Used code from http://stackoverflow.com/questions/15547997/android-listview-in-fragment-layout Sept 19, 2014
    	// Used code from http://www.androprogrammer.com/2014/03/create-android-dynamic-view-with.html Sept 19, 2014
    	adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_multiple_choice, theToDoList);
    	toDoList.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	
    	
    	toDoList.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String toDo = (String) toDoList.getAdapter().getItem(position);
				if (toDo.contains("[X] ")){
					String newToDo = toDo.replace("[X] ", "[  ] ");
					theToDoList.remove(position);
					theToDoList.add(position, newToDo);
					//toDoChecked -= 1;
				}
				else{
					String newToDo = toDo.replace("[  ] ", "[X] ");
					theToDoList.remove(position);
					theToDoList.add(position, newToDo);
					//toDoChecked += 1;
				}

				adapter.notifyDataSetChanged();
				
				return true;
			}
    		
    	});
    	
    	// Used code from http://stackoverflow.com/questions/5972306/set-temporary-text-in-a-edittext-textview-in-android Sept 18, 2014
    	addBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				String input = "[  ] " + toDoItem.getText().toString();
				addToDo(input);
				toDoItem.setText(null); // Resets the text field after message is entered
				adapter.notifyDataSetChanged();			
			}
		});
    	
    	// Used code from http://theopentutorials.com/tutorials/android/listview/android-multiple-selection-listview/ Sept 20, 2014
    	delBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				int listSize = toDoList.getCount();
				SparseBooleanArray checkedItems = toDoList.getCheckedItemPositions();
				delToDo(checkedItems, adapter, listSize);    
                adapter.notifyDataSetChanged();								
			}
		});
    	
    	arcBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				int listSize = toDoList.getCount();
				SparseBooleanArray checkedItems = toDoList.getCheckedItemPositions();
				ArchiveActivity.grabToDoItems(checkedItems, theToDoList, listSize);  
				delToDo(checkedItems, adapter, listSize);
                adapter.notifyDataSetChanged();	
			}
		});
    	
    	return todoView;
    }
    
	// Used code from http://stackoverflow.com/questions/16981501/android-listview-with-edittext-and-textview Sept 17, 2014
    public void addToDo(String text){
    	if (text.length() > 0){
    		theToDoList.add(text); 
    		totalToDoItems += 1;
    	}
    	 	
    }
    
    // Used code from http://wptrafficanalyzer.in/blog/deleting-selected-items-from-listview-in-android/ Sept 16, 2014
    public void delToDo(SparseBooleanArray array, ArrayAdapter<String> adapter, int size){
		for(int i = size - 1; i >= 0; i--){
            if(array.get(i)){
                adapter.remove(theToDoList.get(i));
                //totalToDoItems -= 1;
            }
        }
		array.clear();
    }
    
 // Used code from http://stackoverflow.com/questions/4540754/dynamically-add-elements-to-a-listview-android Sept 20, 2014
    public static void grabArchItems(SparseBooleanArray array, ArrayList<String> archList, int size){ 	
		for(int i = size - 1; i >= 0; i--){
            if(array.get(i)){
                theToDoList.add(archList.get(i));
                adapter.notifyDataSetChanged();            
            }
        }	
    }

	public static int grabToDoHistory() {
    	totalToDoItems = toDoList.getCount();
		return totalToDoItems;
	}

	public static int grabToDoChecked() {
		int size = theToDoList.size();
		toDoChecked = 0;
		
		for (int i = size - 1; i >= 0; i--){
			if (theToDoList.get(i).contains("[X] ")){
				toDoChecked += 1;
			}
		}
		return toDoChecked;
	}

	public static int grabToDoUnchecked() {
		totalToDoItems = toDoList.getCount();
		toDoUnchecked = totalToDoItems - toDoChecked;
		return toDoUnchecked;
	}

	public static ArrayList<String> grabToDoList() {
		return theToDoList;
	}
    
}
