import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;

class Position {
    public static void main(String[] args) {
		Random r = new Random();

		String sensor_filename = args[0]+"-"+args[1]+"-"+args[2]+".sensor";
		String obstacle_filename = args[0]+"-"+args[1]+"-"+args[2]+".obstacle";

        Integer g = Integer.parseInt(args[0]);
        Integer mu = Integer.parseInt(args[1]);
        Double eps = Double.parseDouble(args[2]);
        Double seg_size = 100.0;

        Integer segs = 2 * g * (g - 1 );
        Integer N = mu * segs;
        Integer streets = 2*g;
        Integer sps = N/streets; //or mu*(g-1) //Sensors Per Street divided by 2 --> half vertical half horizontal

		Double Min = 0.0;
		Double Max = (g-1)*seg_size;
		System.out.println(Max);
		Double x,y;

		Double sig = seg_size - 2*eps;

		try {
			//TODO: treat the possibility of same coordinate repetition

			PrintWriter pw_sensor = new PrintWriter(sensor_filename, "UTF-8");
			pw_sensor.println(N);
	        for (int street = 0; street < g; street++){
	        	//vertical
		        for(int sensor = 0; sensor < sps; sensor++){
		        	y = Min + (Math.random() * (Max - Min));
		        	pw_sensor.println(seg_size*street+" "+y+" 0.0");
		        }
		        //horizontal
		        for(int sensor = 0; sensor < sps; sensor++){
		        	x = Min + (Math.random() * (Max - Min));
		        	pw_sensor.println(x+" "+seg_size*street+" 0.0");
		        }
		    }
		    pw_sensor.close();


			PrintWriter pw_obstacle = new PrintWriter(obstacle_filename, "UTF-8");
			// I choose street_state and street_indian to honor my hometown BH
	        for (int street_indian = 0; street_indian < g-1; street_indian++){
		        for(int street_state = 0; street_state < g-1; street_state++){
		        	x = seg_size*street_state + eps;
		        	y = seg_size*street_indian + eps;
//		        	pw_obstacle.println(x+" "+y+" "+sig+" "+sig);
		        }
		    }
		    pw_obstacle.close();


		} catch (IOException ex) {
		  // report
		} finally {
		   //try {pw_sensor.close();} catch (Exception ex) {}
		}
    }
}

//ps: just ignore the bottom code =^)

//	r.nextInt(max - min + 1) + min;  // This will return a random int between min and max
//	Min + (Math.random() * (Max - Min));//To generate a random float (Random class not needed)
/* Binary
	byte dataToWrite[] = //...
	FileOutputStream out = new FileOutputStream("the-file-name");
	out.write(dataToWrite);
	out.close();
*/
//Double Min = 1.0;
//Double Max = 100.0;
//System.out.println(Min + (Math.random() * (Max - Min)));
