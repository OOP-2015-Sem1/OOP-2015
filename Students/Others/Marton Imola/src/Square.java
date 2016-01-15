
public class Square {

		private boolean clicked;
		private boolean goal;	//true if it should be clicked
		private boolean marked; //marked as square that should not be clicked 
		private boolean brick;
		private GameStateManipulator gameStateManipulator;
		
		
		public void setLife(GameStateManipulator life) {
			this.gameStateManipulator = life;
		}
		public Square(boolean clicked, boolean goal, boolean marked, boolean brick) {
			super();
			this.clicked = clicked;
			this.goal = goal;
			this.marked = marked;
			this.brick = brick;
		}
		public boolean isBrick() {
			return brick;
		}
		public boolean isClicked() {
			return clicked;
		}
		public boolean isFail() {
			return clicked&&!goal;
		}

		public boolean isGoal() {
			return goal;
		}
		public boolean isMarked() {
			return marked;
		}
		public boolean isSuccess() {
			return clicked&&goal;
		}
		public void click()
		{
			if(isBrick())
			{
				//do nothing
			} 
			else
				if(isMarked())
				{
					marked=false;
				}
				else
				if(isGoal())
				{
					clicked=true;
					goal=true;
					gameStateManipulator.userActionPerformed();
				}
				else
					if(isClicked()){
						//do nothing
					}
					else
						if(!isGoal())
						{
							clicked=true;
							goal=false;
							if(gameStateManipulator!=null)
							{
								gameStateManipulator.decrementLife((int)(Math.random()*gameStateManipulator.getCurrentLife()/2+gameStateManipulator.getCurrentLife()/2+1));
								gameStateManipulator.userActionPerformed();
							}
						}
		}
		public void mark(){
			if(isBrick()||isClicked()||isFail()||isSuccess())
			{
				//do nothing
			}
			else 
				if(isMarked())
			{
				marked=false;
			} 
			else
			{
				marked=true;
			}
		}
		
}
