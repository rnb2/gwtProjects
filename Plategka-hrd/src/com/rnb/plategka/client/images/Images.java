package com.rnb.plategka.client.images;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {

	public Images INSTANCE = GWT.create(Images.class);

	ImageResource table();

	ImageResource about();

	ImageResource table_row_delete();

	ImageResource table_add();

	ImageResource script();

	ImageResource search();

	ImageResource save();

	ImageResource add();

	ImageResource remove();

	ImageResource wallet();
}
