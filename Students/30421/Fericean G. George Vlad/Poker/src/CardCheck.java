import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CardCheck extends Game{
	JFrame frmPoker;
	JLabel Card1,Card2,Card3,Card4,Card5;
	int YourBet, OppBet,WinLvl,OppWinLvl;
	String[] c = null;
	int[] s= null;
	int count=0,i,j,Ok=1;
	int cards=0;
	String[] end=null;
	public void Numbers()
	{
	c[0] = String.valueOf(Card1.getText().charAt(0));
	c[1] = String.valueOf(Card2.getText().charAt(0));
	c[2] = String.valueOf(Card3.getText().charAt(0));
	c[3] = String.valueOf(Card4.getText().charAt(0));
	c[4] = String.valueOf(Card5.getText().charAt(0));
	c[5] = String.valueOf(Card1.getText().charAt(1));
	c[6] = String.valueOf(Card2.getText().charAt(1));
	c[7] = String.valueOf(Card3.getText().charAt(1));
	c[8] = String.valueOf(Card4.getText().charAt(1));
	c[9] = String.valueOf(Card5.getText().charAt(1));
	}
	CardCheck()
	{	for(i=0;i<4;i++)
		{
		switch(c[i])
			{
			case "2": s[0]++;
			case "3": s[1]++;
			case "4": s[2]++;
			case "5": s[3]++;
			case "6": s[4]++;
			case "7": s[5]++;
			case "8": s[6]++;
			case "9": s[7]++;
			case "10": s[8]++;
			case "J": s[9]++;
			case "K": s[10]++;
			case "Q": s[11]++;
			case "A": s[12]++;
			}
		}
		for(i=0;i<13;i++)
			{
			}
	}
	public int isFlush(String [] c)	
	{	int Ok=1;
		for(i=5;i<8;i++)
			if(!(c[i]==c[i+1]))
				Ok=0;
		if(Ok==1)
		{
			WinLvl=7;
		}
		return Ok;
	}
	public int isStraight(String[] c)	
	{	int Ok=1;
		for(i=0;i<5;i++)
			if(c[i]!=c[i+1])
				Ok=0;
		if(Ok==1)
		{
			WinLvl=4;
		}
		return Ok;
	}
	public int flushStraight(String[] c)
	{
		if(isStraight(c)==1 && isFlush(c)==1)
			{
			 WinLvl=8;
			 return 1;
			}
		return 0;
	}
}
