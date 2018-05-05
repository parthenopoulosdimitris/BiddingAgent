package temp.predictions.library;

import java.io.IOException;
import java.util.Set;

import brown.tradeable.ITradeable;
import temp.histograms.KDE;
import temp.predictions.IDistributionPrediction;
import temp.price.JointDistKDE;
import temp.representation.APriceRep;
import temp.representation.KDERep;

public class SimpleKDEPrediction implements IDistributionPrediction {
  
private KDE kde; 
private Set<ITradeable> tradeables; 

public SimpleKDEPrediction(Set<ITradeable> tradeables, KDE kernelDensity) { 
  this.tradeables = tradeables; 
  this.kde = kernelDensity; 
}

@Override
public KDERep getPrediction(Set<ITradeable> goods) {
  // TODO Auto-generated method stub
  return new KDERep(new JointDistKDE(kde));
}

@Override
public APriceRep getMeanPrediction(Set<ITradeable> goods) {
  // TODO Auto-generated method stub
  String[] args = new String[2];
  args[0] = goods.toString();
  args[1] = "mean";
  try {
    Process p = Runtime.getRuntime().exec("python KDE.py", args);
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  return null;
}

@Override
public APriceRep getRandomPrediction(Set<ITradeable> goods) {
  // TODO Auto-generated method stub
  String[] args = new String[2];
  args[0] = goods.toString();
  args[1] = "random";
  try {
    Process p = Runtime.getRuntime().exec("python KDE.py", args);
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  return null;
}

public Set<ITradeable> getGoods() {
  return null; 
}

}