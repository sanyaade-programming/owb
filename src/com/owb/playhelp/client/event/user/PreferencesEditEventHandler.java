/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.user;

import com.google.gwt.event.shared.EventHandler;

public interface PreferencesEditEventHandler extends EventHandler {
	void onEditPreference(PreferencesEditEvent event);
}

