package com.poc.camel.service;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.camel.model.Employee;

@Service
public class EmployeeService extends RouteBuilder {

	@Autowired
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub

		from("direct:insert").process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				Employee employee = xchg.getIn().getBody(Employee.class);
				System.out.println("in service");
				String query = "INSERT INTO employee(empId,empName)values('" + employee.getEmpId() + "','"
						+ employee.getEmpName() + "')";
				xchg.getIn().setBody(query);
			}
		}).to("jdbc:dataSource");
	}
}

/*
 * @Autowired DataSource dataSource;
 * 
 * public DataSource getDataSource() { return dataSource; }
 * 
 * public void setDataSource(DataSource dataSource) { this.dataSource =
 * dataSource; }
 * 
 * @Override public void configure() throws Exception { // TODO Auto-generated
 * method stub dataSource.getConnection(); from("direct:insert").process(new
 * Processor() { public void process(Exchange xchg) throws Exception { Employee
 * employee = xchg.getIn().getBody(Employee.class); String query =
 * "INSERT INTO employee(empId,empName)values('" + employee.getEmpId() + "','" +
 * employee.getEmpName() + "')"; xchg.getIn().setBody(query); }
 * }).to("jdbc:dataSource");
 * 
 * }
 * 
 * 
 * 
 * @Override public void configure() throws Exception { // TODO Auto-generated
 * method stub from("direct:insert").process(new Processor() { public void
 * process(Exchange exc) throws Exception {
 * 
 * Employee employee = exc.getIn().getBody(Employee.class); String query =
 * "INSERT INTO employee(empId,empName)values('" + employee.getEmpId() + "','" +
 * employee.getEmpName() + "')"; exc.getIn().setBody(query); }
 * }).to("jdbc:dataSource");
 * 
 * }
 * 
 */