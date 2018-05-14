package com.excellence.filetools;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.excellence.filetools.events.FileDragEventHandler;

public class Controller implements Initializable
{
	private static final String OTT_DES = "OTT files (*.zip)";
	private static final String OTT_EXTENSION = "*.zip";
	private static final String TV_DES = "TV files (*.bin)";
	private static final String TV_EXTENSION = "*.bin";

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

	/**
	 * 初始化
	 *
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		/**
		 * 也可以直接使用fxml的方式
		 */
		ottFilePath.setOnDragOver(new FileDragEventHandler(OTT_EXTENSION));
		tvFilePath.setOnDragOver(new FileDragEventHandler(TV_EXTENSION));
	}

	public void ottDragDropEvent(DragEvent event)
	{
		ottFilePath.setText(getDragDropFilePath(event));
	}

	private String getDragDropFilePath(DragEvent event)
	{
		Dragboard dragboard = event.getDragboard();
		if (dragboard.hasFiles())
		{
			return dragboard.getFiles().get(0).getPath();
		}
		return null;
	}

	public void tvDragDropEvent(DragEvent event)
	{
		tvFilePath.setText(getDragDropFilePath(event));
	}

	public void ottSelectEvent(ActionEvent event)
	{
		File file = getChooserFile(OTT_DES, OTT_EXTENSION);
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
		File file = getChooserFile(TV_DES, TV_EXTENSION);
		if (file != null)
		{
			tvFilePath.setText(file.getPath());
		}
	}

	public void mergeFileEvent(ActionEvent event)
	{

	}

}
