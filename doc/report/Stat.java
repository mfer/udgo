import java.io.*;
import java.util.*;

/** "Component" */
interface Measure {
 
    //Prints the Measure.
    public void print();
}
 
/** "Composite" */
class CompositeMeasure implements Measure {
 
    //Collection of child Measures.
    private List<Measure> childMeasures = new ArrayList<Measure>();
 
    //Prints the Measure.
    public void print() {
        for (Measure Measure : childMeasures) {
            Measure.print();
        }
    }
 
    //Adds the Measure to the composition.
    public void add(Measure Measure) {
        childMeasures.add(Measure);
    }
 
    //Removes the Measure from the composition.
    public void remove(Measure Measure) {
        childMeasures.remove(Measure);
    }
}
 
/** "Leaf" */
class Granularity implements Measure {

  Set<Integer> value = new HashSet<Integer>();
 
  //Prints the Measure.
  public void print() {
    System.out.println("Granularity: "+value);
  }

}

/** "Leaf" */
class Density implements Measure {

  Set<Integer> value = new HashSet<Integer>();
 
  //Prints the Measure.
  public void print() {
    System.out.println("Density: "+value);
  }
}

class Width implements Measure {

  Set<Integer> value = new HashSet<Integer>();
 
  //Prints the Measure.
  public void print() {
    System.out.println("Width: "+value);
  }
}

class NumberOfNodes implements Measure {

  Set<Integer> value = new HashSet<Integer>();
 
  //Prints the Measure.
  public void print() {
    System.out.println("NumberOfNodes: "+value);
  }
}

class GreatestMSTedge implements Measure {

  Set<Double> value = new HashSet<Double>();

  //Prints the Measure.
  public void print() {
    System.out.println("GreatestMSTedge: "+value);
  }
}


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
/*
    int g=1, mu=1, nodes=1, N=100, Node=1000;
    double greatest_distance_mst;

    BufferedReader br = null;
    String logFile = args[0]+".dat";
    String currentLine;
    br = new BufferedReader(new FileReader("g"+logFile));
    PrintStream log;
    try {
      log = new PrintStream(logFile);
      DataSeries gmst[][][] = new DataSeries[N][N][Node];

      while ((currentLine = br.readLine()) != null) {  
        System.out.println(currentLine);
        

        StringTokenizer st = new StringTokenizer(currentLine," ");
      
        st.nextToken(); //to skip the gmstStamp
        g = Integer.parseInt(st.nextToken());
        mu = Integer.parseInt(st.nextToken());
        nodes = Integer.parseInt(st.nextToken());
        greatest_distance_mst = Double.parseDouble(st.nextToken());

        gmst[g][mu][nodes] = new DataSeries();
        gmst[g][mu][nodes].addSample(greatest_distance_mst);

        System.out.println("-->"+g+" "+mu+" "+nodes+" "+greatest_distance_mst);
      }

      
      for (DataSeries[][] gmst_nodes : gmst){
        System.out.println(gmst_nodes.length);
        for (DataSeries[] gmst_mu : gmst_nodes){
          System.out.println(gmst_mu.length);
          for (DataSeries gmst_g : gmst_mu){
            //System.out.println(gmst_g.);
            //System.out.println(gmst_g.getMean());            
          }
        }
      }

      log.println(nodes+" "+gmst[g][mu][nodes].getMean()+" "+gmst[g][mu][nodes].getVariance()+" "+gmst[g][mu][nodes].getStandardDeviation());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }
*/

        //Initialize four Measures
        Granularity g = new Granularity();
        Density mu = new Density();
        Width eps = new Width();
        NumberOfNodes n = new NumberOfNodes();
        GreatestMSTedge gmst = new GreatestMSTedge();

        //Initialize three composite Measures
        CompositeMeasure Granu = new CompositeMeasure();
        CompositeMeasure Densi = new CompositeMeasure();
        CompositeMeasure Width = new CompositeMeasure();
        CompositeMeasure Nodes = new CompositeMeasure();
        CompositeMeasure Great = new CompositeMeasure();
 
        g.value.add(2);
        Granu.add(g);

        mu.value.add(4);
        Densi.add(mu);

        eps.value.add(48);
        Width.add(eps);

        n.value.add((2-1)*2*2*4);
        Nodes.add(n);

        gmst.value=28.8;
        Great.add(gmst);

        //Composes the Measures
        Granu.add(Densi);
        Densi.add(Width);
        Width.add(Nodes);
        Nodes.add(Great);

        //Prints the complete Measure (four times the string "Granularity").
        Granu.print();


  }

}
