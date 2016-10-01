import java.awt.Color;
import java.awt.Image;

public class Block {
    private Color color;
    private boolean used;
    private Image image;

    public Block(){
        this.color=Color.BLACK;
        this.used=false;
        this.image=null;
    }
    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public boolean getUsed() {
        return this.used;
    }
    public Image getImage(){
        return this.image;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
    public void setBoth(boolean state, Color color){
        this.used=state;
        this.color=color;
    }
    public void setCI(Color color, Image image){
        this.color=color;
        this.image=image;
    }
    public void setAll(boolean state, Color color, Image image){
        this.used=state;
        this.color=color;
        this.image=image;
    }
}
