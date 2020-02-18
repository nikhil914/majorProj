package com;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
public class Processor extends Thread{
	ArrayList<Task> tasks;
	int remain_time;
	int total_time;
	boolean status;
	DefaultTableModel dtm;
	int index;
	String id;
public Processor(String id,DefaultTableModel dtm) {
	this.id = id;
	tasks = new ArrayList<Task>();
	this.dtm = dtm;
}
public void addTask(Task task) {
	total_time = total_time + task.getExecutionTime();
	this.remain_time = this.remain_time + task.getExecutionTime();
	tasks.add(task);
	System.out.println(task.getName()+" "+task.getProcessor()+" "+this.remain_time);
	if(!status) {
		status = true;
		start();
	}
}

public void run(){
	runTask();
}
public void runTask() {
	try{
		while(index < tasks.size()){
			Task task = tasks.get(index);
			UpdateTask ut = new UpdateTask(dtm,task.getID(),"Assigned To "+task.getProcessor());
			System.out.println(task.getName()+" == "+task.getProcessor()+" "+this.remain_time);
			sleep(task.getExecutionTime());
			ut = new UpdateTask(dtm,task.getID(),"Task Done. Exited From "+task.getProcessor());
			index = index + 1;
			this.remain_time = this.remain_time - task.getExecutionTime();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}