package com.wmp.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wmp.model.Employee;
import com.wmp.model.Skill;
import com.wmp.model.Solr;
import com.wmp.repository.EmployeeRepository;
import com.wmp.repository.SkillRepository;
import com.wmp.repository.SolrRepository;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

@Service
public class SolrService implements SolrServiceInterface {

	@Resource
	private SolrRepository repository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Resource
	SkillRepository skillRepository;
	
	@Transactional
	@Override
	public void addToIndex(Long id) {
		
		String urlString = "http://localhost:8983/solr/skilltracker";
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());

		Employee e = this.employeeRepository.findById(id);
		List<Skill> s = this.skillRepository.findById(id);

		String[] sArray = new String[s.size()];
		int i = 0;
		for (Skill sElement : s) {
			System.out.println(i);
			sArray[i++] = sElement.getSkill();
			System.out.print(sArray[i - 1]);
		}

		Solr add = new Solr("" + id, e.getFirstName(), e.getLastName(), e.getCareerLevel(), e.getAddress(), e.getCity(),
				e.getState(), e.getZipcode(), e.getCreatedAt().toString(), e.getUpdatedAt().toString(), sArray,
				e.getCareerLevel(), sArray);
		
		Solr current = repository.findByIdString("" + id);
		if(current == null) {
			//repository.save(add);
			try {
				solr.addBean(add);
				solr.commit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SolrServerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("WWWWWWWWWWW   current not found");
		} else {
			try {
				solr.deleteByQuery("Id:" + id);
				solr.commit();
				solr.addBean(add);
				solr.commit();
			} catch (SolrServerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//repository.delete(current);
			//repository.save(add);
			System.out.println("WWWWWWWWWWW   current found and deleted");
		}
	}

	@Override
	public void deleteFromIndex(Long id) {
		// TODO Auto-generated method stub

	}

	// Add methods here
}