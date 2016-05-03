import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;

public abstract class Piece implements Mino{
	private static final Image GHOST=new ImageIcon("img/G.png").getImage();
    protected int x,
    y,
    firstx,
    firsty,
    xORy,
    pORm,
    pORm2;
    private static int[] rows;
    private static Block[][] field;
    private final int[][] offsets;
    private Color color;
    private boolean downFlag;
    private Clip clip;
    private Image image;
    
    public Piece(int x, int y, int[][] offsets, Color color, Clip clip, Image image){
        this.firstx=this.x=x;
        this.firsty=this.y=y;
        this.xORy=0;
        this.pORm=this.pORm2=1;
        this.color=color;
        this.downFlag=true;
        this.offsets=offsets;
        this.clip=clip;
        this.image=image;
    }
    public static void setField(Block[][] field, int[] rows){
    	Piece.field=field;
    	Piece.rows=rows;
    }
    private boolean checkPos(int x, int y) {
        boolean valid=false;
        try{
            for(int i=0;i<offsets.length;i++){
                valid=valid || Piece.field[y+this.pORm2*offsets[i][(this.xORy+1)%2]]
                                          [x+this.pORm*offsets[i][this.xORy]].getUsed();
            }
            return(!valid);
        }
        catch(ArrayIndexOutOfBoundsException e){
        	return false;
        }
    }
    public void ghost(boolean state){
    	int y=this.y;
    	do{
    		y--;
    	}while(this.checkPos(this.x, y));
    	y++;
    	for(int i=0;i<offsets.length;i++){
            Piece.field[y+this.pORm2*offsets[i][(this.xORy+1)%2]]
                       [this.x+this.pORm*offsets[i][this.xORy]].setCI(state?Color.GRAY:Color.BLACK, state?Piece.GHOST:null);
        }
    }
    private void transformPiece(boolean state){
        if(state)this.ghost(state);
        for(int i=0;i<offsets.length;i++){
            Piece.field[this.y+this.pORm2*offsets[i][(this.xORy+1)%2]]
                       [this.x+this.pORm*offsets[i][this.xORy]].setAll(state, state?this.color:Color.BLACK, state?this.image:null	);
        }
        if(!state)this.ghost(state);
    }
    public boolean movePos(int x, int y){
    	this.downFlag=false;
        this.transformPiece(false);
        if(this.checkPos(x, y)){
            this.x=x;
            this.y=y;
            this.transformPiece(true);
            this.downFlag=true;
            //System.out.println("bye");
            return true;
        }
        this.transformPiece(true);
        this.downFlag=true;
        //System.out.println("bye");
        return false;
    }
    private boolean wallKicks(int d){
    	if(!this.checkPos(this.x, this.y)){
            if(this.checkPos(this.x+d, this.y)){
                this.x+=d;
            }
            else if(this.checkPos(this.x-d, this.y)){
                this.x-=d;
            }
            else if(this.checkPos(this.x, this.y-1)){
                this.y--;
            }
            else if(this.checkPos(this.x+d, this.y-1)){
                this.y--;
                this.x+=d;
            }
            else if(this.checkPos(this.x-d, this.y-1)){
                this.y--;
                this.x-=d;
            }
            else if(this.checkPos(this.x, this.y+1)){
                this.y++;
            }
            else{
            	return false;
            }
    	}
    	return true;
    }
    private void calculateRight(){
        this.xORy=(this.xORy+1)%2;
        if(this.pORm+this.pORm2!=0){
            this.pORm2*=-1;
        }
        else{
            this.pORm*=-1;
        }
    }
    private void calculateLeft(){
        this.xORy=(this.xORy+1)%2;
        if(this.pORm+this.pORm2!=0){
            this.pORm*=-1;
        }
        else{
            this.pORm2*=-1;
        }
    }
    public boolean rotateRight() {
    	while(!this.downFlag);//System.out.println("downFlag is false in rright.");
    	this.downFlag=false;
    	boolean temp;
        this.transformPiece(false);
        this.calculateRight();
        if(!(temp=this.wallKicks(1))){
        	this.calculateLeft();
        }
        this.transformPiece(true);
        this.downFlag=true;
        return temp;
    }
    public boolean rotateLeft() {
    	while(!this.downFlag);//System.out.println("downFlag is false in rleft.");
    	this.downFlag=false;
    	boolean temp;
        this.transformPiece(false);
        this.calculateLeft();
        if(!(temp=this.wallKicks(-1))){
        	this.calculateRight();
        }
        this.transformPiece(true);
        this.downFlag=true;
        return temp;
    }
    public boolean rotate180(){
    	while(!this.downFlag);//System.out.println("downFlag is false in r180.");
    	this.downFlag=false;
    	boolean temp;
        this.transformPiece(false);
        this.calculateRight();
        this.calculateRight();
        if(!(temp=this.wallKicks(1))){
            this.calculateRight();
            this.calculateRight();
        }
        this.transformPiece(true);
        this.downFlag=true;
        return temp;
    }
    public boolean moveUp(){
    	while(!this.downFlag);//System.out.println("downFlag is false in up.");
        return this.movePos(this.x, this.y+1);
    }
    public boolean moveDown(){
    	while(!this.downFlag);//System.out.println("downFlag is false in down.");
    	return this.movePos(this.x, this.y-1);
    }
    public boolean moveLeft(){
    	while(!this.downFlag);//System.out.println("downFlag is false in left.");
        return this.movePos(this.x-1, this.y);
    }
    public boolean moveRight(){
    	while(!this.downFlag);//System.out.println("downFlag is false in right.");
        return this.movePos(this.x+1, this.y);
    }
    public boolean turnOn(){
    	this.downFlag=true;
    	boolean temp;
    	if(temp=this.checkPos(this.x, this.y)){
    		this.transformPiece(true);
    	}
    	return temp;
    }
    public void turnOff(){
    	while(!this.downFlag);
    	this.downFlag=false;
    	this.transformPiece(false);
    	this.x=this.firstx;
    	this.y=this.firsty;
        this.xORy=0;
        this.pORm=this.pORm2=1;
    }
    public void lock(){
    	for(int i=0;i<offsets.length;i++){
    		Piece.rows[this.y+this.pORm2*offsets[i][(this.xORy+1)%2]]+=1;
    	}
    }
    public int[][] getOffsets(){
    	return this.offsets;
    }
    public void playSound(){
    	this.clip.setFramePosition(0);
    	this.clip.start();
    }
    public void closeClip(){
    	this.clip.close();
    }
    public Image getImage(){
    	return this.image;
    }
    protected static Clip getClip(String p){
		Clip clip=null;
    	try {
            AudioInputStream stream;
            stream = AudioSystem.getAudioInputStream(new File("se/"+p));
            clip = (Clip) AudioSystem.getLine(
            	new DataLine.Info(Clip.class, stream.getFormat())
            );
            clip.open(stream);
        }
        catch (Exception e){
        	System.err.println(e);
        }
    	return clip;
    }
}