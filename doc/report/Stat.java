import java.io.*;
import java.util.*;

public class Stat{

  /**
  * @param args
  */
  public static void main(String[] args) throws IOException, IndexOutOfBoundsException, InterruptedException{
    if(args.length<1){
      System.out.println("Usage:");
      System.out.println("  javac Stat.java");
      System.out.println("  java Stat <g>");
      System.exit(0);
    }

    int g=1, mu=1, nodes=1, N=100, Node=1000;
    double greatest_mst;

    BufferedReader br = null;
    String logFile = args[0]+".dat";
    String currentLine;
    br = new BufferedReader(new FileReader("g"+logFile));
    PrintStream log;
    try {
      log = new PrintStream(logFile);

      final TupleType measure =
            TupleType.DefaultFactory.create(
                    Integer.class,
                    Integer.class,
                    Integer.class);

      

      HashMap<Tuple, DataSeries> map = new HashMap<Tuple, DataSeries>();

    

      while ((currentLine = br.readLine()) != null) {  
        //System.out.println(currentLine);        
        StringTokenizer st = new StringTokenizer(currentLine," ");      
        st.nextToken();

        g = Integer.parseInt(st.nextToken());
        mu = Integer.parseInt(st.nextToken());
        nodes = Integer.parseInt(st.nextToken());
        greatest_mst = Double.parseDouble(st.nextToken());
        //System.out.println("-->"+g+" "+mu+" "+nodes+" "+greatest_mst);

        final Tuple t = measure.createTuple(g, mu, nodes);
        //System.out.println(t+" "+greatest_mst);

        if (!map.containsKey(t)) {
          DataSeries gmst = new DataSeries();
          gmst.addSample(greatest_mst);
          map.put( t , gmst );  
        } else {
          map.get(t).addSample(greatest_mst);
        }
        
      }


      Iterator<Tuple> keySetIterator = map.keySet().iterator();
      while(keySetIterator.hasNext()){
        Tuple key = keySetIterator.next();
        x.add(key.getNthValue(2));
        log.println(key.getNthValue(0) + " " + key.getNthValue(1) + " " + key.getNthValue(2) + " " + map.get(key).getMean()+" "+map.get(key).getVariance()+" "+map.get(key).getStandardDeviation());
      }      


    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }


  }

}
