package com.person.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.person.entity.Person;
import com.person.repo.PersonRepo;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonRepo repo;
	
	@GetMapping("/{id}")
	public Person getPerson(@PathVariable("id") Integer id) {
		Optional<Person> findById = repo.findById(id);
		return findById.filter(p->p.getFirstname()!=null).get();
	}
	
	@PostMapping(value="/")
	public  ResponseEntity<Person> createPerson(@RequestBody Person person) {
		
		Person save = repo.save(person);
		return ResponseEntity.ok(save);
	}

	@GetMapping("/lastname")
	public ResponseEntity<Person> getByDistinctLastname(@RequestParam(name="fname")String fname,@RequestParam(name="lname")String lname) {
		
		Optional<Person> findDistinctByLastnameAndFirstname = repo.findDistinctByLastnameAndFirstname(lname, fname);
		
		if(findDistinctByLastnameAndFirstname.isPresent()) {
			System.out.println("found...");
			return ResponseEntity.ok(findDistinctByLastnameAndFirstname.get());
			
		}
		return null;
	}
	@GetMapping("/lastnameor")
	public List<Person> findDistinctByLastnameOrFirstname(@RequestParam(name="fname")String fname,@RequestParam(name="lname")String lname) {
		 List<Person> findDistinctByLastnameOrFirstname = repo.findDistinctByLastnameOrFirstname(lname,fname);
		return findDistinctByLastnameOrFirstname;
	}
	@GetMapping(value="/sSal/{sSal}/eSal/{eSal}")
	public List<Person> getBySalaryBetween(@PathVariable("sSal")int sSal, @PathVariable("eSal") int eSal){
		 return repo.getBySalaryBetween(sSal, eSal)
				   .stream().sorted(Comparator.comparing(Person::getSalary))
				   .collect(Collectors.toList());
	}
	@GetMapping("/salary/{salary}")
	public List<Person> findBySalaryLessThan(@PathVariable("salary")int salary){
		return repo.findBySalaryLessThan(salary).stream().sorted(Comparator.comparing(Person::getSalary)).collect(Collectors.toList());
	}
	@GetMapping("/grtSalary/{salary}")
	public List<Person>  findBySalaryGreaterThanEqual(@PathVariable("salary")int sal){
		return repo.findBySalaryGreaterThanEqual(sal).stream().sorted(Comparator.comparing(Person::getSalary)).collect(Collectors.toList());
	}
	@GetMapping("/nameLike/{name}")
	List<Person> findByFirstnameLike(@PathVariable("name")String name){
		return repo.findByFirstnameLike("%"+name+"%").stream().sorted(Comparator.comparing(Person::getFirstname)).collect(Collectors.toList());
	}
	@GetMapping("/orderByName/{name}")
	public List<Person> findByLastnameNotOrderBySalaryDesc(@PathVariable("name")String name){
		 return repo.findByLastnameNotOrderBySalaryDesc(name);
	}
	@GetMapping("/in")
	public List<Person> findBySalaryIn(@RequestBody List<Integer> list){
		return repo.findBySalaryIn(list);
	}
	@GetMapping("/notin")
	public List<Person> findBySalaryNotIn(){
		return repo.findBySalaryNotIn(Arrays.asList(17000,19000));
	}
}
