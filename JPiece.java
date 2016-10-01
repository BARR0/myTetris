import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class JPiece extends Piece{
    public static final String FNAME="pieceJ.wav";
    public static final int blockColor=0;
    private static final int startX=4,
            startY=20;
    private static final int[][] coordinates={
            {-1, 0},
            {0, 0},
            {1, 0},
            {-1, 1}
    };
    private static final Color COLOR=Color.BLUE;
    private static final Clip CLIP=Piece.getClip(FNAME);
    private static final Image IMAGE=new ImageIcon("img/J.png").getImage();
    /*rotation:
        0 > |___

        1 >	 _
         	|
            |

        2 >  ___
            	|

        3 > 
             |
            _|
     */
    public JPiece() {
        super(JPiece.startX, JPiece.startY, JPiece.coordinates, JPiece.COLOR, JPiece.CLIP, JPiece.IMAGE);
    }
    public Color getColor(){
        return COLOR;
    }
    public JPiece getCopy(){
        return new JPiece();
    }
}
