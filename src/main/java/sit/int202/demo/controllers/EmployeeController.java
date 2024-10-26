package sit.int202.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sit.int202.demo.entities.Employee;
import sit.int202.demo.repository.EmployeeRepository;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    @RequestMapping("/employee")
    public String employeePage(Model model){
        List<Employee> employees = repository.findAll();
        model.addAttribute("employees", employees);
        return "employee_list";
    }
}
