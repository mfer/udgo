import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;

class Position {
    public static void main(String[] args) {
		Random r = new Random();

		String sensor_filename = "test.sensor";
		String obstacle_filename = "test.obstacle";

        Integer g = 2;
        Integer mu = 19;
        Double eps = 3.0;
        Double seg_size = 100.0;

        Integer gCol = g;
        Integer gRow = g;

        Integer segs = gRow * (gCol - 1 ) + gCol * (gRow - 1 );
        Integer N = gCol * gRow  + segs * mu;

        Integer streets = gCol+gRow;
        Integer sps = N/streets;

		Double Min = 0.0;
		Double Max = (g-1)*seg_size;
		System.out.println(Max);
		Double x,y;

		Double sig = seg_size - 2*eps;
        
		try {
			PrintWriter pw_sensor = new PrintWriter(sensor_filename, "UTF-8");
			pw_sensor.println(N);


	        for (int street_indian = 0; street_indian < gRow; street_indian++){
	        	//just at intersection sensors
		        for(int street_state = 0; street_state < gCol; street_state++){
		        	x = seg_size*street_state;
		        	y = seg_size*street_indian;
		        	pw_sensor.println(x+" "+y+" 0.0");

		        	if (street_indian < gRow-1 && street_state < gCol-1){

		        		for (int i = 1; i <= mu; i++){
				        	//horizontal middle block
				        	x = seg_size*street_state + i*seg_size/(mu+1);
				        	y = seg_size*street_indian;
				        	pw_sensor.println(x+" "+y+" 0.0");

							//vertical middle block
							x = seg_size*street_state;
				        	y = seg_size*street_indian + i*seg_size/(mu+1);
				        	pw_sensor.println(x+" "+y+" 0.0");
			        	}
		        	}
		        	if (street_indian == gRow-1 && street_state < gCol-1){
		        		for (int i = 1; i <= mu; i++){
				        	//horizontal middle block
				        	x = seg_size*street_state + i*seg_size/(mu+1);
				        	y = seg_size*street_indian;
				        	pw_sensor.println(x+" "+y+" 0.0");
			        	}
		        	}
		        	if (street_indian < gRow-1 && street_state == gCol-1){
		        		for (int i = 1; i <= mu; i++){
							//vertical middle block
							x = seg_size*street_state;
				        	y = seg_size*street_indian + i*seg_size/(mu+1);
				        	pw_sensor.println(x+" "+y+" 0.0");
			        	}
		        	}

		        }

		    }		    
		    pw_sensor.close();


			PrintWriter pw_obstacle = new PrintWriter(obstacle_filename, "UTF-8");
			// I choose street_state and street_indian to honor my hometown BH
	        for (int street_indian = 0; street_indian < gRow-1; street_indian++){
		        for(int street_state = 0; street_state < gCol-1; street_state++){
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
