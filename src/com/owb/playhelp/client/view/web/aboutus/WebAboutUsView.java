package com.owb.playhelp.client.view.web.aboutus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class WebAboutUsView extends Composite implements HasText {

	private static WebAboutUsViewUiBinder uiBinder = GWT
			.create(WebAboutUsViewUiBinder.class);

	interface WebAboutUsViewUiBinder extends UiBinder<Widget, WebAboutUsView> {
	}

	public WebAboutUsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public WebAboutUsView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
