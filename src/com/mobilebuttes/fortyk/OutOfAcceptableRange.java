package com.mobilebuttes.fortyk;

public class OutOfAcceptableRange extends Exception {

	String mistake;
	/**
	 * 
	 */
	private static final long serialVersionUID = 9196053656416264400L;

	public OutOfAcceptableRange() {
		super();
		mistake = "unknown";
		// TODO Auto-generated constructor stub
	}

	public OutOfAcceptableRange(String arg0) {
		super(arg0);
		mistake = arg0;
	}
}
