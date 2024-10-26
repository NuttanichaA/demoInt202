package sit.int202.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int202.demo.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
