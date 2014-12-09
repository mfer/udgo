import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import gaal.Point3d;

class CTRgen {
    public static void main(String[] args) {
		String ctr_filename = "test.ctr";
		BufferedReader br = null;
		String testlog_filename = "test.testlog";
		String sensor_filename = "test.sensor";
		String ctr ="\n";
		Integer s1, s2;
		Double TRANSMITTING_RANGE=100000.0;

		try { 			
			String sCurrentLine; 
			br = new BufferedReader(new FileReader(sensor_filename));
			Integer N = Integer.parseInt(br.readLine());
			Point3d sensors[] = new Point3d[N];
			Integer sensor = 0;
			Integer link[][] = new Integer[N+1][N+1];
			int links = 0;

			while ((sCurrentLine = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(sCurrentLine);
				sensors[sensor] = new Point3d(Double.parseDouble(st.nextToken()),
					Double.parseDouble(st.nextToken()),
					Double.parseDouble(st.nextToken()));
				sensor++;
			}

			for (int i=0; i <= N; i++){
				for (int j=0; j <= N; j++){
					link[i][j] = 0;
				}
			}


			br = new BufferedReader(new FileReader(testlog_filename));
			while ((sCurrentLine = br.readLine()) != null) {				
				if (sCurrentLine.length() < 42/2){ //To filter lines about nodes initialization log
					StringTokenizer st = new StringTokenizer(sCurrentLine,":");
				
					st.nextToken(); //to skip the timeStamp
					s1 = Integer.parseInt(st.nextToken());
					s2 = Integer.parseInt(st.nextToken());

					if (link[s2][s1] == 0 && link[s1][s2] == 0){
						link[s1][s2]=1;
						link[s2][s1]=1;
					}
				}
			}

			//our output should there in [0-N)
			for (int i=0; i < N; i++){
				for (int j=i+1; j < N; j++){					
					if (link[i][j] == 1) {
						//cooja expected [0-N)
						if(sensors[i].distance(sensors[j])<=TRANSMITTING_RANGE){
							ctr += i+" "+j+" "+sensors[i].distance(sensors[j])+"\n";
							links++;
						}
					}
				}
			}
		

			//System.out.println(N+"\n"+links+ctr);
			PrintWriter pw_ctr = new PrintWriter(ctr_filename, "UTF-8");
			pw_ctr.println(N+"\n"+links+ctr);
 			pw_ctr.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }    
}
