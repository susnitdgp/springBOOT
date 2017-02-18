package com.kaniha.commonspare.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kaniha.commonspare.entity.GroupMaterial;
import com.kaniha.commonspare.entity.GroupMaterialPK;

@Repository
public interface GroupMaterialRepository extends JpaRepository<GroupMaterial,GroupMaterialPK>{
	
	@Query("SELECT gm FROM GroupMaterial gm where gm.forYear = :for_year")
	public Page<GroupMaterial> getAllGroupMaterialForYear(@Param("for_year")String for_year,Pageable pageable);

}
