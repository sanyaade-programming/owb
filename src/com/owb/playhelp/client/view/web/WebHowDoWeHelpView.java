package com.owb.playhelp.client.view.web;

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

public class WebHowDoWeHelpView extends Composite implements HasText {

	private static WebHowDoWeHelpViewUiBinder uiBinder = GWT
			.create(WebHowDoWeHelpViewUiBinder.class);

	interface WebHowDoWeHelpViewUiBinder extends
			UiBinder<Widget, WebHowDoWeHelpView> {
	}

	public WebHowDoWeHelpView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public WebHowDoWeHelpView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
