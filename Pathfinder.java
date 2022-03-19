import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Pathfinder extends Application{

	public static void main(String[] args) throws Exception{
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Pathfinder");
		
		//Layout
		GridPane menuLayout = new GridPane();
		for(int row = 0; row < 30; row++)
			menuLayout.getRowConstraints().add(new RowConstraints(10));
		
		for(int column = 0; column < 20; column++)
			menuLayout.getColumnConstraints().add(new ColumnConstraints(10));
		
		menuLayout.setStyle("-fx-background-color: #2E2F30");
		
		//Label
		Label title = new Label("A* PathFinder");
		title.setFont(new Font("Verdana",24));
		title.setTextFill(Color.ALICEBLUE);
		GridPane.setConstraints(title, 1, 1, 18, 4, HPos.CENTER, VPos.CENTER);
		menuLayout.getChildren().add(title);
		
		Label header = new Label("Size of Grid:");
		header.setFont(new Font("Verdana",20));
		header.setTextFill(Color.ALICEBLUE);
		GridPane.setConstraints(header, 1, 5, 18, 4, HPos.CENTER, VPos.CENTER);
		menuLayout.getChildren().add(header);
		
		Label x = new Label("X:");
		x.setFont(new Font("Verdana",18));
		x.setTextFill(Color.ALICEBLUE);
		GridPane.setConstraints(x, 2, 9, 18, 4, HPos.LEFT, VPos.CENTER);
		menuLayout.getChildren().add(x);
		
		Label y = new Label("Y:");
		y.setFont(new Font("Verdana",18));
		y.setTextFill(Color.ALICEBLUE);
		GridPane.setConstraints(y, 2, 13, 18, 4, HPos.LEFT, VPos.CENTER);
		menuLayout.getChildren().add(y);
		
		
		//Text Fields
		TextField xInput = new TextField("25");
		GridPane.setConstraints(xInput, 5, 9, 11, 4, HPos.LEFT, VPos.CENTER);
		menuLayout.getChildren().add(xInput);
		
		TextField yInput = new TextField("25");
		GridPane.setConstraints(yInput, 5, 13, 11, 4, HPos.LEFT, VPos.CENTER);
		menuLayout.getChildren().add(yInput);
		
		
		//Buttons
		Button create = new Button("Create");
		GridPane.setConstraints(create, 1, 17, 18, 4, HPos.CENTER, VPos.CENTER);
		create.setStyle("fx-background-color: #F0F8FF");
		menuLayout.getChildren().add(create);
		create.setOnAction(e -> {
			new Board(Integer.parseInt(xInput.getText()), Integer.parseInt(yInput.getText()));
		});
		
		Button exit = new Button("Exit");
		GridPane.setConstraints(exit, 1, 25, 18, 4, HPos.CENTER, VPos.CENTER);
		exit.setStyle("fx-background-color: #F0F8FF");
		menuLayout.getChildren().add(exit);
		
		exit.setOnAction(e -> {
			primaryStage.close();
		});
		
		
		Scene menu = new Scene(menuLayout, 200, 300);
		
		primaryStage.setTitle("A* Pathfinder");
		primaryStage.setScene(menu);
		primaryStage.show();
	}
	 
}
