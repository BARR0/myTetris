import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Tetris extends JFrame{
	private TetrisPanel panel;
	
	public Tetris(){
		super("Tetris");
		this.setSize(new Dimension(600, 400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(100, 100);
		this.setVisible(false);
		TetrisMenu menu = new TetrisMenu(this);
		menu.setVisible(true);
		this.pack();
		//this.setResizable(false);
		this.setMinimumSize(new Dimension(900, 600));

		this.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}
			public void windowIconified(WindowEvent e) {
			}
			public void windowDeiconified(WindowEvent e) {
			}
			public void windowDeactivated(WindowEvent e) {
			}
			public void windowClosing(WindowEvent e) {
				menu.dispose();
				if(Tetris.this.panel!=null){
					Tetris.this.panel.close();
				}
			}
			public void windowClosed(WindowEvent e) {
			}
			public void windowActivated(WindowEvent e) {
			}
		});
	}
	public void addPanel(TetrisPanel p){
		this.panel=p;
		this.add(p);
		this.pack();
	}
	public static void main(String[] args) {
		new Tetris();
	}
}
