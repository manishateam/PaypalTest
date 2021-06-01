package com.paypal.bfs.test.employeeserv.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;
import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.domain.EmployeeException;
import com.paypal.bfs.test.employeeserv.domain.InvalidParam;
import com.paypal.bfs.test.employeeserv.domain.ProblemDetails;
import com.paypal.bfs.test.employeeserv.model.EmployeeModel;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepo;
import com.paypal.bfs.test.employeeserv.validation.EmployeeValidation;

import java.net.URI;
import java.util.Iterator;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	EmployeeRepo repo;

	@Autowired
	ModelMapper mapper;

	@Autowired
	ObjectMapper obj;

	@Override
	public ResponseEntity<Object> employeeGetById(String id) {

		try {
			EmployeeModel emp = repo.findByEmployeeId(Integer.parseInt(id));
			if (emp == null) {
				ProblemDetails prob = ProblemDetails.forNotFound();
				prob.setCause("Employee not found for requested id");
				prob.setDetail("Employee does not exist for requested Employee Id");
				return ResponseEntity.status(prob.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON)
						.body(prob);
			} else {
				Employee employee = mapper.map(emp, Employee.class);
				return new ResponseEntity<>(employee, HttpStatus.OK);
			}
		} catch (EmployeeException e) {
			ProblemDetails prob = ProblemDetails.forInternalError();
			prob.setDetail(e.getMessage());
			return ResponseEntity.status(prob.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(prob);

		} catch (Exception e) {
			ProblemDetails prob = ProblemDetails.forInternalError();
			prob.setDetail(e.getMessage());
			return ResponseEntity.status(prob.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(prob);
		}
	}

	@Override
	public ResponseEntity<Object> createEmployee(String emp) {
		EmployeeValidation empValidation = new EmployeeValidation();
		try {
			Set<ValidationMessage> validationResult = empValidation.ValidateEmployee(emp);
			if (validationResult.isEmpty()) {
				Employee employee = obj.readValue(emp, Employee.class);
				EmployeeModel empObj = mapper.map(employee, EmployeeModel.class);
				repo.save(empObj);
				URI uri = URI.create("/v1/bfs/employees/" + empObj.getEmployeeId());
				return ResponseEntity.created(uri).body(empObj);
			} else {
				ProblemDetails prob = handleBadRequest(validationResult);
				return ResponseEntity.status(prob.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON)
						.body(prob);

			}
		} catch (EmployeeException e) {
			ProblemDetails prob = ProblemDetails.forInternalError();
			prob.setDetail(e.getMessage());
			return ResponseEntity.status(prob.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(prob);

		} catch (Exception e) {
			ProblemDetails prob = ProblemDetails.forInternalError();
			prob.setDetail(e.getMessage());
			return ResponseEntity.status(prob.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(prob);
		}

	}

	public ProblemDetails handleBadRequest(Set<ValidationMessage> validateResult) {
		ProblemDetails prob = ProblemDetails.forBadRequest();
		Iterator<ValidationMessage> it = validateResult.iterator();
		while (it.hasNext()) {
			prob.setCause("Invalid input data");
			ValidationMessage err = it.next();

			prob.addInvalidParam(new InvalidParam(err.getType(), err.getMessage()));
		}
		return prob;

	}

}
