package com;
import java.util.Comparator;
public class Task implements Comparator<Task>{
	String name;
	int execution_time;
	String processor;
	int id;
public void setID(int id){
	this.id = id;
}
public int getID(){
	return id;
}
public void setName(String name){
	this.name = name;
}
public String getName(){
	return name;
}
public void setExecutionTime(int execution_time) {
	this.execution_time = execution_time;
}
public int getExecutionTime(){
	return execution_time;
}
public void setProcessor(String processor) {
	this.processor = processor;
}
public String getProcessor() {
	return processor;
}
public int compare(Task t1, Task t2){
	double s1 = t1.getExecutionTime();
    double s2 = t2.getExecutionTime();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}