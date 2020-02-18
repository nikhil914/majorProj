package com;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class PTCT{

	static ArrayList<Task> tasklist = new ArrayList<Task>();

public static void ptct(DefaultTableModel dtm,int processors,Processor p_list[]) {
	tasklist.clear();
	for(int i=0;i<dtm.getRowCount();i++){
		Task task = new Task();
		String name = dtm.getValueAt(i,0).toString().trim();
		String time = dtm.getValueAt(i,1).toString().trim();
		task.setID(i);
		task.setName(name);
		task.setExecutionTime((Integer.parseInt(time)-500));
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
		ArrayList<PCA> pca = new ArrayList<PCA>();
		for(int j=0;j<p_list.length;j++){
			PCA p = new PCA();
			p.setProcessor(p_list[j]);
			p.setETC(p_list[j].remain_time);
			pca.add(p);
		}
		java.util.Collections.sort(pca,new PCA());
		Processor p = pca.get(0).getProcessor();
		task.setProcessor(p.id);
		p.addTask(task);
	}
	try{
		for(int i=0;i<p_list.length;i++){
			p_list[i].join();
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	long end = System.currentTimeMillis();
	InputScreen.ptct = end - start;
	javax.swing.JOptionPane.showMessageDialog(null,"PTCT algorithm execution time : "+InputScreen.ptct); 
}
}