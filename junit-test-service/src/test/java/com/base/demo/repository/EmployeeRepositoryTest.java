package com.base.demo.repository;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.demo.dao.EmployeeDao;
import com.base.demo.entity.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class EmployeeRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeDao employeeDao;

	@Test
	void test() {
		Employee alex = new Employee();
		alex.setEmpname("Kavi");
		alex.setEmpId(12);
		alex.setSalary(10000);
		alex.setDeptId(2);
		employeeDao.save(alex);

	    // when
	    Employee found = employeeDao.getOne(alex.getEmpId());

	    // then
	    assertThat(new Long(found.getEmpId()))
	      .isEqualTo(alex.getEmpId());
	}

}
