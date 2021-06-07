package br.unibrasil.applicationClient.application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class ChatPrincipal extends Application {
	private static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("ChatPrincipal.fxml"));
			Scene scene = new Scene(root,800,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			setPrimaryStage(primaryStage);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void setPrimaryStage(Stage primaryStage) {
		ChatPrincipal.primaryStage = primaryStage;
	}
}
