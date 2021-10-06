# Cats HTTP4S Crypto

<a href="https://typelevel.org/cats/"><img src="https://raw.githubusercontent.com/typelevel/cats/c23130d2c2e4a320ba4cde9a7c7895c6f217d305/docs/src/main/resources/microsite/img/cats-badge.svg" height="40px" align="right" alt="Cats friendly" /></a>
[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/sentenza/cats-http4s-crypto/Scala%20CI)](https://github.com/sentenza/cats-http4s-crypto/actions?query=workflow%3A%22Scala+CI%22)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

A sample microservice written in Scala using Cats, HTTP4S, Doobie that implements an application to compute Crypto/Fiat
currency conversion rates.

This project is based upon the work that has been done in:

* [Scala Pet Store](https://github.com/pauljamescleary/scala-pet-store)
* [todo-http4s-doobie](https://github.com/jaspervz/todo-http4s-doobie/tree/master/src/main/scala)

## Project stack

This project is meant to be implemented using the [TypeLevel stack](https://typelevel.org/) as much as possible.

* [Cats](https://typelevel.org/cats/) for FP type classes
* [Http4s](http://http4s.org/) to provide a minimal, idiomatic Scala interface for HTTP services
* [Circe](https://circe.github.io/circe/) for JSON serialization
* [Doobie](https://github.com/tpolecat/doobie) to persist data using relational databases like Postgres
* [Pure Config](https://github.com/pureconfig/pureconfig) to handle app configuration
* [FS2 gRPC]()
* Tagless Final wherever possible

## How to run the project

Please read the
[CoinMarketCap API documentation](https://coinmarketcap.com/api/documentation/v1/#section/Quick-Start-Guide)
to understand how to consume their API. You can either define in application.conf your CoinMarketCap API KEY or set it
as an environment variable. For Linux systems you can simply export it:

```bash
export CMC_API_KEY="<YOUR-KEY-HERE>"
```

or you can even pass it to the binary (JAR) as a TypeLevel config (Java) parameter as `-Dcmc.apikey="<YOUR-KEY-HERE>"`

## Design principles

See [DOCS](/DOCS.md)

## Code of conduct

See the [Code of Conduct](/CODE_OF_CONDUCT.md)

## License

This project is released under the MIT license. See [LICENSE](/LICENSE).
