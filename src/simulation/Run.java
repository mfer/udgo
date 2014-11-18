import java.io.*;
public class Run{

  public static void run(int g, int mu, int eps) throws IOException, IndexOutOfBoundsException, InterruptedException{
    Runtime r = Runtime.getRuntime();
    Process p = r.exec("./starts.sh "+g+" "+mu+" "+eps);
    p.waitFor();
    BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line = "";

    while ((line = b.readLine()) != null) {
      System.out.println(line);
    }
    b.close();
  }  

  /**
  * @param args
  */
  public static void main(String[] args) throws IOException, IndexOutOfBoundsException, InterruptedException{
    if(args.length<1){
      System.out.println("Usage:");
      System.out.println("  javac Run.java");
      System.out.println("  java Run <number_of_instances>");
      System.exit(0);
    }
    int numInstances = Integer.parseInt(args[0]), i;
    int g = 2, mu, eps=40;

    run(1,1,1);
    for (mu = 2; mu <= 12; mu++) {

      String logFile = "file.log";
      PrintStream log;
      try {
        log = new PrintStream(logFile);
        log.println("START"); log.println();
        log.println("totalTime");

        DataSeries time = new DataSeries();
        DataSeries ds = new DataSeries();

        for (i = 1; i <= numInstances; i++) {

          long startTime = System.currentTimeMillis();
          run(g,mu,eps);
          long endTime   = System.currentTimeMillis();
          long totalTime = endTime - startTime;

          time.addSample(totalTime);
        }

          log.println("END");
          log.println("TIME MEAN, VAR, STD: "+time.getMean()+", "+
            time.getVariance()+", "+time.getStandardDeviation());

      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(1);
      }

    }
  }

}
