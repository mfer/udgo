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
        Double seg_size = 10.0;

        Integer segs = 2 * g * (g - 1 );
        Integer N = mu * segs;
        Integer streets = 2*g;
        Integer nps = N/streets; //or mu*(g-1) //Nodes per Streets

		Double Min = 0.0;
		Double Max = (g-1)*seg_size;
		Double x,y;

		Double sig = seg_size - 2*eps;
        
		try {
			PrintWriter pw_sensor = new PrintWriter(sensor_filename, "UTF-8");
			pw_sensor.println(N);
	        for (int street = 0; street < streets; street++){
	        	//vertical
		        for(int sensor = 0; sensor < nps; sensor++){
		        	y = Min + (Math.random() * (Max - Min));
		        	pw_sensor.println(seg_size*street+" "+y+" 0.0");
		        }
		        //horizontal
		        for(int sensor = 0; sensor < nps; sensor++){
		        	x = Min + (Math.random() * (Max - Min));
		        	pw_sensor.println(x+" "+seg_size*street+" 0.0");
		        }
		    }
		    pw_sensor.close();


			PrintWriter pw_obstacle = new PrintWriter(obstacle_filename, "UTF-8");
			pw_obstacle.println(N);
			// I choose street_state and street_indian to honor my hometown BH
	        for (int street_indian = 0; street_indian < g; street_indian++){
		        for(int street_state = 0; street_state < g; street_state++){
		        	x = seg_size*street_state;
		        	y = seg_size*street_indian;		        	
		        	pw_obstacle.println(x+" "+y+" "+sig+" "+sig);
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