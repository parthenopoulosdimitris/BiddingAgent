package temp.histograms; 
import java.io.*;
import java.util.LinkedList;
import java.util.List;
/**
 * time to do something I've never done before- use python in java
 * @author acoggins
 *
 */
public class KDE {
  
  private List<Double[]> observations; 
  
  public KDE() {
    this.observations = new LinkedList<Double[]>(); 
  }
  
  public KDE(List<Double[]> observations) {
    this.observations = observations; 
  }
  
  public void addObservation(Double[] observation) { 
    this.observations.add(observation); 
  }
  
  public List<Double[]> getObservations(){ 
    return this.observations;
  }
  
  //runs the observations in python and finds a kernel density estimate. 
  public void estimate() {
    
  }
}