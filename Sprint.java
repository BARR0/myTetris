import java.awt.Image;
import java.text.DecimalFormat;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Sprint implements GameMode{
	private final Image BG=new ImageIcon("img/BG2.png").getImage();
	private final Clip CLIP, WIN, LOSE;
	private int lines, pieces;
	private Stopwatch sw;
	private Field field;
	private double time;
	
	
	public Sprint(){
		this.lines=0;
		this.sw=new Stopwatch();
		this.time=-1.0;
		this.pieces=0;

        this.CLIP=Piece.getClip("theme2.wav");
        this.WIN=Piece.getClip("win.wav");
        this.LOSE=Piece.getClip("lose.wav");
        this.CLIP.loop(Clip.LOOP_CONTINUOUSLY);
        
        this.field=new Field(this, new Mino[]{
			new IPiece(),
			new JPiece(),
			new LPiece(),
			new OPiece(),
			new SPiece(),
			new TPiece(),
			new ZPiece()
		});
		this.field.setSpeed(2.0);
		this.sw.start();
	}
	public void updatePiece() {
		this.pieces++;
	}
	public void updateLine() {
		this.lines++;
	}
	public boolean checkWin(){
		return this.lines>=40;
	}
	public boolean checkLose(){
		return this.time!=-1 && this.lines<40;
	}
	public String[] getScore() {
		return new String[]{
			"Time: "+(this.time<0?this.sw.elapsedTime()/1000.0:this.time/1000.0),
			"Lines: "+this.lines,
			"PPS: "+(new DecimalFormat("#.####").format(this.time<0?this.pieces/(this.sw.elapsedTime()/1000.0):this.pieces/(this.time/1000.0))),
			"Pieces: "+this.pieces
		};
	}
	public Field getField() {
		return this.field;
	}
	public int compareScore(int a) {
		return a-(int)(this.time<0?this.sw.elapsedTime()/1000.0:this.time);
	}
	public void reset() {
		this.field.reset();
		this.lines=0;
		this.sw.start();
		this.time=-1.0;
		this.pieces=0;
		this.CLIP.setFramePosition(0);
		this.CLIP.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void win(){
		this.time=this.sw.elapsedTime();
		this.sw.stop();
		this.CLIP.stop();
		this.WIN.setFramePosition(0);
		this.WIN.start();
	}
	public void lose(){
		this.time=this.sw.elapsedTime();
		this.sw.stop();
		this.CLIP.stop();
		this.LOSE.setFramePosition(0);
		this.LOSE.start();
	}
	public void close(){
		this.field.close();
		this.CLIP.close();
		this.WIN.close();
		this.LOSE.close();
	}
	public Image getBG(){
		return BG;
	}
}
