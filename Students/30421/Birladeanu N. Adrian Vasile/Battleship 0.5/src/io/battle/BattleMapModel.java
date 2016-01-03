package io.battle;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;

import prototypes.ModifiedPoint;
import resources.MapModel;
import resources.Resources;

public class BattleMapModel extends MapModel {
	
	private LinkedList<ModifiedPoint> shipHits=new LinkedList<ModifiedPoint>();
	private Random randomHit=new Random();
	private BattleListener[][] battleListener=new BattleListener
			[Resources.MAP_DIMENSION][Resources.MAP_DIMENSION];
	private int score;
	private int regionsHit;
	
	public BattleMapModel(){
		super();
		score=0;
		regionsHit=0;
	}
	
	public BattleMapModel(MapModel mapModel){
		super();
		for(int i=0; i<Resources.MAP_DIMENSION; i++)
			for(int j=0; j<Resources.MAP_DIMENSION; j++){
				this.region[i][j]=mapModel.getRegion(i, j);
				this.visitedRegion[i][j]=0;
			}
		this.interactiveMap=mapModel.interactiveMap;
		this.shipPoints=21;
	}
	
	public void simulateAI(){
		Random random=new Random();
		simulateCarrier(random);
		simulateBattleship(random);
		simulateCruisers(random);
		simulateDestroyers(random);
	}
	
	public void simulateCarrier(Random random){
		while(numberOfCarriers==0){
			int positionX=random.nextInt(Resources.MAP_DIMENSION);
			int positionY=random.nextInt(Resources.MAP_DIMENSION);
			orientation=random.nextInt(2);
			checkCarrier(positionX, positionY);
			if(correctPosition==true){
				setCarrier(positionX, positionY);
				numberOfCarriers++;
				correctPosition=false;
			}
			else {
				this.revertTemporaryColours();
			}
		}
	}
	
	public void simulateBattleship(Random random){
		while(numberOfBattleships==0){
			int positionX=random.nextInt(Resources.MAP_DIMENSION);
			int positionY=random.nextInt(Resources.MAP_DIMENSION);
			orientation=random.nextInt(2);
			checkBattleship(positionX, positionY);
			if(correctPosition==true){
				setBattleship(positionX, positionY);
				numberOfBattleships++;
				correctPosition=true;
			}
			else {
				this.revertTemporaryColours();
			}
		}
	}
	
	public void simulateCruisers(Random random){
		while(numberOfCruisers<2){
			int positionX=random.nextInt(Resources.MAP_DIMENSION);
			int positionY=random.nextInt(Resources.MAP_DIMENSION);
			orientation=random.nextInt(2);
			checkCruiser(positionX, positionY);
			if(correctPosition==true){
				setCruiser(positionX, positionY);
				numberOfCruisers++;
				correctPosition=false;
			}
			else {
				this.revertTemporaryColours();
			}
		}
	}
	
	public void simulateDestroyers(Random random){
		while(numberOfDestroyers<3){
			int positionX=random.nextInt(Resources.MAP_DIMENSION);
			int positionY=random.nextInt(Resources.MAP_DIMENSION);
			orientation=random.nextInt(2);
			checkDestroyer(positionX, positionY);
			if(correctPosition==true){
				setDestroyer(positionX, positionY);
				numberOfDestroyers++;
				correctPosition=false;
			}
			else {
				this.revertTemporaryColours();
			}
		}
	}
	
	public int hit(int i, int j, int player){
		int hit;
		if(region[i][j]==1){
			hit=1;
			hitShip(i, j);
		}
		else {
			miss(i, j);
			hit=0;
		}
		visitedRegion[i][j]=1;
		if(player==Resources.HUMAN_PLAYER){
			interactiveMap.removeDeploymentListener(i, j);
		}
		regionsHit++;
		return hit;
	}
	
	public void hitShip(int i, int j){
		interactiveMap.changeButtonColor(i, j, Color.RED);
		score+=1;
	}
	
	public void miss(int i, int j){
		interactiveMap.changeButtonColor(i, j, Color.GRAY);
	}
	
	public void simulateHit(){
		int i, j;
		int hit=0;
		if(shipHits.isEmpty()){
			while(hit==0){
				i=randomHit.nextInt(Resources.MAP_DIMENSION);
				j=randomHit.nextInt(Resources.MAP_DIMENSION);
				if(visitedRegion[i][j]==0){
					hit=1;
					int success=hit(i, j, Resources.AI_PLAYER);
					if(success==1){
						ModifiedPoint point=new ModifiedPoint(i, j);
						shipHits.add(point);
					}
				}
			}
		}
		else {
			while(hit==0){
				ModifiedPoint point=shipHits.peek();
				try{
					i=(int)point.getX();
					j=(int)point.getY();
				int completedLoop=0;
				int l, k;
				MainLoop: for(l=i-1; l<=i+1; l++)
					for(k=j-1; k<=j+1; k++){
						completedLoop=0;
						if((l!=i || k!=j) && (l==i || k==j)){
							if(l>=0 && l<Resources.MAP_DIMENSION && 
									k>=0 && k<Resources.MAP_DIMENSION){
								if(visitedRegion[l][k]==0){
									hit=1;
									int success=hit(l, k, Resources.AI_PLAYER);
									if(success==1){
										ModifiedPoint newPoint=new ModifiedPoint(l, k);
										shipHits.add(newPoint);
									}
									break MainLoop;
								}
								if(l>point.getMaxL()){
									point.setMaxL(l);
								}
								if(k>point.getMaxK()){
									point.setMaxK(k);
								}
								
							}
							
						}
						if(l==i+1 && k==j+1){
							completedLoop=1;
						}
					}
					if(completedLoop==1){
						shipHits.remove(0);
					}
			}
			catch(Exception e){
				shipHits=new LinkedList<ModifiedPoint>();
				simulateHit();
				hit=1;
			}
		}
	}
	}

	public void addBattleListener(){
		int i, j;
		for(i=0; i<Resources.MAP_DIMENSION; i++){
			for(j=0; j<Resources.MAP_DIMENSION; j++){
				battleListener[i][j]=new BattleListener
						(i, j, this);
				MouseListener mouseListener=battleListener[i][j];
				interactiveMap.addSpecificListener(mouseListener, i, j);
			}
		}
	}
	
	public int getScore(){
		return this.score;
	}
	
	public int getRegionsHit(){
		return regionsHit;
	}
}
		
