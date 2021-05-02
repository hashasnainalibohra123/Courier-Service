# What Does This Repository Do?

This repository creates a Java library and an Single Stand Java Application.

# Prereqs to run

Java Version Should be installed into the system

### Github User
Add the following service account as a user to the Github org:
  * User: `hasnainalibohra`

### Create Artifactory Docker
Go to [Tech Hub](https://tech.optum.com/products/artifactory/create-artifactory-namespace) to create the namespace.
  * Name: `ewdservices`
  * User: `ewdworker`

### Create Load Balancers

Go to [Tech Hub](https://tech.optum.com/products/load-balancing/create-load-balancer) and create a load balancer for both Stage and Production.

Stage load balancer: `ewd-file-producer-stage.optum.com`

Production load balancer: `ewd-file-producer-prod.optum.com`

### Create Openshift Projects
Go to [Tech Hub](https://tech.optum.com/products/openshift/create-osfi-origin-project) and use the Openshift project creation wizard (**Create OSFI Origin Project**) with the following information:  

Be sure to also add `ewdworker` as another Openshift project administrator.
* NonProd
  * Cluster: `origin-ctc-core-nonprod.optum.com`
  * Zone: `core`
  * Environment: `Non-prod`
  * Project Name: `ewd-file-producer`

* Stage (CTC)
  * Datacenter: `ctc`
  * Zone: `core`
  * Environment: `Stage`
  * Project Name: `ewd-file-producer-stage-ctc`
* Stage (ELR)
  * Datacenter: `elr`
  * Zone: `core`
  * Environment: `Stage`
  * Project Name: `ewd-file-producer-stage-elr`
* Prod (CTC)
  * Datacenter: `ctc`
  * Zone: `core`
  * Environment: `Prod`
  * Project Name: `ewd-file-producer-prod-ctc`
* Prod (ELR)
  * Datacenter: `elr`
  * Zone: `core`
  * Environment: `Prod`
  * Project Name: `ewd-file-producer-prod-elr`

### Create Jenkins Openshift project and master

Use [Tech Hub](https://tech.optum.com/) wizards to create a Jenkins project and master server (only if you don't already have a Jenkins server)

# First time configuration

## Importing into Intellij

The `build.gradle` is configured with the Intellij Idea plugin. Run this command to generate the project files.

```
# Mac
./gradlew idea openIdea

# Windows
gradlew.bat idea openIdea
```

When Intellij opens, select `Import Gradle Project` and `Enable Auto-Import`.

## Building the project

```
# Mac
./gradlew build

# Windows
gradlew.bat build
```

## Spotless

[Spotless](https://github.com/diffplug/spotless) automatically keeps your code formatted. Currently is is configured to use the [Google Java Format](https://github.com/google/google-java-format).

There is an Intellij plugin called [google-java-format](https://plugins.jetbrains.com/plugin/8527-google-java-format/) that you can install to have Intellij match the format too.

Running a build will automatically format the code. The pipeline is set up to fail the build if the Spotless check fails too.

## Running tests

Unit tests are run automatically when the code is built. You can run also them directly with

```
# Mac
./gradlew test

# Windows
gradlew.bat test
```

Gradle will skip the tests if the code hasn't changed since when you last ran the tests.  

Integration tests can be run with this command.

```
# Mac
./gradlew testIntegration

# Windows
gradlew.bat testIntegration
```

The integration tests need a running application to test against, it won't start one up and the tests will fail.

## Running Locally

Most of the time, you will want to build the application into a Docker image and run it with docker compose. This assumes you have previously run `./gradlew build`

```
docker-compose up --build -d
```

Stop the locally running containers with:

```
docker-compose down
```

Alternatively, to start the application with gradle, you can run

```
# Mac
./gradlew run

# Windows
gradlew.bat run
```

# Deploying

Triggering deployments to stage and prod is done with the [GP Scaffold Gradle](https://github.optum.com/gp-optumil/gp-scaffold-gradle) plugin. Run `tasks` to view the **Government Programs Scaffold tasks** that enable control of the pipelines.

```
./gradlew tasks
```
