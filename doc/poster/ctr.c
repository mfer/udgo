//compile and run me with: 
//	gcc main.c -lm && ./a.out > plot.dat
// 	xmgrace -free plot.dat
#include "stdio.h"
#include "math.h"
void main(){
	int g,mu;
	double gd, mud;
	for (g=10;g<=10;g=g+2){
	for (mu=20;mu<=20;mu=mu+2){

		printf("  %d"  ,g);
		printf("\t%d"  ,mu);
		gd = g;
		mud = mu;
		printf("\t%lf\n",100.0*(log(gd)+log(mud-1.0))/mud);
	}
	}
}
