//compile and run me with: 
//	gcc main.c -lm && ./a.out > plot.dat
// 	xmgrace -free plot.dat
#include "stdio.h"
#include "math.h"
void main(){
	int g,mu;
	double gd, mud;
	for (g=2;g<=12;g=g+1){
	for (mu=4;mu<=18;mu=mu+2){

		printf("  %d"  ,g);
		printf("\t%d"  ,mu);
		printf("\t%d"  ,g*(g-1)*mu*2); 
		gd = g;
		mud = mu;
		printf("\t%lf\n",100.0*(log(gd)+log(mud-1.0))/mud);
	}
	}
}
