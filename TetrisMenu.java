import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TetrisMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public TetrisMenu(Tetris tetris) {
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
				tetris.dispose();
				TetrisMenu.this.dispose();
			}
			public void windowClosed(WindowEvent e) {
			}
			public void windowActivated(WindowEvent e) {
			}
		});
		setBounds(800, 200, 400, 700);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
			JLabel tetrisTitle = new JLabel("");
			tetrisTitle.setBackground(new Color(128, 0, 128));
			contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
			tetrisTitle.setIcon(new ImageIcon("img/tetris.png"));
			contentPanel.add(tetrisTitle);

		
		JButton btnMarathon = new JButton(" Marathon");
		btnMarathon.setHorizontalAlignment(SwingConstants.LEFT);
		btnMarathon.setIcon(new ImageIcon("img/oMino.png"));
		btnMarathon.setFont(new Font("OCR A Extended", Font.PLAIN, 40));
		btnMarathon.setForeground(new Color(255, 248, 220));
		btnMarathon.setBackground(new Color(255, 215, 0));
		btnMarathon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tetris.setVisible(true);
				tetris.addPanel(new TetrisPanel(new Marathon()));
				TetrisMenu.this.dispose();
			}
				
		});
		
		JButton btnSprint = new JButton(" Sprint");
		btnSprint.setHorizontalAlignment(SwingConstants.LEFT);
		btnSprint.setIcon(new ImageIcon("img/tMino.png"));
		btnSprint.setFont(new Font("OCR A Extended", Font.PLAIN, 40));
		btnSprint.setForeground(new Color(221, 160, 221));
		btnSprint.setBackground(new Color(128, 0, 128));
		btnSprint.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tetris.setVisible(true);
				tetris.addPanel(new TetrisPanel(new Sprint()));
				TetrisMenu.this.dispose();
			}
				
		});
		contentPanel.add(btnSprint);
		contentPanel.add(btnMarathon);
		
		JButton btnInvisibleMarathon = new JButton("InvisibleMarathon");
		btnInvisibleMarathon.setHorizontalAlignment(SwingConstants.LEFT);
		btnInvisibleMarathon.setIcon(new ImageIcon("img/jMino.png"));
		btnInvisibleMarathon.setFont(new Font("OCR A Extended", Font.PLAIN, 28));
		btnInvisibleMarathon.setForeground(new Color(173, 216, 230));
		btnInvisibleMarathon.setBackground(new Color(0, 0, 128));
		btnInvisibleMarathon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tetris.setVisible(true);
				tetris.addPanel(new TetrisPanel(new InvisibleMarathon()));
				TetrisMenu.this.dispose();
			}
		});
		contentPanel.add(btnInvisibleMarathon);
		
		JButton btnMadMarathon = new JButton("MadMarathon");
		btnMadMarathon.setHorizontalAlignment(SwingConstants.LEFT);
		btnMadMarathon.setIcon(new ImageIcon("img/lMino.png"));
		btnMadMarathon.setFont(new Font("OCR A Extended", Font.PLAIN, 40));
		btnMadMarathon.setForeground(new Color(255, 140, 0));
		btnMadMarathon.setBackground(new Color(139, 0, 0));
		btnMadMarathon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tetris.setVisible(true);
				tetris.addPanel(new TetrisPanel(new MadMarathon()));
				TetrisMenu.this.dispose();
			}
		});
		contentPanel.add(btnMadMarathon);
		
		JButton btnConfig = new JButton(" Config.");
		btnConfig.setHorizontalAlignment(SwingConstants.LEFT);
		btnConfig.setIcon(new ImageIcon("img/sMino.png"));
		btnConfig.setFont(new Font("OCR A Extended", Font.PLAIN, 40));
		btnConfig.setForeground(new Color(152, 251, 152));
		btnConfig.setBackground(new Color(46, 139, 87));
		btnConfig.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ConfigWindow ventana = new ConfigWindow();
				ventana.addPanel(new Config());
			}
				
		});
		contentPanel.add(btnConfig);
		
		
	}
}
