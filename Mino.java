import java.awt.Color;
import java.awt.Image;

//Rotation: http://vignette1.wikia.nocookie.net/tetrisconcept/images/3/3d/SRS-pieces.png/revision/latest?cb=20060626173148
public interface Mino{
	public boolean rotateRight();
    public boolean rotateLeft();
    public boolean rotate180();
    public boolean moveUp();
    public boolean moveDown();
    public boolean moveLeft();
    public boolean moveRight();
    public boolean turnOn();
    public void turnOff();
    public void lock();
    public int[][] getOffsets();
    public Color getColor();
    public void playSound();
    public Mino getCopy();
    public void closeClip();
    public Image getImage();
}
