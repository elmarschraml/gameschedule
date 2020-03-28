# Gameschedule

### Purpose
Schedules games for a league of sports games.

### Input
Input contains a list of teams, and the dates of a season.
See inputexample.json for the input format. 

### Requirements
The schedule should have two rounds. In each round, every team has to play every other team. 
For every pairing of teams, if a team was the home team in the first round, it should be the away team in the second round.
Games happen every Sunday, withing the start- and enddate of the season as defined in the input file. (You may disregard holidays)

### Output
Output should be written to a text file in the following format
Su 22.03.2015 | Team A - Team B 
Su 29.03.2015 | Team C - Team D ...
