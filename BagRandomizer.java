import java.util.Random;

public class BagRandomizer implements Randomizer{
    private Mino[] pieces;
	private Random rn;
    private int pos;
    
    public BagRandomizer(Mino[] pieces){
    	this.rn=new Random();
    	this.pieces=pieces;
    	this.pos=0;
    	this.shuffle();
    }
    public Mino getNext(){
        Mino temp=this.pieces[this.pos].getCopy();
        if((this.pos=(this.pos+1)%this.pieces.length)==0){
        	this.shuffle();
        }
        return temp;
    }
    public void shuffle(){
    	int index;
    	for(int i=pieces.length-1; i>0; i--){
          index=rn.nextInt(i+1);
          Mino a=pieces[index];
          this.pieces[index]=pieces[i];
          this.pieces[i]=a;
        }
    }
}
