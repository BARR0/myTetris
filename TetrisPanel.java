import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TetrisPanel extends JPanel{
	private final Image BG;
    private static final int WIDTH=1000, HEIGHT=600;
    private int gridX,
    gridY,
    blockSize;
    private double winRect;
    private Field field;
    private GameMode mode;
    private Mino nextPiece;
    
    public TetrisPanel(GameMode mode){
        super();
        this.setPreferredSize(new Dimension(TetrisPanel.WIDTH, TetrisPanel.HEIGHT));
        this.mode=mode;
        this.BG=this.mode.getBG();
        this.field=this.mode.getField();
        this.blockSize=3*this.getHeight()>2*this.getWidth()?this.getWidth()/50:this.getHeight()/30;
        this.gridX=this.getWidth()/2-this.blockSize*10;
        this.gridY=50;
        this.nextPiece=null;
        this.addKeyListener(this.field);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.winRect=1.0;
        
        (new Thread(new Runnable(){
            public void run() {
                while(true){
                    try{
                        TetrisPanel.this.repaint();
                        Thread.sleep(16);
                    }
                    catch(InterruptedException e){
                        System.out.println(e);
                    }
                }
            }
        })).start();
    }
    public void close(){
    	this.mode.close();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, this.getWidth(), this.getHeight(), this);
        this.blockSize=3*this.getHeight()>2*this.getWidth()?this.getWidth()/50:this.getHeight()/30;
        this.gridX=this.getWidth()/2-this.blockSize*10;
        //this.gridY=0;
        //this.blockSize=20;
        this.paintGrid(g);
        this.drawHold(g);
        this.drawQueue(g);
        this.drawScore(this.gridX+this.blockSize*20, this.gridY+80, g);
        if(this.mode.checkLose()){
        	this.drawWin(false, g);
        }
        else if(this.mode.checkWin()){
        	this.drawWin(true, g);
        }
        else{
    		this.winRect=1.0;
        }
    }
    public void drawWin(boolean b, Graphics g){
    	g.setColor(new Color(120, 170, 210, 200));
    	g.fillRect((int)(this.getWidth()*this.winRect), (int)(this.getHeight()*this.winRect), (int)(this.getWidth()*(1.0-2.0*this.winRect)), (int)(this.getHeight()*(1.0-2.0*this.winRect)));
    	if(this.winRect<=0.2){
	    	g.setColor(b?Color.YELLOW:Color.ORANGE);
	    	g.setFont(new Font("Calibri", Font.BOLD, this.blockSize*4));
	    	g.drawString(b?">    Win    <":">Game Over<", (int)(this.getWidth()*0.3), (int)(this.getHeight()*0.3));
	    	this.drawScore((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.4), g);
    	}
    	else{
    		this.winRect-=0.015;
    	}
    }
    public void paintGrid(Graphics g){
        for(int i=0;i<10;i++){
            for(int j=0;j<20;j++){
                g.setColor(field.getBlockColor(i, j));
                Image temp=field.getBlockImage(i, j);
                if(temp!=null){
                	g.drawImage(temp, this.gridX+this.blockSize*i, this.gridY+this.blockSize*(19-j), this.blockSize, this.blockSize, this);
                }
                else{
                	g.fillRect(this.gridX+this.blockSize*i, this.gridY+this.blockSize*(19-j), this.blockSize, this.blockSize);
                }
            }
        }
        g.setColor(Color.GRAY);
        for(int i=0;i<=10;i++){
            g.drawLine(this.gridX+this.blockSize*i, this.gridY, this.gridX+this.blockSize*i, this.gridY+this.blockSize*(Field.HEIGHT-2));
        }
        for(int i=0;i<=20;i++){
            g.drawLine(this.gridX, this.blockSize*i+this.gridY, this.gridX+this.blockSize*Field.WIDTH, this.blockSize*i+this.gridY);
        }
    }
    public void drawHold(Graphics g){
    	Mino temp;
    	g.setColor(new Color(150, 150, 150, 100));
    	g.fillRect(this.gridX-this.blockSize*8, this.gridY+this.blockSize*3, this.blockSize*6, this.blockSize*5);
    	if((temp=this.field.getHold())!=null) this.drawPiece(this.gridX-this.blockSize*5, this.gridY+this.blockSize*5, temp, g);
    }
    public void drawQueue(Graphics g){
    	Mino[] pieceQueue=this.field.getPieces();
    	g.setColor(new Color(150, 150, 150, 100));
    	g.fillRect(this.gridX+this.blockSize*12, this.gridY+this.blockSize*3, this.blockSize*6, this.blockSize*5*pieceQueue.length);
    	for(int i=0;i<pieceQueue.length;i++){
    		this.drawPiece(this.gridX+this.blockSize*15, (int)(this.gridY+this.blockSize*5+this.blockSize*4.5*i), pieceQueue[i], g);
    	}
    	if(this.nextPiece!=pieceQueue[0]){
    		this.nextPiece=pieceQueue[0];
        	this.nextPiece.playSound();
    	}
    }
    public void drawPiece(int x, int y, Mino piece, Graphics g){
    	int [][] offsets=piece.getOffsets();
    	for(int[] i:offsets){
            g.drawImage(piece.getImage(), x+this.blockSize*i[0], y-this.blockSize*i[1], this.blockSize, this.blockSize, this);
        }
    }
    public void drawScore(int x, int y, Graphics g){
    	g.setFont(new Font("OCR A Extended", Font.BOLD, 30));
    	g.setColor(Color.WHITE);
    	String[] scores=this.mode.getScore();
    	for(int i=0;i<scores.length;i++){
    		g.drawString(""+scores[i], x, y+40*i);
    	}
    }
}
