import java.io.*;
public class Run{

    public static String tail( File file ) {
      RandomAccessFile fileHandler = null;
      try {
          fileHandler = new RandomAccessFile( file, "r" );
          long fileLength = fileHandler.length() - 1;
          StringBuilder sb = new StringBuilder();

          for(long filePointer = fileLength; filePointer != -1; filePointer--){
              fileHandler.seek( filePointer );
              int readByte = fileHandler.readByte();

              if( readByte == 0xA ) {
                  if( filePointer == fileLength ) {
                      continue;
                  }
                  break;

              } else if( readByte == 0xD ) {
                  if( filePointer == fileLength - 1 ) {
                      continue;
                  }
                  break;
              }

              sb.append( ( char ) readByte );
          }

          String lastLine = sb.reverse().toString();
          return lastLine;
      } catch( java.io.FileNotFoundException e ) {
          e.printStackTrace();
          return null;
      } catch( java.io.IOException e ) {
          e.printStackTrace();
          return null;
      } finally {
          if (fileHandler != null )
              try {
                  fileHandler.close();
              } catch (IOException e) {
                  /* ignore */
              }
      }
  }  

  public static void run(int g, int mu, int eps) throws IOException, IndexOutOfBoundsException, InterruptedException{
    Runtime r = Runtime.getRuntime();

    System.out.println("starts");
    Process p = r.exec("./starts.sh "+g+" "+mu+" "+eps);
    p.waitFor();

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
    int g = 8, mu, eps=40;

    for (mu = 2; mu <= 12; mu=mu+2) {

      String logFile = "file.log";
      PrintStream log;
      try {
        log = new PrintStream(logFile);
        log.println("START"); log.println();
        log.println("totalTime");

        DataSeries time = new DataSeries();

        for (i = 1; i <= numInstances; i++) {
          log.println(i);

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
