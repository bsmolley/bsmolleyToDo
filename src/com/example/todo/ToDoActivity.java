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
    	//theList.add("Victory!!!!");
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
					String newToDo = toDo.replace("[X] ", "");
					theToDoList.remove(position);
					theToDoList.add(position, newToDo);
					//toDoChecked -= 1;
				}
				else{
					String newToDo = "[X] "+ toDo;
					theToDoList.remove(position);
					theToDoList.add(position, newToDo);
					//toDoChecked += 1;
				}

				adapter.notifyDataSetChanged();
				
				return true;
			}
    		
    	});
    	
    	addBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				String input = toDoItem.getText().toString();
				addToDo(input);
				toDoItem.setText(null); // Resets the text field after message is entered
				adapter.notifyDataSetChanged();			
			}
		});
    	
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
    
    public void addToDo(String text){
    	if (text.length() > 0){
    		theToDoList.add(text); 
    		totalToDoItems += 1;
    	}
    	 	
    }
    
    public void delToDo(SparseBooleanArray array, ArrayAdapter<String> adapter, int size){
		for(int i = size - 1; i >= 0; i--){
            if(array.get(i)){
                adapter.remove(theToDoList.get(i));
                //totalToDoItems -= 1;
            }
        }
		array.clear();
    }
    
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
