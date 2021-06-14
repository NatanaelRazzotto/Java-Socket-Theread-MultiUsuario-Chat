package br.unibrasil.applicationClient.application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class ChatPrincipal extends Application {
	private static Stage primaryStage;
	private static ChatPrincipalController controller;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatPrincipal.fxml"));
			//Pane root = FXMLLoader.load(getClass().getResource("ChatPrincipal.fxml"));
			Pane root = (Pane)loader.load();
			Scene scene = new Scene(root,800,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			controller = (ChatPrincipalController) loader.getController();		
			controller.setStageAndSetup(primaryStage);
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
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	controller.setCloseClient();
		    	Platform.exit();
		    	System.exit(0);
		    }
		  });
	}
}
