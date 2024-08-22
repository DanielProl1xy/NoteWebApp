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
Now you have ```.war``` file which you can deploy on your server. On Windows you can use:
```bat
> ./run.bat
```
It deploys server at 8080 port, and you can connect by ```localhost:8080```.


## TODOs
- Data base usage improvment
- Veryfing connections
- Multiple users
- Different cosmetic improvment for better user experience