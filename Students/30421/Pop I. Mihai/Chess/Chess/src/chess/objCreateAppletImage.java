package chess;
import java.awt.*;
import java.io.*;
import javax.swing.ImageIcon;

public class objCreateAppletImage
{
	
	private String strErrorMsg = "";
	
	public Image getImage (Object parentClass, String path, int fileSize)
	{
		
		byte buff[] = createImageArray(parentClass, path, fileSize);
		return Toolkit.getDefaultToolkit().createImage(buff);
		
	}
	
	public ImageIcon getImageIcon (Object parentClass, String path, String description, int fileSize)
	{
		
		byte buff[] = createImageArray(parentClass, path, fileSize);
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buff), description);
		
	}
	
	public void objCreateAppletImage()
	{
	}
	
	public String getErrorMsg ()
	{
		return strErrorMsg;
	}
	
	private byte[] createImageArray (Object parentClass, String path, int fileSize)
	{
		
		int count = 0;

		BufferedInputStream imgStream = new BufferedInputStream(parentClass.getClass().getResourceAsStream(path));
		
		if (imgStream != null) //exista
		{
			
			byte buff[] = new byte[fileSize]; //se creeaza sirul 
			
			try
			{				
				count = imgStream.read(buff);
			}
			catch (IOException e)
			{
				strErrorMsg = "ATENTIE! A aparut o eroare la citirea din fisier: " + path;
			}
			
			try
			{
				imgStream.close(); //inchidere
			}
			catch (IOException e)
			{
				strErrorMsg = "ATENTIE! A aparut o eroare la inchidere " + path;
			}
			
			if (count <= 0)
			{
				
				strErrorMsg = "ATENTIE! Fisierul este gol: " + path;
				return null;
				
			}
			
			return buff;
			
		}
		else
		{
			
			strErrorMsg = "Atentie! Nu se gaseste fisierul: " + path;
			return null;
			
		}
		
	}
	
}	