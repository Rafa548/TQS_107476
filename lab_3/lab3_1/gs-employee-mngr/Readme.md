# a) (assertThat ... conditions are Assertj expressions)
### Assert that allEmployees has a size of 3 and contains only the names of Alex, Ron, and Bob.
``` java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

### Assert that the object fromDb is not null.
``` java
assertThat(fromDb).isNotNull();
```

### Assert that the email retrieved from the database matches the email of the employee.
``` java
assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
```

# b)
``` java
Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
```

# c)
- @Mock is used for unit testing with Mockito to create mock objects.
- @MockBean is used for integration testing with Spring Boot to mock Spring beans in the application context.

# d)
The role of the file “application-integrationtest.properties” is to provide properties to be used in the program execution in this case it
provides the properties to access the database.

# e)


