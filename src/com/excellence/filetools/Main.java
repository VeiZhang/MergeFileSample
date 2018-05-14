package com.excellence.filetools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Load root layout from fxml file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("main.fxml"));
		Parent root = loader.load();

		// Show the scene containing the root layout
		primaryStage.setTitle("合并文件工具");
		primaryStage.setScene(new Scene(root, 520, 300));
		/**
		 * 固定宽高
		 */
		primaryStage.setResizable(false);
		primaryStage.show();

		// relate controller with application
		Controller controller = loader.getController();
		controller.setStage(primaryStage);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
