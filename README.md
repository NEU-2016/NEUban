# NEUban

# Build and run

#### Configurations

Open the `neuban/persistence/src/main/resources/application.properties` file and set your own configurations.

These three lines are important at present:

* spring.datasource.url = jdbc:mysql://localhost:3306/neuban?useSSL=false -> The name of the schema is: `neuban`
* spring.datasource.username = root -> The username is: `root`
* spring.datasource.password = root -> The password is: `root`

#### Creating schema in MySQL

* Start MYSQL workbench
* Connect `Local instance MySQL - root - localhost:3306`
* Create schema -> Name: `neuban` -> Collation: `utf8 - utf8_general_ci` -> `Apply`
* Users and Privileges -> `root` -> `Schema Privileges` -> `Add Entry...` -> `Selected schema` -> `neuban` -> `OK` -> `Apply` 

#### Prerequisites

* Java 8
* Maven > 3.0
* MySQL

#### From terminal

Go on the project's root folder, then type:

`$ mvn spring-boot:run`

#### From Eclipse (Spring Tool Suite)

Import as Existing Maven Project and run it as Spring Boot App.


### Lombok

1. home page: https://projectlombok.org/index.html

1. features: https://projectlombok.org/features/

1. download: https://projectlombok.org/download.html

1. installation details: https://github.com/mplushnikov/lombok-intellij-plugin

## About

NEUban is an open source Java EE project as an assignment for a course at the University of Debrecen. It implements the sprint oriented agile development methodology.

Our goal is to make a user-friendly tool to visualize works and workflows on a Kanban board environment.

## Functionality

* Team-based
* Sprint integration
* Multiple roles for tasks
    * Investigators
    * Developers
    * Reviewers
    * Verifiers
    * etc.
* User-friendly task management:
    * Drag'n'Drop task tiles
        * Multiple dragging
    * Task architecture 
        * Epics
        * Features
        * User stories
        * Tasks
        * etc.
    * Filtering possibilities
        * Assigned users
        * Task types
        * Sprint iterations
    * Column management
        * Adding / removing
        * Limiting tasks per column
* Shareable links
* Image uploads
* Comments
* History
* Internationalization

## Technologies

### Core Layer

### Service Layer

* REST

### Web Layer

* AngularJS
* Bootstrap CSS

## Continuous integration

WIP

## Development progress

* [Trello](https://trello.com/b/iX8giolP)
* [Documentations](https://drive.google.com/drive/folders/0B-X0ddF3Q-BOUERZQXhsTlhLMW8)

## Contributors

### Developers

* Erdei Krisztián
* Fekete Attila
* Iványi-Nagy Gábor