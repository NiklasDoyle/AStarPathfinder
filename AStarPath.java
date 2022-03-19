import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class AStarPath {
	private LinkedList<Space> open;
	private LinkedList<Space> closed;
	
	AStarPath(Space start, Space end, Board board) throws Exception {
		open = new LinkedList<Space>();
		closed = new LinkedList<Space>();
		open.add(start);
		
		Timer timer = new Timer();  
		TimerTask findPath = new TimerTask() {  
		    @Override  
		    public void run() {  
		    	Space current = open.get(0);
				
				for(int i = 0; i < open.size(); i++) {
					if(current.getFCost() > open.get(i).getFCost() || (current.getFCost() ==  open.get(i).getFCost() && current.getHCost() > open.get(i).getHCost()))
						current = open.get(i);
				}
			
				open.remove(current);
				closed.add(current);
				try {
					current.setState("closed");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if(current.equals(end)) {
					//Creates Path From End
					try {
						makePath(end, start);
						timer.cancel();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				LinkedList<Space> neighbors = board.getNeighbors(current);
				
				for(Space neighbor: neighbors) {
					if(neighbor.getState() == "barrier" || closed.contains(neighbor))
						continue;
						
					double newGCost = current.getGCost() + findDistance(current, neighbor);
					
					if(newGCost < neighbor.getGCost() || !open.contains(neighbor)) {
						neighbor.setGCost(newGCost);
						neighbor.setHCost(findDistance(neighbor, end));
						neighbor.setSpaceParent(current);
						
						if(!open.contains(neighbor)) {
							open.add(neighbor);
							try {
								neighbor.setState("open");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}		
				}
		    }; 
		    
		};  
		
		timer.scheduleAtFixedRate(findPath, 0, 50);
		board.setBoardText(" Shortest Possible Path");

	}
	
	public double findDistance(Space start, Space end) {
		double distance = 0;
		
		double distanceX = Math.abs(start.getGridX() - end.getGridX());
		double distanceY = Math.abs(start.getGridY() - end.getGridY());
		
		//      Num Steps with No Diagonal - Number of steps that you save with Diagonal (uses sqrt(2) instead of 2) (sqrt(2) - 2 is negative so +)
		distance = (distanceX + distanceY) + ((Math.sqrt(2) - 2) * Math.min(distanceX, distanceY));
		
		return distance;
	}
	
	private void makePath(Space current, Space start) throws Exception {
		if(current != start) {
			//sleep(200);
			current.setState("path");
			makePath(current.getSpaceParent(), start);
		}
		start.setState("path");
	}


}
