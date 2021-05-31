package fr.unice.polytech.cart.exceptions;

import java.io.Serializable;


public class UnknownItemException extends Exception implements Serializable {

	private String id;

	public UnknownItemException(String name) {
		this.id = id;
	}


	public UnknownItemException() {
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "item id '" + getId() + "' not found";
	}
}
