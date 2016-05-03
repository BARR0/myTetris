import java.awt.Image;

public interface GameMode {
	public void updatePiece();
	public void updateLine();
	public String[] getScore();
	public Field getField();
	public int compareScore(int a);
	public void reset();
	public boolean checkWin();
	public boolean checkLose();
	public void win();
	public void lose();
	public void close();
	public Image getBG();
}
