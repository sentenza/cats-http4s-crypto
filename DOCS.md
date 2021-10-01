### Domain Driven Design (DDD)

Domain driven design is all about developing a _ubiquitous language_, which is a language that you can use to discuss
your software with stakeholders. The key concept is that language surfaces in your code.

REF: [Domain Driven Design by Eric Evans](https://www.amazon.com/exec/obidos/ASIN/0321125215/domainlanguag-20).

DDD is all about making your code expressive, making sure that how you _talk_ about your software materializes in your
code. One of the best ways to do this is to keep you _domain_ pure.

That is, allow the business concepts and entities to be real things, and keep all the other cruft out. Therefore, HTTP,
JDBC, SQL are not essential to your domain, so you want to _decouple_ those as much as possible. On the other hand it
should be clear, just looking at the domain of the application, that there is some sort of correlation between
a `Company` and its `Employee`.

### Onion (or Hexagonal) Architecture

In concert with DDD, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/)
and [Hexagonal Architecture from Cockburn](https://java-design-patterns.com/patterns/hexagonal/) give us patterns on how
to separate our domain from the ugliness of implementation.

We fit DDD an Onion together via the following mechanisms:

**The domain package**
The domain package constitutes the things inside our domain. It is deliberately free of the ugliness of JDBC, JSON,
HTTP, and the rest. We use `Services` as coarse-grained interfaces to our domain. These typically represent real-world
use cases. It's common to see a 1-on-1 mapping of `Services` to `Endpoints` or HTTP API calls your application surfaces.

Inside of the **domain**, we see a few concepts:

1. `Service` - the coarse grained use cases that work with other domain concepts to realize your use-cases
1. `Repository` - ways to get data into and out of persistent storage.  **Important: Repositories do not have any
   business logic in them, they should not know about the context in which they are used, and should not leak details of
   their implementations into the world**.
1. `models` - things like `Importance`, and `User` are all domain objects. We keep these lean (i.e. free of behavior).
   All the behavior comes via `Validations` and `Services`

Note that `Repository` is a `trait` that is to be implemented elsewhere.

**The infrastructure package**
The infrastructure package is where the ugliness lives. It has HTTP things, JDBC things, and the like.

1. `endpoint` - contains the HTTP endpoints that we surface via **HTTP4S**. You will also typically see JSON things in
   here via **circe**
1. `repository` - contains the JDBC code, implementations of our `Repositories`. We will support 2 implementations, an
   in-memory version as well as a **doobie** version.

**The config package**
The config package could be considered infrastructure, as it has nothing to do with the domain. We use **PureConfig**
to load configuration objects when the application starts up.

### What about dependency injection?

This project uses `classes` for certain things (some would argue this isn't very FP). There are lots of ways to do
dependency injection, including function arguments, implicits, and monad transformers. Using _class constructors_ is
rather OO like.

There is no spring, guice, or other dependency injection / inversion of control (IoC) framework at use here.

### Fitting it all together

The idea with FP in general is to keep your domain pure, and to push the ugliness to the edges (which we achieve in part
via DDD and Hexagonal Architecture). The way the application is bootstrapped is via the `Server` class. Its job is to
make sure that all the parts are configured and available so that our application can actually start up. The `Server`
will

1. Load the configuration using pure config. If the user has not properly configured the app, it will not start
1. Connect to the database. Here, we also run **flyway** migrations to make sure that the database is in good order. If
   the database cannot be connected to, the app will not start
1. Create our `Repositories` and `Services`. This wires together our domain. We do not use any kind of dependency
   injection framework, rather we pass instances where needed using **constructors**
1. Bind to our port and expose our services. If the port is unavailable, the app will not start

### How we deal with effects F[_]

In this application, we use **cats effect IO** as our effect type, and use **cats** for _Monads_ and other FP type
classes and data types. 
