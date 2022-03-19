import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Space extends Rectangle{
	//Distance from end
	private double hCost;
	
	//Distance from start
	private double gCost;
	
	private Space parent;
	private String state;
	
	private int x, y;
	
	public Space(int squareSize, int squareSize2, Color color, int x, int y) {
		super(squareSize, squareSize2, color);	
		state = "blank";
		this.x = x;
		this.y = y;
	}

	public void setState(String state) throws Exception {
		if(state != "blank" 
				&& state != "start" 
				&& state != "end" 
				&& state != "barrier" 
				&& state != "closed" 
				&& state != "open" 
				&& state != "path")
			throw new Exception();
		
		this.state = state;
		
		switch (state){
			case "blank":
				this.setFill(Color.ALICEBLUE);
				break;
				
			case "start":
				this.setFill(Color.LIME);
				break;
				
			case "end":
				this.setFill(Color.RED);
				break;
				
			case "barrier":
				this.setFill(Color.DIMGRAY);
				break;
				
			case "closed":
				this.setFill(Color.CORNFLOWERBLUE);
				break;
				
			case "open":
				this.setFill(Color.CORAL);
				break;
				
			case "path":
				this.setFill(Color.GOLD);
				break;
		
		}
	}
	
	public void setSpaceParent(Space parent) {
		this.parent = parent;
	}
	
	public void setGCost(double newGCost) {
		gCost = newGCost;
	}
	
	public void setHCost(double newHCost) {
		hCost = newHCost;
	}
	
	public double getGCost() {
		return gCost;
	}
	
	public double getHCost() {
		return hCost;
	}

	public double getFCost() {
		return gCost + hCost;
	}
	
	public String getState() {
		return state;
	}
	
	public int getGridX() {
		return x;
	}

	public int getGridY() {
		return y;
	}
	
	public Space getSpaceParent() {
		return parent;
	}

}
