import java.util.Random;

public class Poisson {
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public static void main(String[] args){
        int[] counter = new int[100];

        for(int i=0;i<100;i++){
            counter[i]=0;
        }

        for(int i=0;i<1000;i++){
            //System.out.println(i + " " + getPoissonRandom(20));
            counter[getPoissonRandom(20)]++;
        }

        for(int i=0;i<100;i++){
            System.out.println(i + " " + counter[i]);
        }
    }
}