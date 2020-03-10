# Project Overview
A little utility that reads a list of teams and creates a game schedule for them

## How to build 
#### Java 
Target version is JDK 11. 
Tested on the Amazon Corretto build of OpenJDK. 
Should run on any other build of Java 11, but not tested on e.g. other VMs.

#### Dependencies
Nothing except what's stated in the Maven POM.
No need to have Maven installed - just use the provided mvnw wrapper
The project follows Maven and Spring boot conventions as outlined here: 
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)
The code is not really dependant on spring - could be extracted to a plain java app by just replacing 
GameScheduleMain with a version without DI (of courese,  then you need to manually handle properties and logging)


#### Build
./mvnw clean package

#### Troubleshooting
* If the maven build fails with "invalid target release", you are running locally on a different Java version than 11.
Check that java -version returns Java 11.


## How to run

#### Usage 
1. Put your input file mannschaften.json /importdir 
(or change application.properties if you want to use a different diretory or filename)
2. Run java -jar target/gameschedule-1.0.jar
3. Find your generated schedule in the file spielplan<timestamp>.txt in the directory exportdir 
(or edit application.properties to change the export file and directory)

#### Troubleshooting
* if running the jarfile with the java command should fail, try instead ./mvnw spring-boot:run
* If running the app ends in "Build failure" with exit code 1, check the log for errors - most likely the input dir or filename were misconfigured in application.properties.

## Status 

### Known issues
* when parson the json input, jackson logs a warning about illegal access - reason currently unknown, 
but did not want to disable jackson warnings.

#### Limitations and Roadmap
* Currently only supports reading a single season file at a time. 
Could easily be extended to read e.g. all files in a directory at once.
* Likewise, currently expects only a single season's data per file
* output encoding is hardcoded to UTF-8
* input encoding is required to be ISO-8859-1 (as in provided sample data)
* A league and season currently have no metadata associated. 
Names of both are accepted as arbitrary strings with no validation or check for uniqueness.
* Data is currently not persisted, but the output file recreated on each run. 
You could e.g. provide a new implementation of Exporter that also writes to a database.
* Limited to 1000 teams and 10 years of season length - could probably handle more, but would need performance tests
* Currently not intended for parralel/multi-threaded execution

#### Improvements
###### Functional
* read input filename as commandline argument
* randomize games of each round to prevent the same team from playing all its games in a row
* if there is lots more potential game days than games, have an option to spread the games over the season,
instead of having them all happen as quickly as possible
###### Technical
* generalize creation of unit test data
* check and improve test coverage
* split Scheduler into separate classes for determining match days and pairings
