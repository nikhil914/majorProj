package com;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import java.util.Random;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
public class InputScreen extends JFrame {
	JLabel l1,l2,l3;
	JTextField tf1,tf2;
	Font f1;
	JPanel p1,p2,p3;
	LineBorder border;
	TitledBorder title;
	JTable table;
	static DefaultTableModel dtm;
	JScrollPane jsp;
	JButton b1,b2,b3,b4,b5;
	int processors,task;
	ArrayList<Task> tasklist = new ArrayList<Task>();
	Processor p_list[];
	static long minmin,maxmin,ptct;
public InputScreen(){
	super("PTCT Task Scheduling Algorithm");
	getContentPane().setLayout(new BorderLayout());
	p1 = new JPanel();
	p1.setBackground(new Color(81,123,138));
	f1 = new Font("Courier New", Font.BOLD, 16);
	l1 = new JLabel("<HTML><BODY><CENTER>A Task Scheduling Algorithm with Improved Makespan Based on Prediction of Tasks<br/>Computation Time algorithm for Cloud Computing</CENTER></BODY></HTML>");
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
	p2 = new JPanel();
	p2.setPreferredSize(new Dimension(600,150));
	p2.setBackground(Color.white);
	p2.setLayout(new MigLayout("wrap 2"));
	border = new LineBorder(new Color(42,140,241),1,true);
	title = new TitledBorder (border,"Simulation Configuration Screen",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION, new Font("Tahoma",Font.BOLD,16),Color.darkGray);
	p2.setBorder(title);
	f1 = new Font("Courier New",Font.BOLD,14);
	
	l2 = new JLabel("Number Of Processors");
	l2.setFont(f1);
	p2.add(l2);
	tf1 = new JTextField(10);
	tf1.setFont(f1);
	p2.add(tf1);

	l3 = new JLabel("Number Of Tasks");
	l3.setFont(f1);
	p2.add(l3);
	tf2 = new JTextField(10);
	tf2.setFont(f1);
	p2.add(tf2);

	b1 = new JButton("Calculate Random Execution");
	b1.setFont(f1);
	p2.add(b1,"span,split 3");
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			generateExecutionTime();
		}
	});

	b2 = new JButton("Run Min-Min Algorithm");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Runnable r = new Runnable(){
				public void run() {
					minmin();
				}
			};
			new Thread(r).start();
		}
	});

	b3 = new JButton("Run Max-Min Algorithm");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			for(int i=0;i<dtm.getRowCount();i++){
				dtm.setValueAt("-",i,2);
			}
			Runnable r = new Runnable(){
				public void run() {
					MaxMin.maxMin(dtm,processors,p_list);
				}
			};
			new Thread(r).start();
		}
	});

	b4 = new JButton("Run PTCT Algorithm");
	b4.setFont(f1);
	p2.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			for(int i=0;i<dtm.getRowCount();i++){
				dtm.setValueAt("-",i,2);
			}
			Runnable r = new Runnable(){
				public void run() {
					PTCT.ptct(dtm,processors,p_list);
				}
			};
			new Thread(r).start();
		}
	});

	b5 = new JButton("Makespan Time Comparsion Graph");
	b5.setFont(f1);
	p2.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Makespan Time Comparsion Graph");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	p3 = new JPanel();
	p3.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setFont(f1);
	table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
	jsp = new JScrollPane(table);
	table.setRowHeight(30);
	dtm.addColumn("Task ID");
	dtm.addColumn("Execution Time");
	dtm.addColumn("Assigned Processor");

	jsp.getViewport().setBackground(Color.white);
	jsp.setPreferredSize(new Dimension(600,500));
	p3.add(jsp,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);
	getContentPane().add(p3,BorderLayout.SOUTH);
	
}
public int getExecutionTime(){
	int max = 5000;
	int min = 1000;
	Random r = new Random();
	int range = max - min + 1;
	return r.nextInt(range) + min;
}
public void clearTable(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
public void generateExecutionTime() {
	clearTable();
	try{
		processors = Integer.parseInt(tf1.getText().trim());
	}catch(NumberFormatException nfe) {
		JOptionPane.showMessageDialog(this,"Processor size must be integers only");
		return;
	}
	try{
		task = Integer.parseInt(tf2.getText().trim());
	}catch(NumberFormatException nfe) {
		JOptionPane.showMessageDialog(this,"Task size must be integers only");
		return;
	}
	for(int i=0;i<task;i++){
		Object row[] = {"Task "+i,getExecutionTime(),"-"};
		dtm.addRow(row);
	}
}
public static void main(String a[])throws Exception{
	 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 InputScreen screen = new InputScreen();
	 screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
	 screen.setVisible(true);
}
public void minmin() {
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
	for(int i=0;i<tasklist.size();i++){
		Task task = tasklist.get(i);
		Processor p = null;
		int min = 100000;
		for(int j=0;j<p_list.length;j++){
			if(p_list[j].remain_time <= min ) {
				min = p_list[j].remain_time;
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
	minmin = end - start;
	JOptionPane.showMessageDialog(null,"MIN MIN algorithm execution time : "+minmin); 
}
}