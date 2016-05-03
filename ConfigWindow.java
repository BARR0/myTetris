import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ConfigWindow extends JFrame{
	public ConfigWindow(){
		super("Tetris");
		this.setSize(new Dimension(800, 300));
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setMinimumSize(new Dimension(970, 100));
	}
	public void addPanel(JPanel p){
		this.add(p);
		this.pack();
	}
}
