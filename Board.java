import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Board {

	private HashMap<String, Space> grid;
	
	private int startX, startY;
	private int endX, endY;
	
	private Space start, end;
	
	final static int SQUARE_SIZE = 20;
	
	private Stage window;
	private GridPane layout;
	private Scene scene;
	private Button next;
	private Label text;
	
	int boardStage;
	
	public Board(int x, int y) {
		grid = new HashMap<String, Space>();
		
		window = new Stage();
		boardStage = 0;
		
		start = null;
		end = null;
		
		window.setTitle("Pathfinder");
		
		layout = new GridPane();
		layout.setStyle("-fx-background-color: #2E2F30");
	
		//Set Spaces
		for(int row = 0; row < y; row++)
			layout.getRowConstraints().add(new RowConstraints(SQUARE_SIZE + 2));
		
		for(int column = 0; column < x; column++)
			layout.getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE + 2));

		for(int row = 0; row < y; row++)
			for(int column = 0; column < x; column++){
				Space space = new Space(SQUARE_SIZE, SQUARE_SIZE, Color.ALICEBLUE, column, row);
				GridPane.setConstraints(space, column, row, 1, 1, HPos.CENTER, VPos.CENTER);
				layout.getChildren().add(space);
				grid.put(column + " " + row, space);
			}
		
		//Create Button
		next = new Button("Next");
		next.setStyle("fx-background-color: #F0F8FF");
		GridPane.setConstraints(next, x - 4, y, 4, 4);
		layout.getChildren().add(next);
		next.setOnAction(e -> {
			boardStage++;
			switch(boardStage) {
				case 1:
					text.setText(" Choose End Location");
					break;
					
				case 2:
					text.setText(" Add Barriers");
					next.setText("Find Path");
					break;
					
				case 3:
					next.setText("Close");
					try {
						new AStarPath(start, end, this);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					break;
				
				case 4:
					window.close();
					break;
					
			}
		});
		
		//Creates Label
		text = new Label(" Choose Start Location");
		text.setFont(new Font("Verdana",25));
		text.setTextFill(Color.ALICEBLUE);
		GridPane.setConstraints(text, 0, y, x - 3, 3, HPos.LEFT, VPos.BOTTOM);
		layout.getChildren().add(text);

		scene = new Scene(layout, (SQUARE_SIZE + 2 ) * x, (SQUARE_SIZE + 2 ) * (y + 2));
		
		
		//Keeps track of already changed spaces so that draggin does not change one space more than once
		LinkedList<Space> changed = new LinkedList<Space>();
		
		//Mouse Handler
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int objectX = (int)event.getX() / (SQUARE_SIZE + 2);
				int objectY = (int)event.getY() / (SQUARE_SIZE + 2);
				
				if(boardStage == 0)
					try {
						setStart(objectX, objectY);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				else if(boardStage == 1)
					try {
						setEnd(objectX, objectY);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				else if(boardStage == 2)
					try {
						setBarrier(objectX, objectY);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				changed.add(grid.get(objectX + " " + objectY));
			}
		});

		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int objectX = (int)event.getX() / (SQUARE_SIZE + 2);
				int objectY = (int)event.getY() / (SQUARE_SIZE + 2);
				
				if(!changed.contains(grid.get(objectX + " " + objectY))) {
					if(boardStage == 0)
						try {
							setStart(objectX, objectY);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					else if(boardStage == 1)
						try {
							setEnd(objectX, objectY);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					else if(boardStage == 2)
						try {
							setBarrier(objectX, objectY);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					changed.add(grid.get(objectX + " " + objectY));
				}
			}
		});
		
		//Resents changed when mouse released
		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changed.clear();
		
			}
		});
		
		window.setScene(scene);
		window.show();
		
	}

	private void setStart(int x, int y) throws Exception {
		//Ensures only one start
		if(grid.get(startX + " " + startY).getState() == "start") {
			grid.get(startX + " " + startY).setState("blank");
			
		}
		
		if(grid.get(x + " " + y).getState() == "blank") {
			grid.get(x + " " + y).setState("start");
			
			start = grid.get(x + " " + y);
			
			startX = x;
			startY = y;
			
		}
	}
	
	private void setEnd(int x, int y) throws Exception {
		//Ensures only one start
		if(grid.get(endX + " " + endY).getState() == "end")
			grid.get(endX + " " + endY).setState("blank");
			
		
		if(grid.get(x + " " + y).getState() == "blank") {
			grid.get(x + " " + y).setState("end");
			
			end = grid.get(x + " " + y);
			
			endX = x;
			endY = y;

			
		}
	}

	private void setBarrier(int x, int y) throws Exception {
		if(grid.get(x + " " + y).getState() == "blank") {
			grid.get(x + " " + y).setState("barrier");
			
		} else if (grid.get(x + " " + y).getState() == "barrier") {
			grid.get(x + " " + y).setState("blank");

		}
	}
	
	public LinkedList<Space> getNeighbors(Space current) {
		LinkedList<Space> list = new LinkedList<Space>();
		
		//adds all neighbors to a list
		for(int xDif = -1; xDif <= 1; xDif++) {
			for(int yDif = -1; yDif <= 1; yDif++) {
				if(xDif == 0 && yDif == 0)
					continue;
				
				Space neighbor = grid.get((current.getGridX() + xDif) + " " + (current.getGridY() + yDif));
				
				if(neighbor != null) {
					list.add(neighbor);
				}
			}
		}
		return list;
	}
	
	public void setBoardText(String newText) {
		text.setText(newText);
	}
	
}