package com.paypal.bfs.test.employeeserv.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class EmployeeValidation {

	public Set<ValidationMessage> ValidateEmployee(String emp) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonSchemaFactory schemaFactory = JsonSchemaFactory
				.builder(JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909)).objectMapper(objectMapper)
				.build();
		try {
			File file = ResourceUtils.getFile("classpath:v1/schema/employee.json");
			InputStream schemaStream = new FileInputStream(file);
			JsonNode json = objectMapper.readTree(emp);
			JsonSchema schema = schemaFactory.getSchema(schemaStream);
			return schema.validate(json);
		} catch (Exception e) {
			throw e;
		}
	}

}
