package com.rnb2.gwt1.client.fileload;

import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.ParserException;
import org.vectomatic.file.File;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;
/*import org.vectomatic.svg.edit.client.AppConstants;
import org.vectomatic.svg.edit.client.AppMessages;
import org.vectomatic.svg.edit.client.SvgrealApp;*/

import com.rnb2.gwt1.client.Project_gwt_1;
import com.rnb2.gwt1.client.utils.CustomWidgets;

/**
 * Class to load files into the application
 * @author laaglu
 */
public class FileLoadRequest extends LoadRequestBase {
	private File file;

	public FileLoadRequest(File file) {
		this.file = file;
		this.title = file.getName();
	}

	@Override
	public void load() {
		final FileReader reader = new FileReader();
		reader.addLoadEndHandler(new LoadEndHandler() {
			
			@Override
			public void onLoadEnd(LoadEndEvent event) {
				Project_gwt_1 app = Project_gwt_1.getApp();
				try {
					app.addWindow(OMSVGParser.parse(reader.getStringResult()), FileLoadRequest.this);
				} catch(ParserException e) {
					
					//app.info(AppConstants.INSTANCE.openLocalMenuItem(), AppMessages.INSTANCE.loadErrorMessage(file.getName(), e.getMessage()));
				}
				
			}
		});
		reader.readAsText(file);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof FileLoadRequest) {
			FileLoadRequest r = (FileLoadRequest)o;
			return file.getName().equals(r.file.getName()) && file.getSize() == r.file.getSize();
		}
		return false;
	}
	@Override
	public int hashCode() {
		return file.getName().hashCode();
	}

}
