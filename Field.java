import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Field implements Runnable, KeyListener{
    public static final int WIDTH=10,
                            HEIGHT=22;
    public final int    LEFT,
                        RIGHT,
                        RLEFT,
                        RRIGHT,
                        DOWN,
                        UP,
                        R180,
                        HOLD,
                        RESET,
                        DAS,
                        ARR,
                        DSPEED,
                        LOCKDELAY=300;
    private final Mino[] PIECES;
    private Block[][] field;
    private Mino piece, hold;
    private int[] rows;
    private int keyPressed,
                lockDelay;
    private double speed,
                   dSpeed;
    private PieceQueue queue;
    private boolean leftFlag,
                    rightFlag,
                    holdFlag,
                    dead;
    private Stopwatch stopWatch;
    private GameMode mode;

    public Field(GameMode mode, Mino[] pieces){
        int[] controls=new int[12];
        try(BufferedReader br=new BufferedReader(new FileReader("cfg/controls.cfg"));){
            for(int i=0;i<controls.length;i++){
                controls[i]=Integer.parseInt(br.readLine());
            }
        } catch (FileNotFoundException e1) {
            controls=new int[]{
                    KeyEvent.VK_NUMPAD1,
                    KeyEvent.VK_NUMPAD3,
                    KeyEvent.VK_NUMPAD5,
                    KeyEvent.VK_NUMPAD2,
                    KeyEvent.VK_X,
                    KeyEvent.VK_C,
                    KeyEvent.VK_Z,
                    KeyEvent.VK_SPACE,
                    KeyEvent.VK_R,
                    6,
                    0,
                    -1
            };
        } catch(IOException e){
            controls=new int[]{
                    KeyEvent.VK_NUMPAD1,
                    KeyEvent.VK_NUMPAD3,
                    KeyEvent.VK_NUMPAD5,
                    KeyEvent.VK_NUMPAD2,
                    KeyEvent.VK_X,
                    KeyEvent.VK_C,
                    KeyEvent.VK_Z,
                    KeyEvent.VK_SPACE,
                    KeyEvent.VK_R,
                    6,
                    0,
                    -1
            };
        }
        LEFT=controls[0];
        RIGHT=controls[1];
        UP=controls[2];
        DOWN=controls[3];
        RLEFT=controls[4];
        RRIGHT=controls[5];
        R180=controls[6];
        HOLD=controls[7];
        RESET=controls[8];
        DAS=controls[9];
        ARR=controls[10];
        DSPEED=controls[11];

        this.field=new Block[HEIGHT][WIDTH];
        for(int i=0;i<this.field.length;i++){
            for(int j=0;j<this.field[i].length;j++){
                this.field[i][j]=new Block();
            }
        }
        this.rows=new int[HEIGHT];
        for(int i=0;i<this.rows.length;i++){
            this.rows[i]=0;
        }
        this.mode=mode;
        Piece.setField(field, rows);
        this.PIECES=pieces;
        this.queue=new PieceQueue(this.PIECES, this.field, this.rows);
        this.keyPressed=-1;
        this.speed=2.0;
        this.piece=this.queue.getNext();
        this.hold=null;
        this.holdFlag=this.dead=this.rightFlag=this.leftFlag=true;
        this.lockDelay=LOCKDELAY;
        this.stopWatch=new Stopwatch();
        (new Thread(this)).start();
    }
    public boolean getBlock(int i, int j){
        return field[j][i].getUsed();
    }
    public Color getBlockColor(int i, int j){
        return field[j][i].getColor();
    }
    public Image getBlockImage(int i, int j){
        return field[j][i].getImage();
    }
    public void setBlock(int i, int j, boolean used){
        if(this.getBlock(i, j)!=used){
            this.rows[j]+=used?1:-1;
        }
        this.field[j][i].setUsed(used);
    }
    public void setBlockAll(int i, int j, boolean used, Color color, Image img){
        if(this.getBlock(i, j)!=used){
            this.rows[j]+=used?1:-1;
        }
        this.field[j][i].setAll(used, color, img);
    }
    public boolean pieceLock(){
        this.piece.lock();
        this.piece=this.queue.getNext();
        this.holdFlag=true;
        for(int i=0;i<this.rows.length;i++){
            if(this.rows[i]>WIDTH || this.rows[i]<0)System.out.println(i+":"+this.rows[i]);

            if(this.rows[i]>=WIDTH){
                this.mode.updateLine();
                Block[] temp=this.field[i];
                for(int j=i;j<this.field.length-1;j++){
                    this.field[j]=this.field[j+1];
                    this.rows[j]=this.rows[j+1];
                }
                this.field[this.field.length-1]=temp;
                this.rows[this.field.length-1]=0;
                for(int j=0;j<this.field[this.field.length-1].length;j++){
                    this.field[this.field.length-1][j].setAll(false, Color.BLACK, null);;
                }
                i--;
            }
        }
        this.mode.updatePiece();
        /*
        System.out.println("\nRows:");
        for(int i=0;i<this.rows.length;i++){
            System.out.println((i+1)+":"+this.rows[i]);
        }
         */
        return this.piece.turnOn();
    }
    public void hold(){
        if(this.holdFlag){
            this.piece.turnOff();
            Mino temp=this.piece;
            if(this.hold!=null){
                this.piece=this.hold;
            }
            else{
                this.piece=this.queue.getNext();
            }
            this.hold=temp;
            this.piece.turnOn();
            this.holdFlag=false;
        }
    }
    public void reset(){
        this.dead=false;
        try {
            Thread.sleep((int)(1000.0/this.speed)+5);
        } catch (InterruptedException e){}
        for(int i=0;i<this.field.length;i++){
            for(int j=0;j<this.field[i].length;j++){
                this.field[i][j].setAll(false, Color.BLACK, null);
            }
        }
        for(int i=0;i<this.rows.length;i++){
            this.rows[i]=0;
        }
        this.queue=new PieceQueue(this.PIECES, this.field, this.rows);
        this.keyPressed=-1;
        this.speed=2;
        this.piece=this.queue.getNext();;
        this.hold=null;
        this.holdFlag=this.dead=this.rightFlag=this.leftFlag=true;
        (new Thread(this)).start();
    }
    public Mino getHold(){
        return this.hold;
    }
    public Mino[] getPieces(){
        return this.queue.visualize();
    }
    public double getSpeed(){
        return this.speed;
    }
    public void setSpeed(double speed){
        this.speed=speed;
    }
    public boolean hasWon(){
        return !dead;
    }
    public void close(){
        for(Mino i:this.PIECES){
            i.closeClip();
        }
    }
    public void run() {
        while(this.dead && !this.mode.checkWin()){
            try{
                if(!this.piece.moveDown()){
                    if(!this.stopWatch.isOn()){
                        this.stopWatch.start();
                    }
                    else if(this.stopWatch.elapsedTime()>=this.lockDelay){
                        this.dead=this.pieceLock();
                    }
                }
                else if(this.stopWatch.isOn()){
                    this.stopWatch.stop();
                }
                Thread.sleep((int)(1000.0/this.speed));
            }
            catch(InterruptedException e){
                System.err.println(e);
            }
        }
        if(this.dead){
            this.mode.win();
        }
        else{
            this.mode.lose();
        }
    }
    public void keyPressed(KeyEvent e) {
        if(this.keyPressed!=e.getKeyCode()){
            this.keyPressed=e.getKeyCode();
            if(e.getKeyCode()==RESET){
                this.mode.reset();
            }
            else if(this.mode.checkLose() || this.mode.checkWin()){

            }
            else if(e.getKeyCode()==LEFT){
                if(this.piece.moveLeft() && Field.this.stopWatch.isOn())this.stopWatch.start();
                this.leftFlag=true;
                this.rightFlag=false;
                (new Thread(new Runnable(){
                    public void run() {
                        try {
                            Thread.sleep(16*DAS);
                            boolean check;
                            while(Field.this.leftFlag){
                                if((check=Field.this.piece.moveLeft()) && Field.this.stopWatch.isOn()){
                                    Field.this.stopWatch.start();
                                }
                                Thread.sleep(check?ARR:16);
                            }
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                })).start();
            }
            else if(e.getKeyCode()==RIGHT){
                if(this.piece.moveRight() && Field.this.stopWatch.isOn())this.stopWatch.start();
                this.rightFlag=true;
                this.leftFlag=false;
                (new Thread(new Runnable(){
                    public void run() {
                        try {
                            Thread.sleep(16*DAS);
                            boolean check;
                            while(Field.this.rightFlag){
                                if((check=Field.this.piece.moveRight()) && Field.this.stopWatch.isOn()){
                                    Field.this.stopWatch.start();
                                }
                                Thread.sleep(check?ARR:16);
                            }
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                })).start();
            }
            else if(e.getKeyCode()==UP){
                while(this.piece.moveDown());
                this.dead=this.pieceLock();
            }
            else if(e.getKeyCode()==DOWN){
                this.dSpeed=this.speed;
                if(DSPEED>=0){
                    this.speed=DSPEED;
                }
                else{
                    while(this.piece.moveDown());
                }
            }
            else if(e.getKeyCode()==RLEFT){
                if(this.piece.rotateLeft() && this.stopWatch.isOn()){
                    this.stopWatch.start();
                }
            }
            else if(e.getKeyCode()==RRIGHT){
                if(this.piece.rotateRight() && this.stopWatch.isOn()){
                    this.stopWatch.start();
                }
            }
            else if(e.getKeyCode()==R180){
                if(this.piece.rotate180() && this.stopWatch.isOn()){
                    this.stopWatch.start();
                }
            }
            else if(e.getKeyCode()==HOLD){
                this.hold();
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        this.keyPressed=-1;
        if(e.getKeyCode()==RESET){

        }
        else if(this.mode.checkLose() || this.mode.checkWin()){

        }
        else if(e.getKeyCode()==LEFT){
            this.leftFlag=false;
        }
        else if(e.getKeyCode()==RIGHT){
            this.rightFlag=false;
        }
        else if(e.getKeyCode()==UP){

        }
        else if(e.getKeyCode()==DOWN){
            this.speed=this.dSpeed;
        }
        else if(e.getKeyCode()==RLEFT){

        }
        else if(e.getKeyCode()==RRIGHT){

        }
        else if(e.getKeyCode()==R180){

        }
        else if(e.getKeyCode()==HOLD){

        }
    }
    public void keyTyped(KeyEvent e){

    }
}
