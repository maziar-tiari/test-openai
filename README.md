# Introduction 
Setting up a multi-module project to get quickly started with development. Project will be devided later to repo per service.

# Local development
1.	docker-compose up -d
2.	pgadmin runs on http://localhost:5050
    - add new db server (you can find the credentials for the psql server in the docker-compose file)
    - create database named "user_profile"
        - username: buddies
        - password: password

API references

# Build and Test
- Run all Tests: mvn verify
- Run only unit tests: mvn verify -DskipITs=true
- Run only integration tests: mvn verify -DskipUTs=true


# Contribute
TODO: Explain how other users and developers can contribute to make your code better. 

If you want to learn more about creating good readme files then refer the following [guidelines](https://docs.microsoft.com/en-us/azure/devops/repos/git/create-a-readme?view=azure-devops). You can also seek inspiration from the below readme files:
- [ASP.NET Core](https://github.com/aspnet/Home)
- [Visual Studio Code](https://github.com/Microsoft/vscode)
- [Chakra Core](https://github.com/Microsoft/ChakraCore)