package com.paypal.bfs.test.employeeserv.api.Junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.domain.ProblemDetails;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.model.EmployeeModel;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepo;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeTest {

	@Mock
	EmployeeResourceImpl impl;

	@Mock
	EmployeeRepo repo;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ModelMapper mapper;

	@Test
	public void createEmployeeTestResponseBadRequest() throws JsonProcessingException, Exception {

		// emp data required field not passed validation fail
		ObjectMapper om = new ObjectMapper();
		Employee emp = new Employee();
		emp.setFirstName("manisha");
		emp.setLastName("garg");

		MvcResult res = mockMvc.perform(put("/v1/bfs/employees", emp).contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(emp))).andExpect(status().isBadRequest()).andReturn();

		MockHttpServletResponse respObj = res.getResponse();

		String jsonStr = respObj.getContentAsString();
		ProblemDetails retObj = om.readValue(jsonStr, ProblemDetails.class);
		assertEquals(retObj.getStatus(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void createEmployeeTestResponseCreated() throws JsonProcessingException, Exception {
		// emp data required field not passed validation fail
		ObjectMapper om = new ObjectMapper();
		Employee emp = new Employee();
		emp.setFirstName("manisha");
		emp.setLastName("garg");
		emp.setDateOfBirth("1989-03-21");
		Address add = new Address();
		add.setLine1("whitefield");
		add.setCity("bangalore");
		add.setState("karnataka");
		emp.setAddress(add);

		EmployeeModel model = mapper.map(emp, EmployeeModel.class);
		when(repo.save(model)).thenReturn(model);

		MvcResult res = mockMvc.perform(put("/v1/bfs/employees", emp).contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(emp))).andExpect(status().isCreated()).andReturn();

		assertEquals(res.getResponse().getStatus(), 201);
	}
	
	@Test
	public void getEmployeeTest() throws JsonProcessingException, Exception {
		// emp data required field not passed validation fail
		ObjectMapper om = new ObjectMapper();
		Employee emp = new Employee();
		emp.setFirstName("manisha");
		emp.setLastName("garg");
		emp.setDateOfBirth("1989-03-21");
		Address add = new Address();
		add.setLine1("whitefield");
		add.setCity("bangalore");
		add.setState("karnataka");
		emp.setAddress(add);

		EmployeeModel model = mapper.map(emp, EmployeeModel.class);
		
		when(repo.findByEmployeeId(1)).thenReturn(model);

		MvcResult res = mockMvc.perform(get("/v1/bfs/employees/{employeeId}", 1))
				.andExpect(status().is2xxSuccessful())
				.andReturn();	 
		assertEquals(res.getResponse().getStatus(), 200);
	}

}
