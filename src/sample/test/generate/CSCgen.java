import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class CSCgen {
    public static void main(String[] args) {
		String sensor_filename = "test.sensor";
		String obstacle_filename = "test.obstacle";
		String csc_filename = "test.csc";
		BufferedReader br = null;
		String str = null;

		try {
 			PrintWriter pw_csc = new PrintWriter(csc_filename, "UTF-8");
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("template_1"));
			while ((sCurrentLine = br.readLine()) != null) {
				pw_csc.println(sCurrentLine);
			}
//------------------------------------------------------------------------------------//
			br = new BufferedReader(new FileReader(obstacle_filename));
			while ((sCurrentLine = br.readLine()) != null) {
				str = sCurrentLine.replace(" ", ";");
				pw_csc.println("\t\t\t\t<obst>"+str+"</obst>");
			}

//------------------------------------------------------------------------------------//

			br = new BufferedReader(new FileReader("template_2"));
			while ((sCurrentLine = br.readLine()) != null) {
				pw_csc.println(sCurrentLine);
			}

//------------------------------------------------------------------------------------//
			br = new BufferedReader(new FileReader(sensor_filename));
			Integer N = Integer.parseInt(br.readLine());
			Integer sensor = 1;
			System.out.println(N);
			while ((sCurrentLine = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(sCurrentLine);
				pw_csc.println("\t\t<mote>\n\t\t\t<breakpoints />\n\t\t\t\t<interface_config>\n\t\t\t\t\torg.contikios.cooja.interfaces.Position");
         		pw_csc.println("\t\t\t\t\t<x>"+st.nextToken()+"</x>");
         		pw_csc.println("\t\t\t\t\t<y>"+st.nextToken()+"</y>");
         		pw_csc.println("\t\t\t\t\t<z>"+st.nextToken()+"</z>");
				pw_csc.println("\t\t\t\t</interface_config>\n\t\t\t\t<interface_config>\n\t\t\t\t\torg.contikios.cooja.mspmote.interfaces.MspMoteID\n\t\t\t\t\t<id>"+sensor+"</id>\n\t\t\t\t</interface_config>\n\t\t\t<motetype_identifier>sky1</motetype_identifier>\n\t\t</mote>");
				sensor++;
			}

//------------------------------------------------------------------------------------//

			br = new BufferedReader(new FileReader("template_3"));
			while ((sCurrentLine = br.readLine()) != null) {
				pw_csc.println(sCurrentLine);
			}

 			pw_csc.close(); 
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
