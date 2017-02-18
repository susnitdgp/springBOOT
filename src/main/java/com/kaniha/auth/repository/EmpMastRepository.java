package com.kaniha.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kaniha.auth.entity.EmpMast;


@Repository
public interface EmpMastRepository extends JpaRepository<EmpMast,String> {
	
	@Query("SELECT e FROM EmpMast e where e.emp_num = :emp_num")
	public EmpMast getUserDetails(@Param("emp_num")String emp_num);

}
