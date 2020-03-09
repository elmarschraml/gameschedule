# Project Overview
A little utility that reads a list of teams and creates a game schedule for them

# How to build 
#### Java 
Target version is JDK 11. 
Tested on the Amazon Corretto build of OpenJDK. Should run on any other build of Java 11, but not tested on e.g. other VMs.

#### Dependencies
Nothing except what's stated in the Maven POM.
No need to have Maven installed - just use the provided mvnw wrapper
The project follows Maven and Spring boot conventions as outlined here: 
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)


#### Build
./mvnw clean package

### Troubleshooting
If the maven build fails with "invalid target release", you are running locally on a different Java version than 11.
Check that java -version returns Java 11.



# Status 
### Limitations and Roadmap
* Currently only supports reading a single season file at a time, because it simply reads the first file it finds. Could easily be extended to read several files at once.
* Likewise, currently expects only a single season's data per file
* output encoding is hardcoded to UTF-8
* input encoding is required to be ISO-8859-1 (as in provided sample data)
* A league and season currently have no metadata associated. Names of both are accepted as arbitrary strings with no validation or check for uniqueness.
* Data is currently not persisted, but the output file recreated on each run. You could e.g. provide a new implementation of Exporter that also writes to a database.
* No performance tests have been done, scaling beyond a 2-digit number of teams is unknown.
* Likewise, currently not intended for parralel/multi-threaded execution

### TODOs
* lib for json 
* impl importer
* impl scheduler
* unittest scheduler
* caller
