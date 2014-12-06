import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;

class Position {
    public static void main(String[] args) {
		Random r = new Random();

		String sensor_filename = "test.sensor";
		String obstacle_filename = "test.obstacle";

        Integer g = 4;
        Integer mu = 8;
        Double eps = 38.8;
        Double seg_size = 100.0;

        Integer segs = 2 * g * (g - 1 );
        Integer N = g * g;
        Integer streets = 2*g;
        Integer sps = N/streets; //or mu*(g-1) //Sensors Per Street divided by 2 --> half vertical half horizontal

		Double Min = 0.0;
		Double Max = (g-1)*seg_size;
		System.out.println(Max);
		Double x,y;

		Double sig = seg_size - 2*eps;
        
		try {
			PrintWriter pw_sensor = new PrintWriter(sensor_filename, "UTF-8");
			pw_sensor.println(N);
	        for (int street_indian = 0; street_indian < g; street_indian++){
		        for(int street_state = 0; street_state < g; street_state++){
		        	x = seg_size*street_state;
		        	y = seg_size*street_indian;
		        	pw_sensor.println(x+" "+y+" 0.0"); //intersection sensor
		        }
		    }		    
		    pw_sensor.close();


			PrintWriter pw_obstacle = new PrintWriter(obstacle_filename, "UTF-8");
			// I choose street_state and street_indian to honor my hometown BH
	        for (int street_indian = 0; street_indian < g-1; street_indian++){
		        for(int street_state = 0; street_state < g-1; street_state++){
		        	x = seg_size*street_state + eps;
		        	y = seg_size*street_indian + eps;
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