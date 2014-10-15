import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Simulation {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numRuns = Integer.parseInt(args[0]);
		String logFile = "log_file.txt";
		PrintStream log;
		try {
			log = new PrintStream(logFile);		
			log.println("START"); log.println();

				DataSeries ds = new DataSeries();
				int r;
				for (r = 0; r < numRuns; r++)
				{
					ds.addSample(r);
				}				

			log.println("END");
			log.println("MEAN, VAR, STD: "+ds.getMean()+", "+
				ds.getVariance()+", "+ds.getStandardDeviation());


			float mean = (0+r-1)/2;
			float d = 1;
			float sd = d*(float)Math.sqrt((numRuns-1)*(numRuns+1)/12);;
			float var = 0;
			log.println();
			log.println("Expected values from http://en.wikipedia.org/wiki/Arithmetic_progression");
			log.println("MEAN, VAR, STD: "+mean+", "+var+", "+sd);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
