package com.kaniha.auth.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kaniha.auth.entity.WebUser;


@Repository
public interface WebUserRepository extends JpaRepository<WebUser,String> {
	
	@Query("SELECT w FROM WebUser w where w.emp_num = :username AND w.password=:password")
	public Optional<WebUser> checkLogin(@Param("username")String username,
			@Param("password")String password);
	

}
