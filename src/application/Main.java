package application;
	
import control.controladorMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
			controladorMain control = new controladorMain();
			loader.setController(control);
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(true);
			double minWidth = 600.0;
	        double minHeight = 400.0;
	        primaryStage.setMinWidth(minWidth);
	        primaryStage.setMinHeight(minHeight);
			primaryStage.show();
			
			connectBBDD connectBBDD = new connectBBDD();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}