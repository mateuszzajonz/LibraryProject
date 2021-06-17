package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Controller myConrollerToPass;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("BibliotekaView.fxml"));
			Parent root = loader.load();
			Controller myController = loader.getController();
			myConrollerToPass = myController;
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			myController.loadOnStart();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
