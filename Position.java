import java.util.Random;

class Position {

//	r.nextInt(max - min + 1) + min;  // This will return a random int between min and max
//	Min + (Math.random() * (Max - Min));//To generate a random float (Random class not needed)

    public static void main(String[] args) {
		Random r = new Random();
		Double Min = 1.0;
		Double Max = 100.0;
    	System.out.println(Min + (Math.random() * (Max - Min)));
    }
}