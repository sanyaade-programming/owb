package com.owb.playhelp.client.presenter.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.shared.StandardInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class AddOrphanageStatusPresenter implements Presenter{
	public interface Display {
		Widget asWidget();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		Label getNameField();
		HasValue<String> getHealthField();
		HasValue<String> getEducationField();
		HasValue<String> getFoodField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private OrphanageInfo orphanage;

	private final OrphanageServiceAsync orphanageService;
	
	public AddOrphanageStatusPresenter(OrphanageInfo orphanage,OrphanageServiceAsync orphanageService, SimpleEventBus eventBus, Display display){
		this.orphanage = orphanage;
		this.orphanageService = orphanageService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
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
		
		if (orphanage == null) return;
        
		StandardInfo status = this.orphanage.getStandard();
		this.display.getNameField().setText(this.orphanage.getName());
		if (status!=null){
			this.display.getHealthField().setValue(String.valueOf(status.getHealth()));
			this.display.getEducationField().setValue(String.valueOf(status.getEducation()));
			this.display.getFoodField().setValue(String.valueOf(status.getNutrition()));
		}
	}

	  private void doSave() {
		  
		  StandardInfo status = new StandardInfo();
		  status.setHealth(Float.valueOf(this.display.getHealthField().getValue()));
		  status.setEducation(Float.valueOf(this.display.getEducationField().getValue()));
		  status.setNutrition(Float.valueOf(this.display.getFoodField().getValue()));
		  
		  this.orphanage.setStandard(status);

	    new RPCCall<OrphanageInfo>() {
	      @Override
	      protected void callService(AsyncCallback<OrphanageInfo> cb) {
	    	  orphanageService.updateOrphanage(orphanage, cb);
	      }

	      @Override
	      public void onSuccess(OrphanageInfo result) {
	        GWT.log("OrphanageAddPresenter: Firing ShowPopupAddOrphanageStatusEvent");
	        eventBus.fireEvent(new OrphanageUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
}
