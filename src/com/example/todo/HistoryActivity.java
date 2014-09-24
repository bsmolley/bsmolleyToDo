package com.example.todo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoryActivity extends Fragment{
	
	//TextView totalToDoText;
	
	public HistoryActivity(){
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View histView = inflater.inflate(R.layout.history_main, container, false);
        
        int totalToDoItems = ToDoActivity.grabToDoHistory();
        int toDoChecked = ToDoActivity.grabToDoChecked();
        int toDoUnchecked = ToDoActivity.grabToDoUnchecked();
        int archItems = ArchiveActivity.grabArchiveHistory();
        int archChecked = ArchiveActivity.grabArchChecked();
        int archUnchecked = ArchiveActivity.grabArchUnchecked();
               
        TextView totalToDoText = (TextView) histView.findViewById(R.id.totalText);
        TextView toDoCheckedText = (TextView) histView.findViewById(R.id.checkText);
        TextView toDoUncheckedText = (TextView) histView.findViewById(R.id.uncheckText);        
        TextView totalArchText = (TextView) histView.findViewById(R.id.archText);
        TextView archCheckedText = (TextView) histView.findViewById(R.id.archCheck);
        TextView archUncheckedText = (TextView) histView.findViewById(R.id.archUncheck);
        
        String totalToDoString = totalToDoText.getText().toString() + " " + Integer.toString(totalToDoItems);
        String toDoCheckedString = toDoCheckedText.getText().toString() + " " + Integer.toString(toDoChecked);
        String toDoUncheckedString = toDoUncheckedText.getText().toString() + " " + Integer.toString(toDoUnchecked);
        String totalArchString = totalArchText.getText().toString() + " " + Integer.toString(archItems);
        String archCheckedString = archCheckedText.getText().toString() + " " + Integer.toString(archChecked);
        String archUncheckedString = archUncheckedText.getText().toString() + " " + Integer.toString(archUnchecked);
        
        totalToDoText.setText(totalToDoString);
        toDoCheckedText.setText(toDoCheckedString);
        toDoUncheckedText.setText(toDoUncheckedString);
        totalArchText.setText(totalArchString);
        archCheckedText.setText(archCheckedString);
        archUncheckedText.setText(archUncheckedString);
        
                
        return histView;
    }
   
	
}
