import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class OPiece extends Piece {
	public static final String FNAME="pieceO.wav";
	public static final int blockColor=0;
	private static final int startX=4,
							 startY=20;
	private static final int[][] coordinates={ //solo 1
			{0, 0},
			{1, 0},
			{0, 1},
			{1, 1}
	};
	private static final Color COLOR=Color.YELLOW;
	private static final Clip CLIP=Piece.getClip(FNAME);
	private static final Image IMAGE=new ImageIcon("img/O.png").getImage();
	/*rotation:
        0,1,2 & 3 >  __
        			|__|
	 */
	public OPiece() {
		super(OPiece.startX, OPiece.startY, OPiece.coordinates, OPiece.COLOR, OPiece.CLIP, OPiece.IMAGE);
	}
	public boolean rotateRight(){
		return true;
	}
    public boolean rotateLeft(){
    	return true;
    }
    public boolean rotate180(){
    	return true;
    }
	public Color getColor(){
		return COLOR;
	}
	public OPiece getCopy(){
		return new OPiece();
	}
}
