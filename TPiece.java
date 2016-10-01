import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class TPiece extends Piece {
    public static final String FNAME="pieceT.wav";
    public static final int blockColor=0;
    private static final int startX=4,
                             startY=20;
    private static final int[][] coordinates={
            {0, 0},
            {1, 0},
            {-1, 0},
            {0, 1}
    };
    private static final Color COLOR=Color.MAGENTA;
    private static final Clip CLIP=Piece.getClip(FNAME);
    private static final Image IMAGE=new ImageIcon("img/T.png").getImage();
    /*rotation:
        0 > _|_

        1 > |_
            |

        2 >  _ _
              |

        3 > _|
             |
     */
    public TPiece() {
        super(TPiece.startX, TPiece.startY, TPiece.coordinates, TPiece.COLOR, TPiece.CLIP, TPiece.IMAGE);
    }
    public Color getColor(){
        return COLOR;
    }
    public TPiece getCopy(){
        return new TPiece();
    }
}
