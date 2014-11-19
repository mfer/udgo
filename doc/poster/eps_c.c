//compile and run me with: 
//	gcc main.c -lm && ./a.out > plot.dat
// 	xmgrace -free plot.dat
#include "stdlib.h"
#include "stdio.h"
#include "math.h"
void main(){
	int e,mu;
	double ed, mud;

	FILE *ofp;
	char *mode = "a";
	char filename[] = "n.dat";

	for (mu=4;mu<=18;mu=mu+2){

	sprintf(filename, "%d.dat", mu);

	ofp = fopen(filename, mode);
	if (ofp == NULL) {
		fprintf(stderr, "Can't open output file out.list!\n");
		exit(1);
	}


	for (e=-30;e<=90;e=e+1){
		ed = ((float) e)/100;
		fprintf(ofp, "  %lf"  ,ed);
		fprintf(ofp, "\t%d"  ,mu);
		mud = mu;
		fprintf(ofp, "\t%lf\n",100.0*(pow((1-2*ed),2*mud)*(1-2*pow(2.71828174591064453125,mud*ed))));
	}
	
	}
}
