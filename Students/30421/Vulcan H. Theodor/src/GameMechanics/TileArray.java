package GameMechanics;

public class TileArray {

	public Tile[] array= new Tile[40];
	
	public TileArray(){
			
			int[] rentArray= new int[6];
			
			array[0]= new MiscTile();
			
			rentArray= createArray(2, 10, 30, 90, 160, 250);
			array[1]= new RealEstateTile(60, 50, rentArray);
			
			
			array[2]= new CommunityChestTile();
			
			rentArray= createArray(4, 20, 60, 180, 320, 450);
			array[3]= new RealEstateTile(60, 50, rentArray);	
			
			array[4]= new MiscTile();
			
			array[5]= new RailroadTile();
			
			rentArray= createArray(6, 30, 90, 270, 400, 550);
			array[6]= new RealEstateTile(100, 50, rentArray);
			
			array[7]= new ChanceTile();
			
			rentArray= createArray(6, 30, 90, 270, 400, 550);
			array[8]= new RealEstateTile(100, 50, rentArray);	
				
			rentArray= createArray(8, 40, 100, 300, 450, 600);
			array[9]= new RealEstateTile(120, 50, rentArray);	
			
			array[10]= new MiscTile();
			
			rentArray= createArray(10, 50, 150, 450, 625, 750);
			array[11]= new RealEstateTile(140, 100, rentArray);	
			
			array[12]= new UtilityTile();
			
			rentArray= createArray(10, 50, 150, 450, 625, 750);
			array[13]= new RealEstateTile(140, 100, rentArray);	
			
			rentArray= createArray(12, 60, 180, 500, 700, 900);
			array[14]= new RealEstateTile(160, 100, rentArray);	

			array[15]= new RailroadTile();
			
			rentArray= createArray(14, 70, 200, 550, 750, 950);
			array[16]= new RealEstateTile(180, 100, rentArray);	
			
			rentArray= createArray(14, 70, 200, 550, 750, 950);
			array[18]= new RealEstateTile(180, 100, rentArray);	
			
			array[17]= new CommunityChestTile();
			
			rentArray= createArray(16, 80, 220, 600, 800, 1000);
			array[19]= new RealEstateTile(200, 100, rentArray);	
			
			array[20]= new MiscTile();
			
			rentArray= createArray(18, 90, 250, 700, 875, 1050);
			array[21]= new RealEstateTile(220, 150, rentArray);	
			
			array[22]= new ChanceTile();
			
			rentArray= createArray(18, 90, 250, 700, 875, 1050);
			array[23]= new RealEstateTile(220, 150, rentArray);	
			
			rentArray= createArray(20, 100, 300, 750, 925, 1100);
			array[24]= new RealEstateTile(240, 150, rentArray);	
			
			array[25]= new RailroadTile();
			
			rentArray= createArray(22, 110, 330, 800, 975, 1150);
			array[26]= new RealEstateTile(260, 150, rentArray);	
			
			rentArray= createArray(22, 110, 330, 800, 975, 1150);
			array[27]= new RealEstateTile(260, 150, rentArray);	
			
			array[28]= new UtilityTile();
			
			rentArray= createArray(24, 120, 360, 850, 1025, 1200);
			array[29]= new RealEstateTile(280, 150, rentArray);	
			
			array[30]= new MiscTile();
			
			rentArray= createArray(26, 130, 390, 900, 1100, 1275);
			array[31]= new RealEstateTile(300, 200, rentArray);	
			
			rentArray= createArray(26, 130, 390, 900, 1100, 1275);
			array[32]= new RealEstateTile(300, 200, rentArray);	
			
			array[33]= new CommunityChestTile();
			
			rentArray= createArray(28, 150, 450, 1000, 1200, 1400);
			array[34]= new RealEstateTile(320, 200, rentArray);	
			
			array[35]= new RailroadTile();
			
			array[36]= new ChanceTile();
			
			rentArray= createArray(35, 175, 500, 1100, 1300, 1500);
			array[37]= new RealEstateTile(350, 200, rentArray);	
			
			array[38]= new MiscTile();
			
			rentArray= createArray(50, 200, 600, 1400, 1700, 2000);
			array[39]= new RealEstateTile(350, 200, rentArray);	
			
		}
	
	private int[] createArray(int a, int b, int c, int d, int e, int f){
		int[] array= {a, b, c, d, e, f};
		return array;
	}
	
}
