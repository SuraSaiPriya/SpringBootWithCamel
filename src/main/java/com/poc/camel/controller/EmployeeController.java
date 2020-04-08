package com.poc.camel.controller;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.camel.model.Employee;

@RestController
public class EmployeeController {

	@Autowired
	private ProducerTemplate producerTemplate;

	@RequestMapping(value = "/employees", consumes = "application/json", method = RequestMethod.POST)
	public boolean insertEmployee(@RequestBody Employee emp) {
		producerTemplate.requestBody("direct:insert", emp, List.class);
		return true;

	}

	

}

