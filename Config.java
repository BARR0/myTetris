import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Config extends JPanel implements KeyListener, ActionListener{
    private static final int WIDTH=950, HEIGHT=300;
    public static final int [] DEFAULTS = {
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_X,
            KeyEvent.VK_UP,
            KeyEvent.VK_Z,
            KeyEvent.VK_C,
            KeyEvent.VK_R,
            16,
            10,
            30,
            1,
            1
        };
    private JButton[] buttons;
    private JButton bSave;
    private JSlider DAS, ARR, DSpeed;
    private JCheckBox music, sound;
    private int pos;
    private int[] controls;
    /*left,
    right,
    up,
    down,
    rleft,
    rright,
    r180,
    hold;*/

    public Config(){
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.controls=new int[14];
        try(BufferedReader br=new BufferedReader(new FileReader("cfg/controls.cfg"));){
            String tmp;
            for(int i=0;i<Config.this.controls.length && !(tmp=br.readLine()).equals("");i++){
                System.out.println(tmp);
                this.controls[i]=Integer.parseInt(tmp);
            }
        } catch (FileNotFoundException e1) {
            this.controls=Config.DEFAULTS;
        } catch(IOException e){
            this.controls=Config.DEFAULTS;
        }
        this.bSave=new JButton("Save");
        this.buttons=new JButton[]{
                new JButton("Left"),
                new JButton("Right"),
                new JButton("Hard Drop"),
                new JButton("Soft Drop"),
                new JButton("Rotate Left"),
                new JButton("Rotate Right"),
                new JButton("Rotate 180"),
                new JButton("Hold"),
                new JButton("Retry")
        };
        for(JButton i:this.buttons){
            this.add(i);
            i.addActionListener(this);
        }

        DAS=new JSlider(JSlider.VERTICAL, 0, 30, this.controls[9]);
        DAS.setMajorTickSpacing(10);
        DAS.setMinorTickSpacing(5);
        DAS.setPaintTicks(true);
        DAS.setPaintLabels(true);
        DAS.setToolTipText(""+DAS.getValue());
        DAS.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                Config.this.DAS.setToolTipText(""+Config.this.DAS.getValue());
                Config.this.controls[9]=Config.this.DAS.getValue();
            }
        });

        ARR=new JSlider(JSlider.VERTICAL, 0, 99, this.controls[10]);
        ARR.setMajorTickSpacing(10);
        ARR.setMinorTickSpacing(5);
        ARR.setPaintTicks(true);
        ARR.setPaintLabels(true);
        ARR.setToolTipText(""+ARR.getValue());
        ARR.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                Config.this.ARR.setToolTipText(""+Config.this.ARR.getValue());
                Config.this.controls[10]=Config.this.ARR.getValue();
            }
        });

        DSpeed=new JSlider(JSlider.VERTICAL, -1, 99, this.controls[11]);
        DSpeed.setMajorTickSpacing(10);
        DSpeed.setMinorTickSpacing(5);
        DSpeed.setPaintTicks(true);
        DSpeed.setPaintLabels(true);
        DSpeed.setToolTipText(""+DSpeed.getValue());
        DSpeed.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                Config.this.DSpeed.setToolTipText(""+Config.this.DSpeed.getValue());
                Config.this.controls[11]=Config.this.DSpeed.getValue();
            }
        });

        this.add(ARR);
        this.add(DAS);
        this.add(DSpeed);

        this.music = new JCheckBox("Music");
        this.music.setEnabled(true);
        this.music.setSelected(this.controls[12] == 1);
        this.music.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                Config.this.controls[12]=Config.this.music.isSelected()?1:0;
            }
        });

        this.sound = new JCheckBox("Sound");
        this.sound.setEnabled(true);
        this.sound.setSelected(this.controls[13] == 1);
        this.sound.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                Config.this.controls[13]=Config.this.sound.isSelected()?1:0;
            }
        });

        this.add(this.sound);
        this.add(this.music);

        this.add(this.bSave);
        this.pos=-1;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.bSave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try(PrintWriter pr=new PrintWriter("cfg/controls.cfg");){
                    for(int i:Config.this.controls){
                        pr.println(i);
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i=0;i<this.buttons.length;i++){
            g.drawString(KeyEvent.getKeyText(this.controls[i]), this.buttons[i].getX(), this.buttons[i].getY()+50);
        }
        if(this.pos!=-1){
            int x=this.buttons[this.pos].getX()+this.buttons[this.pos].getWidth()/2,
                y=this.buttons[this.pos].getY()-30;
            g.fillPolygon(
                    new int[]{x, x-10, x+10},
                    new int[]{y, y-20, y-20},
                    3
                    );
        }
        g.drawString("DAS", this.DAS.getX(), this.DAS.getY()+210);
        g.drawString("ARR", this.ARR.getX(), this.ARR.getY()+210);
        g.drawString("Down Speed", this.DSpeed.getX(), this.DSpeed.getY()+210);
        g.drawString("(-1 is instant)", this.DSpeed.getX(), this.DSpeed.getY()+230);
    }
    public void keyPressed(KeyEvent arg0) {
    }
    public void keyReleased(KeyEvent e) {
        //System.out.println("hi: "+KeyEvent.getKeyText(e.getKeyCode()));
        if(this.pos!=-1){
            //System.out.println(this.buttons[this.pos].getText()+":"+e.getKeyCode());
            this.controls[this.pos]=e.getKeyCode();
        }
        this.repaint();
    }
    public void keyTyped(KeyEvent e) {
    }
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<this.buttons.length;i++){
            if(this.buttons[i]==e.getSource()){
                this.pos=i;
                //System.out.println(this.pos);
                break;
            }
        }
        this.repaint();
        this.requestFocusInWindow();
    }
}
