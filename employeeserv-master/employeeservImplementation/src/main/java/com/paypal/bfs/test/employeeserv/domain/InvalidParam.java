/******************************************************************************
 Copyright (c) 2020 Oracle
 SOFTWARE     : NWDAF
 FILENAME     : InvalidParam.java
 DESCRIPTION  : This class is created to handle invalid data.
 Date         : Feb 19, 2021
 Author       : MGARG5
********************************************************************************/
package com.paypal.bfs.test.employeeserv.domain;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InvalidParam {

	@NotEmpty
	private String param;
	private String reason;

	public InvalidParam() {
		super();
	}

	public InvalidParam(String param, String reason) {
		this.param = param;
		this.reason = reason;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public int hashCode() {
		return Objects.hash(param, reason);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		InvalidParam o = (InvalidParam) obj;
		return (this.hashCode() == o.hashCode()) && Objects.equals(this.param, o.param)
				&& Objects.equals(this.reason, o.reason);
	}
}
