package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

public class Controller
{

	@FXML
	private TextField ottFilePath;

	@FXML
	private TextField tvFilePath;

	@FXML
	private TextField ottVersion;

	@FXML
	private TextField tvVersion;

	public void ottDragDropEvent(DragEvent event)
	{
		Dragboard dragboard = event.getDragboard();
		if (dragboard.hasFiles())
		{
			dragboard.getFiles().get(0).getPath();
		}
	}
}
