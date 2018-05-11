package sample;

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
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		primaryStage.setTitle("合并文件工具");
		primaryStage.setScene(new Scene(root, 520, 300));
		/**
		 * 固定宽高
		 */
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
