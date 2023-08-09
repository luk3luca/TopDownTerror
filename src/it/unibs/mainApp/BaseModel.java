package it.unibs.mainApp;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class BaseModel {
	protected EventListenerList listenerList = new EventListenerList();

	public void addChangeListener(ChangeListener l) {
	    listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
	    listenerList.remove(ChangeListener.class, l);
	}

	protected void fireValuesChange() {
		fireValuesChange(new ChangeEvent(this));
	}
	
	protected void fireValuesChange(ChangeEvent changeEvent) {

	    Object[] listeners = listenerList.getListenerList();

	    for (int i = listeners.length - 2; i >= 0; i -=2 ) {

	        if (listeners[i] == ChangeListener.class) {

	            ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
	        }
	    }
	}
}
