package hu.unideb.inf.rft.neuban.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.neuban.persistence.repositories.ExampleRepository;
import hu.unideb.inf.rft.neuban.service.ExampleService;

@Service
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleRepository exampleRepository;
}
