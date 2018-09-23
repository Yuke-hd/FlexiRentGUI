package controller;

import javafx.event.Event;
import javafx.event.EventType;

public class CustomEvent extends Event {

	private String parameter;

	public static final EventType<CustomEvent> CUSTOM = new EventType<CustomEvent>(ANY, "CUSTOM");

	public CustomEvent(String parameter) {
		super(CustomEvent.CUSTOM);
		this.parameter = parameter;
	}

	public String getParameter() {
		return this.parameter;
	}
	

}