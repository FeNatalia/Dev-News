# Dev News

Dev News is the backend API for a developer news site where users can create articles and comment them. It doesn't require a graphical user interface, so it is enough to be able to make requests and get plain json text responses via curl/Postman.

## How to use

You need to have docker and gradle installed in your computer. 
Once you have the set up, run in the project terminal: 

### `docker-compose up`

It will connect the project to the database. 
Then in the new terminal tab run: 

### `gradle bootRun`

This will start Spring Boot and run in the development mode. 
Use POSTMAN for the requests. The default port is localhost:8080. 
