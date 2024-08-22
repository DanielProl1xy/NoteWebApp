# Your Notes using Web 

This is my first java spring application. It allows you to store all of your notes on your local server and access them from any your device if it has internet browser.

## Dependencies
- Maven
- Apache Tomcat
- Postgre SQL

## How to use
First you should build and package the app using maven:
```sh
$ git clone https://github.com/DanielProl1xy/NoteWebApp.git
$ cd NoteWebApp
$ mvn install
```
Now you have ```.war``` file in target folder. You can deploy it on your server.
In my case I use Windows batch script:
```bat
> ./run.bat
```
For this script to work  sure to set ```CATALINA_HOME``` to corresponding apache tomcat directory. 

And finally, server deploys at 8080 port, and you can connect by ```localhost:8080```.

## TODOs
- Data base usage improvment
- Veryfing connections
- Multiple users
- Different cosmetic improvment for better user experience