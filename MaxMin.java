package com;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class MaxMin{

	static ArrayList<Task> tasklist = new ArrayList<Task>();

public static void maxMin(DefaultTableModel dtm,int processors,Processor p_list[]) {
	tasklist.clear();
	for(int i=0;i<dtm.getRowCount();i++){
		Task task = new Task();
		String name = dtm.getValueAt(i,0).toString().trim();
		String time = dtm.getValueAt(i,1).toString().trim();
		task.setID(i);
		task.setName(name);
		task.setExecutionTime(Integer.parseInt(time));
		tasklist.add(task);
	}
	java.util.Collections.sort(tasklist,new Task());
	p_list = new Processor[processors];
	for(int i=0;i<p_list.length;i++){
		Processor p = new Processor("Processor "+i,dtm);
		p_list[i] = p;
	}
	long start = System.currentTimeMillis();
	for(int i=tasklist.size()-1;i>=0;i--){
		Task task = tasklist.get(i);
		Processor p = null;
		int max = 100000;
		for(int j=0;j<p_list.length;j++){
			if(p_list[j].remain_time <= max ) {
				max = p_list[j].remain_time;
				p = p_list[j];
			}
		}
		if(p != null) {
			task.setProcessor(p.id);
			p.addTask(task);
		}
	}
	try{
		for(int i=0;i<p_list.length;i++){
			p_list[i].join();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	long end = System.currentTimeMillis();
	InputScreen.maxmin = end - start;
	javax.swing.JOptionPane.showMessageDialog(null,"MAX MIN algorithm execution time : "+InputScreen.maxmin); 
}
}