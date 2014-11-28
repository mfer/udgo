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
		Integer s1, s2, zeroFlag=0;

		try { 			
			String sCurrentLine; 
			br = new BufferedReader(new FileReader(sensor_filename));
			Integer N = Integer.parseInt(br.readLine());
			Point3d sensors[] = new Point3d[N];
			Integer sensor = 0;
			Integer link[][] = new Integer[N+1][N+1];
			int links = 0, Nm1=N-1;

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

					if(s1==0 || s2==0) zeroFlag=1;
					if (link[s2][s1] == 0 && link[s1][s2] == 0){
						link[s1][s2]=1;
						link[s2][s1]=1;
					}
				}
			}

			//our output should there in [0-N)
			for (int i=0; i <= N; i++){
				for (int j=i+1; j <= N; j++){					
					if (link[i][j] == 1) {
					if (zeroFlag == 0) {
						//cooja expected (0-N]
						s1=i-1; s2=j-1;
						if(sensors[s1].distance(sensors[s2])<10000.0){
							ctr += s1+" "+s2+" "+sensors[s1].distance(sensors[s2])+"\n";
							links++;
						}
					}else{
						//there are cases that cooja give us [0-N) instead of (0-N]
						if (i<N && j<N) {
							if(sensors[i].distance(sensors[j])<10000.0){
								ctr += i+" "+j+" "+sensors[i].distance(sensors[j])+"\n";
								links++;
							}
						}
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
