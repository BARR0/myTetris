import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class ZPiece extends Piece{
    public static final String FNAME="pieceZ.wav";
    public static final int blockColor=0;
    private static final int startX=4,
                             startY=20;
    private static final int[][] coordinates={ //solo 2
            {0, 0},
            {1, 0},
            {-1, 1},
            {0, 1}
    };
    private static final Color COLOR=Color.RED;
    private static final Clip CLIP=Piece.getClip(FNAME);
    private static final Image IMAGE=new ImageIcon("img/Z.png").getImage();
    /*rotation:
        0 & 2 >
	        	_
	         	 |_

        1 & 3 >
	         	 _|
	            | 
     */
    public ZPiece() {
        super(ZPiece.startX, ZPiece.startY, ZPiece.coordinates, ZPiece.COLOR, ZPiece.CLIP, ZPiece.IMAGE);
    }
    public Color getColor(){
        return COLOR;
    }
    public ZPiece getCopy(){
        return new ZPiece();
    }
}
