package com.example.todo;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EmailActivity extends Fragment {
	
	static ListView emailListView;
	static ArrayList<String> emailList = new ArrayList<String>();
	static ArrayAdapter<String> adapter;
	
	public EmailActivity(){
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View emailView = inflater.inflate(R.layout.email_main, container, false);
             
        ArrayList<String> toDoList = ToDoActivity.grabToDoList();
        ArrayList<String> archList = ArchiveActivity.grabArchList();
        emailListView = (ListView) emailView.findViewById(R.id.emailListViewXML);
        
    	adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, emailList);
    	
    	emailList.clear();
      	
    	emailList.addAll(toDoList);
    	emailList.addAll(archList);
    		
    	adapter.notifyDataSetChanged();

    	emailListView.setAdapter(adapter);
    	


        return emailView;
    }

}
