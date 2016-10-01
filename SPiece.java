import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class SPiece extends Piece{
    public static final String FNAME="pieceS.wav";
    public static final int blockColor=0;
    private static final int startX=4,
                             startY=19;
    private static final int[][] coordinates={ //solo 2
            {0, 0},
            {-1, 0},
            {0, 1},
            {1, 1}
    };
    private static final Color COLOR=Color.GREEN;
    private static final Clip CLIP=Piece.getClip(FNAME);
    private static final Image IMAGE=new ImageIcon("img/S.png").getImage();
    /*rotation:
        0 & 2 >
               _
             _|

        1 & 3 >
                 |_
                   |
     */
    public SPiece() {
        super(SPiece.startX, SPiece.startY, SPiece.coordinates, SPiece.COLOR, SPiece.CLIP, SPiece.IMAGE);
    }
    public Color getColor(){
        return COLOR;
    }
    public SPiece getCopy(){
        return new SPiece();
    }
}
