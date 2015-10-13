package com.rnb2.gwt1.client.images;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {

	public Images INSTANCE = GWT.create(Images.class);

	ImageResource user_group();

	ImageResource user();

	ImageResource table();
	
	ImageResource table_row_delete();
	
	ImageResource table_add();
	
	ImageResource user_edit();
	
	ImageResource script();
	
	ImageResource search();
	
	ImageResource save();
	
	ImageResource add();
	
	ImageResource remove();

	ImageResource copy();

	ImageResource table_edit();

	ImageResource vcard();

	ImageResource key();

	ImageResource excel_imports();

	ImageResource gear();

	ImageResource tree();
}
