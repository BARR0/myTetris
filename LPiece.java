import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class LPiece extends Piece{
    public static final String FNAME="pieceL.wav";
    public static final int blockColor=0;
    private static final int startX=4,
            startY=20;
    private static final int[][] coordinates={
            {-1, 0},
            {0, 0},
            {1, 0},
            {1, 1}
    };
    private static final Color COLOR=Color.ORANGE;
    private static final Clip CLIP=Piece.getClip(FNAME);
    private static final Image IMAGE=new ImageIcon("img/L.png").getImage();
    /*rotation:
        0 > ___|

        1 > |
            |_

        2 >  ___
            |

        3 > _
             |
             |
     */
    public LPiece() {
        super(LPiece.startX, LPiece.startY, LPiece.coordinates, LPiece.COLOR, LPiece.CLIP, LPiece.IMAGE);
    }
    public Color getColor(){
        return COLOR;
    }
    public LPiece getCopy(){
        return new LPiece();
    }
}
