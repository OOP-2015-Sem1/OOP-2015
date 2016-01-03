
public class SquareCreator {

	public static Square createBrick(){
		Square x = new Square(false,false,false,true);
		return x;
	}	
	public static Square createGoal(){
		Square x = new Square(false,true,false,false);
		return x;
	}	
	public static Square createEmpty(){
		Square x = new Square(false,false,false,false);
		return x;
	}
}
