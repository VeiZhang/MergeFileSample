package com.excellence.filetools.events;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/5/14
 *     desc   :
 * </pre>
 */

public class FileDragEventHandler implements EventHandler<DragEvent>
{
	private String mExtension = null;

	public FileDragEventHandler(String extension)
	{
		mExtension = extension.replace("*", "");
	}

	@Override
	public void handle(DragEvent event)
	{
		Dragboard dragboard = event.getDragboard();
		if (dragboard.hasFiles())
		{
			if (dragboard.getFiles().get(0).getPath().endsWith(mExtension))
			{
				/**
				 * 显示文件拖拽的状态，否则图标是禁止的
				 */
				event.acceptTransferModes(TransferMode.ANY);
			}
		}
	}
}
