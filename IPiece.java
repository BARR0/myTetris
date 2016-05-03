import java.awt.Color;
import java.awt.Image;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class IPiece extends Piece {
	public static final String FNAME="pieceI.wav";
	public static final int blockColor=0;
	private static final int startX=5,
							 startY=19;
	private static final int[][] coordinates={
			{0, 0},
			{1, 0},
			{-1, 0},
			{-2, 0}
	};
	private static final Color COLOR=Color.CYAN;
	private static final Clip CLIP=Piece.getClip(FNAME);
	private static final Image IMAGE=new ImageIcon("img/I.png").getImage();
	/*rotation:
        0 & 2> ____

        1 & 3>  |
            	|
            	|
            	|
	 */
	public IPiece() {
		super(IPiece.startX, IPiece.startY, IPiece.coordinates, IPiece.COLOR, IPiece.CLIP, IPiece.IMAGE);
	}
	public Color getColor(){
		return COLOR;
	}
	public IPiece getCopy(){
		return new IPiece();
	}
}
