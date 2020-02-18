package com;
import javax.swing.table.DefaultTableModel;
public class UpdateTask extends Thread{
	DefaultTableModel dtm;
	int row;
	String msg;
public UpdateTask(DefaultTableModel dtm,int row,String msg) {
	this.dtm = dtm;
	this.row = row;
	this.msg = msg;
	start();
}
public synchronized void update() {
	dtm.setValueAt(msg,row,2);
}
public void run(){
	update();
}
}