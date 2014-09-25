package com.example.todo;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EmailActivity extends Fragment {
	
	static ListView emailListView;
	static ArrayList<String> emailList = new ArrayList<String>();
	static ArrayAdapter<String> adapter;
	static EditText emailAddress;
	
	public EmailActivity(){
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	// Used code from http://www.tutorialspoint.com/android/android_sending_email.htm Sept 22, 2014
	// Used code from http://stackoverflow.com/questions/6383330/how-to-combine-two-array-list-and-show-in-a-listview-in-android Sept 22, 2014
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View emailView = inflater.inflate(R.layout.email_main, container, false);
        
        Button selAll = (Button) emailView.findViewById(R.id.emSelAllBtn);
        Button emailBtn = (Button) emailView.findViewById(R.id.emEmail);
        Button unSelAll = (Button) emailView.findViewById(R.id.emUnselAll);
        emailAddress = (EditText) emailView.findViewById(R.id.emEmailSpace);
             
        ArrayList<String> toDoList = ToDoActivity.grabToDoList();
        ArrayList<String> archList = ArchiveActivity.grabArchList();
        emailListView = (ListView) emailView.findViewById(R.id.emailListViewXML);
    	adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, emailList);
    	emailListView.setAdapter(adapter);
    	
    	// Compose the list that makes up the to do items
    	composeMailList(toDoList, archList);
    	adapter.notifyDataSetChanged();
    	
    	selAll.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				SparseBooleanArray checkedItems = emailListView.getCheckedItemPositions();
				for(int i = 0; i<=emailList.size(); i++){
					if (!checkedItems.get(i)){
						checkedItems.put(i, true);
					}
				}
				adapter.notifyDataSetChanged();

				
			}
		});
    	
    	unSelAll.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				SparseBooleanArray checkedItems = emailListView.getCheckedItemPositions();
				for(int i = 0; i<=emailList.size(); i++){
					if (checkedItems.get(i)){
						checkedItems.put(i, false);
					}
				}
				adapter.notifyDataSetChanged();
				
			}
		});
    	
    	emailBtn.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v) {
				sendEmail(emailAddress);		
			}
		});
 	

        return emailView;
    }
    
    private void sendEmail(EditText emailAddress) {
    	String[] address = {emailAddress.getText().toString()};
    	ArrayList<String> message = new ArrayList<String>();
    	
		SparseBooleanArray checkedItems = emailListView.getCheckedItemPositions();
		for(int i = 0; i<=emailList.size(); i++){
			if (checkedItems.get(i)){
				message.add(emailList.get(i));
			}
		}
		
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("message/rfc822");
		if (address != null)
			email.putExtra(Intent.EXTRA_EMAIL, address);
		email.putExtra(Intent.EXTRA_SUBJECT, "To Do List Items");
		email.putExtra(Intent.EXTRA_TEXT, message.toString());
		startActivity(email);
	}

	public void composeMailList(ArrayList<String> toDoList, ArrayList<String> archList) {
		emailList.clear();
		emailList.addAll(toDoList);
		emailList.addAll(archList);
		adapter.notifyDataSetChanged();
    }
    

}
