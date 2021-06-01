/******************************************************************************
 Copyright (c) 2020 Oracle
 SOFTWARE     : NWDAF
 FILENAME     : ProblemDetails.java
 DESCRIPTION  : this class created to return object if service fails
 Date         : Oct 19, 2020
 Author       : sunnyku
********************************************************************************/
package com.paypal.bfs.test.employeeserv.domain;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProblemDetails implements Serializable  {

	private static final long serialVersionUID = 9146513767908525413L;

	private static final ObjectMapper objectMapper = new ObjectMapper();
    private String type;
	private String title;
	private HttpStatus status;
	private String detail;
	private URI instance;
	private String cause;

	@Valid
	private List<InvalidParam> invalidParams;

	public ProblemDetails() {
		super();
	}

	public ProblemDetails(String type, String title, HttpStatus status, String detail, URI instance, String cause,
			List<InvalidParam> invalidParams) {
		this.type = type;
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.instance = instance;
		this.cause = cause;
		this.invalidParams = invalidParams;
	}

	public ProblemDetails(Integer statusCode) {
		this.status = HttpStatus.valueOf(statusCode);
		this.title = HttpStatus.valueOf(statusCode).getReasonPhrase();
		this.detail = HttpStatus.valueOf(statusCode).getReasonPhrase();
		this.cause = HttpStatus.valueOf(statusCode).getReasonPhrase();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public URI getInstance() {
		return instance;
	}

	public void setInstance(URI instance) {
		this.instance = instance;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public List<InvalidParam> getInvalidParams() {
		return invalidParams;
	}

	public void setInvalidParams(List<InvalidParam> invalidParams) {

		this.invalidParams = invalidParams;
		if ((this.invalidParams != null) && (!this.invalidParams.isEmpty())) {
			this.invalidParams.sort(new Comparator<InvalidParam>() {
				@Override
				public int compare(InvalidParam arg0, InvalidParam arg1) {
					if (arg0 == null && arg1 == null)
						return 0;
					if (arg0 != null && arg1 == null)
						return 1;
					if (arg0 == null && arg1 != null)
						return -1;
					return arg0.getParam().compareTo(arg1.getParam());
				}
			});
		}
	}

	public void addInvalidParam(InvalidParam invalidParam) {
		if (this.invalidParams == null) {
			this.invalidParams = new ArrayList<InvalidParam>();
		}

		if (invalidParam != null) {
			this.invalidParams.add(invalidParam);
			this.invalidParams.sort(new Comparator<InvalidParam>() {
				@Override
				public int compare(InvalidParam arg0, InvalidParam arg1) {
					if (arg0 == null && arg1 == null)
						return 0;
					if (arg0 != null && arg1 == null)
						return 1;
					if (arg0 == null && arg1 != null)
						return -1;
					return arg0.getParam().compareTo(arg1.getParam());
				}
			});
		}
	}

	public static ProblemDetails forBadRequest() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.BAD_REQUEST);
		prob.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
		prob.setDetail(HttpStatus.BAD_REQUEST.getReasonPhrase());
		prob.setCause(HttpStatus.BAD_REQUEST.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forInternalError() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		prob.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		prob.setDetail(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		prob.setCause(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forNotFound() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.NOT_FOUND);
		prob.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
		prob.setDetail(HttpStatus.NOT_FOUND.getReasonPhrase());
		prob.setCause(HttpStatus.NOT_FOUND.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forForbidden() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.FORBIDDEN);
		prob.setTitle(HttpStatus.FORBIDDEN.getReasonPhrase());
		prob.setDetail(HttpStatus.FORBIDDEN.getReasonPhrase());
		prob.setCause(HttpStatus.FORBIDDEN.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forUnsupportedContentType() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		prob.setTitle(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
		prob.setDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
		prob.setCause(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forConflict() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.CONFLICT);
		prob.setTitle(HttpStatus.CONFLICT.getReasonPhrase());
		prob.setDetail(HttpStatus.CONFLICT.getReasonPhrase());
		prob.setCause(HttpStatus.CONFLICT.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forUnprocessableRequest() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		prob.setTitle(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
		prob.setDetail(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
		prob.setCause(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
		return prob;
	}

	public static ProblemDetails forNotImplemented() {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(HttpStatus.NOT_IMPLEMENTED);
		prob.setTitle(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
		prob.setDetail(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
		prob.setCause(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
		return prob;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cause, detail, instance, status, title, type, invalidParams);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		ProblemDetails o = (ProblemDetails) obj;
		return (this.hashCode() == o.hashCode()) && Objects.equals(this.cause, o.cause)
				&& Objects.equals(this.detail, o.detail) && Objects.equals(this.instance, o.instance)
				&& Objects.equals(this.invalidParams, o.invalidParams) && Objects.equals(this.status, o.status)
				&& Objects.equals(this.title, o.title) && Objects.equals(this.type, o.type);
	}
	
	@Override
	public String toString() {
		String text;
		try {
			text = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			text = "{}";
		}
		return text;
	}

}
