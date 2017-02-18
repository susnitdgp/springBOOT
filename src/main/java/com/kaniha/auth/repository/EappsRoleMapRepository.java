package com.kaniha.auth.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kaniha.auth.entity.EappsRoleMap;
import com.kaniha.auth.entity.EappsRolePK;

@Repository
public interface EappsRoleMapRepository extends JpaRepository<EappsRoleMap,EappsRolePK> {
		
	@Query("SELECT e.role_alloted FROM EappsRoleMap e where e.emp_num = :emp_num")
	public List<String> getEAPPSRoles(@Param("emp_num")String emp_num);

}
