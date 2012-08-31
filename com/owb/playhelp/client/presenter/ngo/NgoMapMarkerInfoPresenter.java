/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEvent;
import com.owb.playhelp.client.event.ngo.NgoRemoveEvent;
import com.owb.playhelp.client.helper.ClickPoint;

public class NgoMapMarkerInfoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HTMLPanel getMainPanel();
		public HasText getNgoName();
		public HasText getNgoDescription();
		public HasText getNgoAddress();
		public HasText getNgoPhone();
		public HasText getNgoEmail();
		public Anchor getEditBut();
		public Anchor getRemoveBut();
		public HasClickHandlers getReportBut();
		public HasClickHandlers getFollowBut();
		public HasClickHandlers getFulldescBut();
	}

	private final SimpleEventBus eventBus;
	public final Display display;

	private UserProfileInfo currentUser;
	private final NgoInfo ngo;

	public NgoMapMarkerInfoPresenter(UserProfileInfo currentUser,
			SimpleEventBus eventBus, NgoInfo ngo, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
		this.ngo = ngo;
	}

	public void bind() {
	    this.display.getEditBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupAddNgoEvent(new ClickPoint(100,100),ngo));
	        }
	      });
	    this.display.getRemoveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new NgoRemoveEvent(ngo));
	        }
	      });
	    this.display.getReportBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFollowBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFulldescBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		display.getNgoName().setText(ngo.getName());
		display.getNgoAddress().setText(ngo.getAddress());
		display.getNgoDescription().setText(ngo.getDescription());
		display.getNgoPhone().setText(ngo.getPhone());
		display.getNgoEmail().setText(ngo.getEmail());
		
		if (!ngo.getMember()) {
			display.getEditBut().setVisible(false);
			display.getRemoveBut().setVisible(false);
		}
		bind();
	}

}
