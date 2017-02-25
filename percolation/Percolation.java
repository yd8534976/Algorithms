import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    
    private static final int TOP=0;
    private int BOTTOM=0;
    
    private int N=0;
    private int count=0;
    
    private boolean[] isOpen;
    
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    
    private boolean isOutside(int row,int col){
        if((row<1||row>N)||(col<1||col>N)){
            return true;
        }
        return false;
    }
    
    public Percolation(int n){
        if(n<=0){
            throw new java.lang.IllegalArgumentException();
        }
        uf=new WeightedQuickUnionUF(n*n+2);
        uf2=new WeightedQuickUnionUF(n*n+2);
        isOpen=new boolean[n*n+1];
        for(int i=1;i<=n*n;i++){
            isOpen[i]=false;
        }
        this.N=n;
        this.BOTTOM=n*n+1;
    }
    public void open(int row,int col){
        if(isOutside(row,col)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if(isOpen(row,col)){
            return;
        }
        count++;
        int current=(row-1)*N+col;
        isOpen[current]=true;
        if(row==1){
            uf.union(current,TOP);
            uf2.union(current,TOP);
        }
        if(row==N){
            uf.union(current,BOTTOM);
        }
        if(col-1>0){
            if(isOpen(row,col-1)){
                int left=current-1;
                uf.union(current,left);
                uf2.union(current,left);
            }
        }
        if(col+1<=N){
               if(isOpen(row,col+1)){
                   int right=current+1;
                   uf.union(current,right);
                   uf2.union(current,right);
               }
        }
           
        if(row-1>0){
               if(isOpen(row-1,col)){
                   int up=current-N;
                   uf.union(current,up);
                   uf2.union(current,up);
               }
        }
        if(row+1<=N){
               if(isOpen(row+1,col)){
                   int down=current+N;
                   uf.union(current,down);
                   uf2.union(current,down);
               }
        }
    }
    public boolean isOpen(int row,int col){
        if(isOutside(row,col)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        int current=(row-1)*N+col;
        if(isOpen[current]==true){
            return true;
        }
        return false;
    }
    public boolean isFull(int row,int col){
        if(isOutside(row,col)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        int current=(row-1)*N+col;
        if((isOpen[current])&&(uf2.connected(current,TOP))){
            return true;
        }
        return false;
    }
    public int numberOfOpenSites(){
        return count;
    }
    public boolean percolates(){
        if(uf.connected(TOP,BOTTOM)&&(count>0)){
            return true;
        }else{
            return false;
        }
    }
    
    public static void main(String[] args){
    }
}
