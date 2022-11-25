package org.medmota.testspringdata.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medmota.testspringdata.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	void setup() {
		employee = new Employee().builder().firstName("Mohamed").lastName("MOTAOUIA").email("med.motaouia@gmail.com")
				.build();
	}

	@Test
	void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		Employee savedEmployee = employeeRepository.save(employee);

		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	@Test
	void givenEmployeesList_whenFindAll_thenEmployeesList() {
		Employee employee2 = new Employee().builder().firstName("Samir").lastName("MOTAOUIA")
				.email("sami.motaouia@gmail.com").build();

		employeeRepository.save(employee);
		employeeRepository.save(employee2);

		List<Employee> listOfAllEmployees = employeeRepository.findAll();

		assertThat(listOfAllEmployees).isNotNull();
		assertThat(listOfAllEmployees.size()).isEqualTo(2);
	}

	@Test
	void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
		employeeRepository.save(employee);

		Employee employeeDB = employeeRepository.findById(employee.getId()).get();

		assertThat(employeeDB).isNotNull();
		assertThat(employeeDB.getLastName()).isEqualTo("MOTAOUIA");
	}

	@Test
	void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

		employeeRepository.save(employee);

		employeeRepository.deleteById(employee.getId());

		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

		assertThat(employeeOptional).isEmpty();

	}

}
