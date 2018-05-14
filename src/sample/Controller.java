package sample;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller
{
	private Stage mPrimaryStage;

	@FXML
	private TextField ottFilePath;

	@FXML
	private TextField tvFilePath;

	@FXML
	private TextField ottVersion;

	@FXML
	private TextField tvVersion;

	public void setStage(Stage primaryStage)
	{
		mPrimaryStage = primaryStage;
	}

	public void ottDragDropEvent(DragEvent event)
	{
		Dragboard dragboard = event.getDragboard();
		if (dragboard.hasFiles())
		{
			dragboard.getFiles().get(0).getPath();
		}
	}

	public void ottSelectEvent(ActionEvent event)
	{
		File file = getChooserFile("OTT files (*.zip)", "*.zip");
		if (file != null)
		{
			ottFilePath.setText(file.getPath());
		}
	}

	private File getChooserFile(final String description, final String... extensions)
	{
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(description, extensions);
		fileChooser.getExtensionFilters().add(extensionFilter);
		return fileChooser.showOpenDialog(mPrimaryStage);
	}

	public void tvSelectEvent(ActionEvent event)
	{
		File file = getChooserFile("TV files (*.bin)", "*.bin");
		if (file != null)
		{
			tvFilePath.setText(file.getPath());
		}
	}

	public void mergeFileEvent(ActionEvent event)
	{

	}

}
