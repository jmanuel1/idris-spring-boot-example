package io.github.mmhelloworld.helloworld;

import io.github.mmhelloworld.helloworld.EmployeeRepository;
import io.github.mmhelloworld.helloworld.EmployeeService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import java.util.List;

/*
This is written in Java so that Micronaut can process the annotations at
compile-time from the source code.
*/
@Controller("/")
public class EmployeeController {
  EmployeeRepository employeeRepository;

  EmployeeController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Get("/employees")
  public List<Employee> getEmployees() {
    return EmployeeService.getEmployees(employeeRepository);
  }

  @Post("/employees")
  public Employee saveEmployee(@Body Employee employee) {
    return EmployeeService.saveEmployee(employeeRepository, employee);
  }
}
