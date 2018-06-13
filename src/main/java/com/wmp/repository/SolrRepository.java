package com.wmp.repository;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.wmp.model.Solr;

/**
 * Created by Garrett Kizior on 5/22/2018.
 */

@Repository
public interface SolrRepository extends SolrCrudRepository<Solr, String> {

	List<Solr> findBySkillsContains(String skill);
	List<Solr> findBySkillsNoSpacesContains(String skill);


	// List<Skill> findByFirstNameContainsOrLastNameContains(String firstName,
	// String lastName); // find documents whose docTitle ends with specified string

	// List<Skill> findByEmpIdContains(String id);

	@Query("Id:*?0* OR firstName:*?0* OR lastName:*?0* OR careerLevel:*?0* OR skills:*?0*")
	List<Solr> findByQueryAnnotation(String query);
	List<Solr> findByCareerLevelContains(String careerLevel);
	List<Solr> findByCfnameContains(String cfname);
}