package io.battle;

import java.awt.event.MouseEvent;
import prototypes.ModifiedMouseListener;
import resources.Main;
import resources.Resources;

public class BattleListener extends ModifiedMouseListener{
	
	private BattleMapModel battleMap;
	private static boolean active=true;
	
	public BattleListener(int i, int j, BattleMapModel mapModel){
		super();
		this.i=i;
		this.j=j;
		battleMap=mapModel;
	}
	
	public static void disable(){
		active=false;
	}
	
	public static void enable(){
		active=true;
	}
	
	public void mouseClicked(MouseEvent e){
		if(active==true){
			battleMap.hit(i, j, Resources.HUMAN_PLAYER);
			Main.game.battlePanel.battle();
		}
		
	}
	
	public void mouseEntered(MouseEvent e){
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		
	}
}
