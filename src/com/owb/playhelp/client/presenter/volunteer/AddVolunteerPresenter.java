/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.volunteer;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.volunteer.VolunteerUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.volunteer.VolunteerServiceAsync;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

public class AddVolunteerPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		HasValue<String> getNameField();
		HasValue<String> getDescField();
		HasValue<String> getAddressField();
		HasValue<String> getPhoneField();
		HasValue<String> getEmailField();
		HasValue<String> getWebField();
		ArrayList<CheckBox> getCheckBoxList();
	} 
	  
	private final SimpleEventBus eventBus;
	private final Display display;
	private VolunteerInfo volunteer;

	private final VolunteerServiceAsync volunteerService;

	public AddVolunteerPresenter(VolunteerServiceAsync volunteerService,
			SimpleEventBus eventBus, Display display) {
		//this.currentUser = currentUser;
		this.volunteerService = volunteerService;
		this.eventBus = eventBus;
		//this.geocoder = geocoder;
		this.display = display;
		this.volunteer = null;
	}
	public AddVolunteerPresenter(VolunteerInfo volunteer, VolunteerServiceAsync volunteerService,
			SimpleEventBus eventBus, Display display) {
		this(volunteerService, eventBus, display);
		this.volunteer = volunteer;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateVolunteer();
	        	doSave();
	        	eventBus.fireEvent(new AddOrphanageUpdateEvent());
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new AddOrphanageCancelEvent());
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
		
		if (volunteer == null) return;

		this.display.getNameField().setValue(this.volunteer.getName());
		this.display.getDescField().setValue(this.volunteer.getDescription());
		this.display.getAddressField().setValue(this.volunteer.getAddress());
		this.display.getPhoneField().setValue(this.volunteer.getPhone());
		this.display.getEmailField().setValue(this.volunteer.getEmail());
		this.display.getWebField().setValue(this.volunteer.getWebsite());
	}
	  
	  private void updateVolunteer(){
		  double lat = -1.0;
		  double lng = -1.0;
		  //SolveAddress();

		  // Now add the descriptions and the questions together
		  String description = display.getDescField().getValue().trim();
		  for(CheckBox box : display.getCheckBoxList()) {
			  if (box != null){
				// if the box was checked
				  if (box.getValue()) {
					  description = description + " || Yes ";
				  } else {
					  description = description + " || No ";
				  }
				  description = description + box.getText();
			  }
		  }
		  
		  if (volunteer == null){
			  volunteer = new VolunteerInfo(display.getNameField().getValue().trim(),
					  	description,
					  	display.getAddressField().getValue().trim(), lat, lng, 
					  	display.getPhoneField().getValue().trim(),
					  	display.getEmailField().getValue().trim(),
					  	display.getWebField().getValue().trim());
		  } else {
			  volunteer.setName(display.getNameField().getValue().trim());
			  volunteer.setAddress(display.getAddressField().getValue().trim(),lat,lng);
			  volunteer.setPhone(display.getPhoneField().getValue().trim());
			  volunteer.setEmail(display.getEmailField().getValue().trim());
			  volunteer.setWebsite(display.getWebField().getValue().trim());
			  volunteer.setDescription(description);
		  }
		  

	  }
	  
	  private void doSave() {
	    new RPCCall<VolunteerInfo>() {
	      @Override
	      protected void callService(AsyncCallback<VolunteerInfo> cb) {
	    	  volunteerService.updateVolunteer(volunteer, cb);
	      }

	      @Override
	      public void onSuccess(VolunteerInfo result) {
	        GWT.log("VolunteerAddPresenter: Firing VolunteerUpdateEvent");
	        eventBus.fireEvent(new VolunteerUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving volunteer...");
	      }
	    }.retry(3);
	  }
	  
}
