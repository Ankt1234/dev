package com.person.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.person.entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer>{
	
	
	Optional<Person> findDistinctByLastnameAndFirstname(String lname,String fname);
	List<Person> findDistinctByLastnameOrFirstname(String lname,String fname);
	Optional<Person> findByLastnameAndFirstname(String fname,String lname);
    List<Person> getBySalaryBetween(int sal1,int sal2);
    List<Person>  findBySalaryLessThan(int salary);
    List<Person>  findBySalaryGreaterThanEqual(int sal);
    List<Person> findByFirstnameLike(String name);
    List<Person> findByLastnameNotOrderBySalaryDesc(String name);
    List<Person> findBySalaryIn(List<Integer> list);
    List<Person> findBySalaryNotIn(List<Integer> list);
    

}
