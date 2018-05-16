package com.excellence.filetools.progress;/**
											* <pre>
											*     author : VeiZhang
											*     blog   : http://tiimor.cn
											*     time   : 2018/5/16
											*     desc   :
											* </pre>
											*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Progress extends AnchorPane
{
	private static Progress mInstance = new Progress();

	private Stage mStage = null;

	public static Progress getInstance(Stage parent)
	{
		// 窗口父子关系
		mInstance.mStage.initOwner(parent);
		return mInstance;
	}

	public Progress()
	{
		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("progress.fxml"));
			mStage = new Stage();
			mStage.initStyle(StageStyle.UNDECORATED);
			// 去掉标题栏
			mStage.initStyle(StageStyle.TRANSPARENT);
			// 应用模态，不能操作父窗口
			mStage.initModality(Modality.APPLICATION_MODAL);
			mStage.setTitle("等待");
			mStage.setScene(new Scene(root, 300, 275));
			mStage.setResizable(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void show()
	{
		if (mStage != null)
		{
			mStage.show();
		}
	}

	public void close()
	{
		if (mStage != null)
		{
			mStage.close();
		}
	}

}
