package com;
import java.util.Comparator;
public class PCA implements Comparator<PCA>{
	Processor p;
	int etc;
public void setProcessor(Processor p) {
	this.p = p;
}
public Processor getProcessor(){
	return p;
}
public void setETC(int etc){
	this.etc = etc;
}
public int getETC(){
	return etc;
}
public int compare(PCA p1, PCA p2){
	double s1 = p1.getETC();
    double s2 = p2.getETC();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}