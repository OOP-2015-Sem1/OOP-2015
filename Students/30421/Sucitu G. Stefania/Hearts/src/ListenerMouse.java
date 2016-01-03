import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class ListenerMouse extends MouseAdapter {
	private static boolean PlayerTurn = true;
	static int pickedPosition;
	static int CardsPlayedIndex;
	public ListenerMouse(int i){
		ListenerMouse.pickedPosition = i;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		boolean allowed = false;
		Card PickedCard=null;
		if (PlayerTurn==true){
		while (allowed == false) {
			PickedCard = GameRunner.Players[3].hand[pickedPosition];
		if (PickedCard.Suit == GameRunner.CardsPlayed[0].Suit){
			allowed = true;
		} else {
			if (GameRunner.CheckForSuit(3) == false) {
				allowed = true;
			}}
			if (allowed==false){			   
				JOptionPane.showMessageDialog(null, "Not Allowed! Pick another card!");
		}}
		if (allowed == true) {
			GameRunner.CardsPlayed[CardsPlayedIndex] = PickedCard;
			CardsPlayedIndex++;
			PlayerTurn = false;
			RemoveCard(3);
		}}
	}
	
	private static Card[] RemoveCard(int nrPlayer)
	{
		for (int i = pickedPosition; i < ((GameRunner.Players[nrPlayer].handlength)-1); i++) {
			GameRunner.Players[nrPlayer].hand[i] = GameRunner.Players[nrPlayer].hand[i + 1];
		}
		(GameRunner.Players[nrPlayer].handlength)--;
		return GameRunner.Players[nrPlayer].hand;
	}
}
