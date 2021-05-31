package fr.unice.polytech.cart.exceptions;

import java.io.Serializable;


public class UnknownCustomerException extends Exception implements Serializable {

	private String name;

	public UnknownCustomerException(String name) {
		this.name = name;
	}


	public UnknownCustomerException() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Customer identified by '" + getName() + "' not found";
	}
}
