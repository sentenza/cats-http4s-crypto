# Cats HTTP4S Doobie TODO
A sample microservice written in Scala using Cats, HTTP4S, Doobie that implements a TODO application. This project is also based upon the work that has been done in [Scala Pet Store](https://github.com/pauljamescleary/scala-pet-store) and [todo-http4s-doobie](https://github.com/jaspervz/todo-http4s-doobie/tree/master/src/main/scala) projects.

## Project stack
This project is meant to be implemented using the [TypeLevel stack](https://typelevel.org/) as much as possible. 

* [Cats](https://typelevel.org/cats/) for FP type classes
* [Http4s](http://http4s.org/) to provide a minimal, idiomatic Scala interface for HTTP services
* [Circe](https://circe.github.io/circe/) for JSON serialization
* [Doobie](https://github.com/tpolecat/doobie) to persist data using relational databases like Postgres
* [Pure Config](https://github.com/pureconfig/pureconfig) to handle app configuration
* Tagless Final wherever possible
