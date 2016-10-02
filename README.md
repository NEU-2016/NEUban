# NEUban

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

## Building the project:
``mvn clean install``

## Running NEUban locally:
``mvn tomcat7:run``

## Contributors

### Developers

* Erdei Krisztián
* Fekete Attila
* Iványi-Nagy Gábor