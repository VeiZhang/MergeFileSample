package com.excellence.filetools;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.excellence.filetools.events.FileDragEventHandler;
import com.excellence.filetools.progress.Progress;
import com.excellence.filetools.utils.FilePackager;

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

	private FilePackager mFilePackager = null;
	private Alert mProgressAlert = null;

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
		showProgressDialog();
		Observable.create(new Observable.OnSubscribe<FilePackager>()
		{
			@Override
			public void call(Subscriber<? super FilePackager> subscriber)
			{
				try
				{
					if (mFilePackager != null)
					{
						mFilePackager.deleteTempFile();
					}

					mFilePackager = new FilePackager(ottFilePath.getText(), ottVersion.getText(), tvFilePath.getText(), tvVersion.getText());
					mFilePackager.packMergeFile();
					subscriber.onCompleted();
				}
				catch (Exception e)
				{
					subscriber.onError(e);
				}
			}
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Subscriber<FilePackager>()
		{
			@Override
			public void onCompleted()
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						showInfoAlert(mFilePackager.getMergeFilePath(), mFilePackager.getMergeVersion());
					}
				});
			}

			@Override
			public void onError(Throwable throwable)
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						showErrorAlert(throwable);
					}
				});
			}

			@Override
			public void onNext(FilePackager filePackager)
			{

			}
		});
	}

	private void showProgressDialog()
	{
		hideProgressDialog();

		Progress.getInstance(mPrimaryStage).show();
	}

	private void hideProgressDialog()
	{
		Progress.getInstance(mPrimaryStage).close();
	}

	private void showErrorAlert(Throwable e)
	{
		hideProgressDialog();

		Alert alert = new Alert(Alert.AlertType.ERROR, "", new ButtonType("确定", ButtonBar.ButtonData.YES));
		alert.setTitle("警告");
		alert.setHeaderText("合成文件失败！");
		alert.setContentText(e.getMessage());

		/**************详细信息**************/
		Label label = new Label("异常信息：");
		TextArea textArea = new TextArea(printException(e));
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// 设置可隐藏的窗口
		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		/**************详细信息**************/

		alert.showAndWait();
	}

	private String printException(Throwable e)
	{
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	private void showInfoAlert(File mergeFile, int mergeVersion)
	{
		hideProgressDialog();

		Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
		alert.setTitle("成功");
		alert.setHeaderText("生成文件路径：" + mergeFile);
		alert.setContentText("合成文件版本号：" + mergeVersion);

		Optional<ButtonType> optionalType = alert.showAndWait();
		if (optionalType.get() == ButtonType.OK)
		{
			try
			{
				// 打开目录
				Desktop.getDesktop().open(mergeFile.getParentFile());
			}
			catch (Exception e)
			{
				showErrorAlert(e);
			}
		}
	}

}
