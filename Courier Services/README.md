# What Does This Repository Do?

This repository creates a Java library and an Single Stand Java Application.

# Prereqs to run

Java Version Should be installed into the system

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

## Spotless

[Spotless](https://github.com/diffplug/spotless) automatically keeps your code formatted. Currently is is configured to use the [Google Java Format](https://github.com/google/google-java-format).

There is an Intellij plugin called [google-java-format](https://plugins.jetbrains.com/plugin/8527-google-java-format/) that you can install to have Intellij match the format too.

Running a build will automatically format the code. The pipeline is set up to fail the build if the Spotless check fails too.

## Running tests

Unit tests are run automatically when the code is built. You can run also them directly with

```
# Mac
mvn test

# Windows
mvn test
```

## Running Locally

Most of the time, you will want to build the application into a Docker image and run it with docker compose. This assumes you have previously run `mvn clean install`

```
docker-compose up --build -d
```

Stop the locally running containers with:

```
docker-compose down
```

Alternatively, to start the application with maven, you can run

```
# Mac
mvn run

# Windows
mvt run
```
``````
Before running  the app please go to the directory Courier-Service\Courier Services
cd Courier-Service\Courier Services
``````
# Deploying
To deploy into the docker 
run the following commands 
docker-compose up -d
To run in docker instance you need to open the terminal in the docker instance and paste the inputs

Type this command to run locally the app
'Checkout the application'
````
mvn exec:java -Dexec.mainClass="com.everest.engineering.EverestEngineeringApp"  
````
