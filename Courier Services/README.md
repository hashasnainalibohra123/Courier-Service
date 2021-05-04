# What Does This Repository Do?

This repository creates a Java library and an Single Stand Java Application.
This is  the courier service application which detetmine the cost to deliver the package inclusing coupons.

# Prereqs to run

1. Java Version Should be installed into the system
2. Docker installed for deployment

### Github User
Add the following service account as a user to the Github org:
  * User: `hasnainalibohra`

# First time configuration

## Importing into Intellij

The `maven' is configured with the Intellij Idea plugin. Run this command to generate the project files.

```
# Mac
mvn clean install

# Windows
mvn clean install
```

When Intellij opens, select `Import Maven Project` and `Enable Auto-Import`.

## Building the project

```
# Mac
mvn clean install

# Windows
mvn clean install
```

## Running tests

Unit tests are run automatically when the code is built. You can run also them directly with

```
# Mac
mvn test

# Windows
mvn test
```

## Running Locally
```mvn exec:java -Dexec.mainClass="com.everest.engineering.EverestEngineeringApp" ```

Alternatively, to start the application with maven, you can run

```
# Mac
mvn run

# Windows
mvn run
```
``````
Before running  the app please go to the directory Courier-Service\Courier Services
cd Courier-Service\Courier Services
``````
# Deploying
###1. Build the package :-
Run  the following command in the directory Courier-Service\Courier Services
```
mvn clean package
```
Build the Docker app 
```
docker build -t courierservices:1.0-SNAPSHOT .

```
Most of the time, you will want to build the application into a Docker image and run it with docker compose. This assumes you have previously run `mvn clean install`

```
docker-compose up --build -d
```

Stop the locally running containers with:

```
docker-compose down
```

To deploy into the docker 
run the following commands 
docker-compose up -d
To run in docker instance you need to open the terminal in the docker instance and paste the inputs

Type this command to run locally the app
'Checkout the application'
````
mvn exec:java -Dexec.mainClass="com.everest.engineering.EverestEngineeringApp"  
````
