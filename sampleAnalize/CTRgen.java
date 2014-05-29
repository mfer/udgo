import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import gaal.Point3d;

class CTRgen {
    public static void main(String[] args) {
		String ctr_filename = args[0]+"-"+args[1]+"-"+args[2]+".ctr";
		BufferedReader br = null;
		String testlog_filename = args[0]+"-"+args[1]+"-"+args[2]+".testlog";
		String sensor_filename = args[0]+"-"+args[1]+"-"+args[2]+".sensor";
		String ctr ="\n";
		Integer s1, s2;

		try { 			
			String sCurrentLine; 
			br = new BufferedReader(new FileReader(sensor_filename));
			Integer N = Integer.parseInt(br.readLine());
			Point3d sensors[] = new Point3d[N];
			Integer sensor = 0, links = 0;

			while ((sCurrentLine = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(sCurrentLine);
				sensors[sensor] = new Point3d(Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()));
				sensor++;
			}
			//System.out.println(sensors[1].x+" "+sensors[1].y+" "+sensors[1].z+" ");
			//System.out.println(sensors[2].x+" "+sensors[2].y+" "+sensors[2].z+" ");
			//System.out.println(sensors[2].distance(sensors[1]));

			for (int i=0; i < N; i++){
				if (sensors[i] == null) System.out.println("Hey not everybody emit a radio. Or there is an isolated node.");

			}


			br = new BufferedReader(new FileReader(testlog_filename));
			while ((sCurrentLine = br.readLine()) != null) {				
				if (sCurrentLine.length() < 21){
					StringTokenizer st = new StringTokenizer(sCurrentLine,":");
				
					st.nextToken(); //to skip the timeStamp
					s1 = Integer.parseInt(st.nextToken());
					s2 = Integer.parseInt(st.nextToken());

					s1--;
					s2--;

					if (s1 < s2) {
						ctr += s1+" "+s2+" "+sensors[s1].distance(sensors[s2])+"\n";
						links++;
					}
				}
			}		


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



 
		
 
