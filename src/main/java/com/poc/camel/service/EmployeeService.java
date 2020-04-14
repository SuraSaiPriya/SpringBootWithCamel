package com.poc.camel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		from("direct:select").setBody(constant("select * from Employee")).to("jdbc:dataSource")
				.process(new Processor() {
					public void process(Exchange xchg) throws Exception {
						ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) xchg.getIn()
								.getBody();
						List<Employee> employees = new ArrayList<Employee>();

						System.out.println(dataList);
						for (Map<String, String> data : dataList) {

							Employee employee = new Employee();

							employee.setEmpId(data.get("empId"));
							employee.setEmpName(data.get("empName"));

							employees.add(employee);
						}
						xchg.getIn().setBody(employees);
					}
				});

	}
}
