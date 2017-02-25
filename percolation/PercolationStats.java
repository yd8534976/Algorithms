import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private double[] x;
    private int trials;
    public PercolationStats(int n,int trials){
        this.trials=trials;
        if(n<=0||trials<=0){
            throw new java.lang.IllegalArgumentException();
        }
        x=new double[trials];
        for(int i=0;i<trials;i++){
            Percolation percolation=new Percolation(n);
            while(!percolation.percolates()){
                int row=StdRandom.uniform(1,n+1);
                int col=StdRandom.uniform(1,n+1);
                percolation.open(row,col);
            }
            x[i]=((double)percolation.numberOfOpenSites())/(n*n);
        }
    }
    public double mean(){
        return StdStats.mean(x);
    }
    public double stddev(){
        return StdStats.stddev(x);
    }
    public double confidenceLo(){
        double interval=(1.96*StdStats.stddev(x))/Math.sqrt(trials);
        return StdStats.mean(x)-interval;
    }
    public double confidenceHi(){
        double interval=(1.96*StdStats.stddev(x))/Math.sqrt(trials);
        return StdStats.mean(x)+interval;
    }
    
    public static void main(String[] args){
    }
}