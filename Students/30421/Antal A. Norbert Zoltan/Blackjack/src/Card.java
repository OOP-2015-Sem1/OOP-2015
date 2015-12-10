import javax.swing.ImageIcon;

public class Card implements Cloneable{
	private ImageIcon img;
	private int value;
	
	public Card(String path, int value){
		img = new ImageIcon(path);
		this.value = value;
	}
	
	public ImageIcon getImg(){
		return img;
	}
	
	public int getValue(){
		return value;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
