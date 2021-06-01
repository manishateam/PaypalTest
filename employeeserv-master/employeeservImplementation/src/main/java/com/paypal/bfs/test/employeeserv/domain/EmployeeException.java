package com.paypal.bfs.test.employeeserv.domain;

public class EmployeeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ProblemDetails probDetails;

	public EmployeeException() {
		super();
	}

	public EmployeeException(ProblemDetails prob) {
		this.probDetails = prob;
	}

	public ProblemDetails getProbDetails() {
		return probDetails;
	}

	public void setProbDetails(ProblemDetails probDetails) {
		this.probDetails = probDetails;
	}

}
