package helpers;

public class Init {
	
	public static void mapInit(int map[][]){
		int i,j;
		for(i=0; i<=1; i++){
			for(j=0; j<=35; j++){
				map[i][j] = 0;
			}
		}
	}

}
