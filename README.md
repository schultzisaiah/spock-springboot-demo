# Spock Demo (with Spring Boot)

This project is a small demo of Spock tests on a Spring Boot app.

[HERE](http://thejavatar.com/testing-with-spock/) is one of my favorite cheat-sheat resources on the internet that I still reference often. It does a great job of outlining pretty much everything that Spock is capable of.

This project demonstrates some of the more simple and most-commonly-used types of tests that I find myself writing from day to day.

Some particular features highlighted in this demo:
- Testing with feature toggles ([Togglz](https://www.togglz.org/), in this case) [↴](#feature-toggling)
- Testing with/out mocks in a Controller/Service/Repo architecture [↴](#using-mockspy)
- Behavior-driven testing (Spies) [↴](#using-mockspy)
    - counting invocation
    - validating call-order
- Multiple test cases in the same test function (ie: "where" table and "Unroll") [↴](#unroll-test-iterations)
- Taking advantage of "private" access [↴](#take-advantage-of-private-access)
- Mocking different types of responses based on different input [↴](#advanced-value-matching)
- Failure details [↴](#detailed-failure-descriptions)


Run using:
```shell script
gradle wrapper test
```

## Some more detailed commentary:
Spock refers to the classes to be run by JUnit as "Specifications" (aka "Spec's"). Public methods defined in these specs are the individual tests that will be picked up by the runner. No `@Test` annotations necessary to signal JUnit - instead, if you don't want the method to be executed by the test-runner, then make it a private method, and it will be ignored.

The tests themselves are usually written using an explicit "given/when/then" structure. The actual execution lifecycle of these blocks within the Spock framework can be a little bit complicated (might be worth doing that research after you've taken some time to get on your feet with the basics), but conceptually it's like this:
- `given:` is used to set up the variables surrounding the test. If you need to put the class-under-test in a certain state, it should be done here. Input variables, for example, can also be defined here.
- `when:` is where the action to be tested happens. Usually, this block is one line of code -- the call to the method-under-test.
- `then:` is where the validations for the test results are performed. Each line of this block should be a boolean function. If the line evaluates to `true`, then that test detail passes. If `false`, then Spock marks the test as failed. 
- BONUS SECTION! `where:` is used to define multiple testing scenarios to be executed by the same test code. See the "@Unroll" section below for more on that!

### Feature toggling
I set us up for the standardized testing of feature toggles by creating the [BaseSpec](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/BaseSpec.groovy) file as an abstract class all of our Specifications will extend. Here is defined the JUnit rule which will signal our feature toggle framework ([Togglz](https://www.togglz.org/)) to enable all feature toggles by default for each test. A helper method for easily enabling/disabling toggles is provided here as well.

Using this to test feature toggle behavior can be found [here](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/service/ThingServiceSpec.groovy#L98-L125).

### Using Mock/Spy
Well done "pure" unit tests are very well mocked out so that only the method under test is code influencing the results of the test. This allows us to create concise tests that are easy to read and maintain, clearly indicate the offending code when tests fail, and run very very fast.

The "mock everything" strategy is employed for the [ThingControllerSpec](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/controller/ThingControllerSpec.groovy) tests.

However, using end-to-end real code (aka "integration" style tests) has its useful place as well. (Ideally, these should be run at a different time in the CI/CD pipeline because they are by nature slower than pure unit tests, however that is not the topic of this demo!) So the [ThingServiceSpec](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/controller/ThingServiceSpec.groovy) was implemented using Spock "Spy" functionality. This instructs Spock to use a fully-implemented version of the class in question, however it also gives us the ability to validate things like:

Make sure the `get` method is invoked once:
```groovy
when:
serviceUnderTest.methodUnderTest()

then:
1 * spyService.get(_)
```

Make sure the `delete` method is called _first_ (a complete example [here](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/service/ThingServiceSpec.groovy#L70-L96)):
```groovy
when:
serviceUnderTest.methodUnderTest()

then:
1 * spyService.delete(thing)

then:
1 * spyService.get(_)
```

### @Unroll test iterations
One of my favorite quality-of-life features offered by Spock is the ability to define a test once, and "unroll" different scenarios using the same exact test.

You can see this used throughout this demo code. Basically test variables can be defined in the "where" clause of the test one one of two ways...

... a table:
```groovy
where:
i | name       | updateDate             | listOfStuff
0 | 'TARDIS'   | now().minusDays(2)     | [new Stuff("sonic screwdriver"), new Stuff("The Doctor"), new Stuff("Bad Wolf")]
1 | 'Holocron' | now().minusYears(2000) | [new Stuff("Jedi"), new Stuff("Sith")]
2 | 'Omega 13' | now().minusYears(10)   | [new Stuff("Never give up!"), new Stuff("Never surrender!")]
```

... or a list:
```groovy
where:
env << ['dev', 'qa', 'prod']
```

Then the @Unroll annotation on the test itself will instruct Spock to run the test separately for each set of values defined. (ie: in the examples above, there are three rows in the table and three elements in the list, so Spock will produce three tests in either case).

Even better, these "where" variables can be used in the test name, too! So this makes for very readable and intuitive test results. For example, this...
```groovy
@Unroll
def 'test using #env'() {
    given:
    // ...

    when:
    // ...

    then:
    // ...

   where:
   env << ['dev', 'qa', 'prod']
}
```
...results in three tests named:
- test using dev
- test using qa
- test using prod

### Take advantage of "private" access!
Another favorite quality-of-life ability that Spock offers is groovy's ability to disregard the "privacy" of variables and methods. This means that we no longer have to make a method public/package-private just so we can test it in isolation! With groovy, we can simply reference the method (or variable), and the framework will happily execute it without complaint.

Another great use case for this is global private variables that are autowired via the `@Value("${some.prop}")` annotation. If we wanted to "mock" that value using pure JUnit, the options we had may not have been completely desireable:
1. Load up a full spring context (so it gets autowired)
    - This isn't always ideal because it can significantly slow down the runtime of the tests. If we don't need the full app context, then why should we have to pay the price of loading it?
2. Add a setter method for the variable(s) for the test code to use
    - This allows us to bypass the spring context-load, so that's a win. But it clutters the code with methods that aren't actually intended for "real" use.
3. Import something like PowerMock
    - A framework like PowerMock sits on top of the java/JUnit that allows for things like accessing private elements. It allows for the "real" code to stay nice and clean, but the tradeoff is it requires some extra know-how for writing tests, and it adds quite a bit of clutter to the test code.

With Spock, we don't need to do anything extra or special! We just "get it for free" with how groovy naturally behaves. [ThingService](/src/main/java/com/heliopolis/p3x972/spock/springboot/demo/service/ThingService.java#L15-L16) has an example of an `@Value` annotation being tested by [ThingServiceSpec](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/service/ThingServiceSpec.groovy#L53-L67). And the [StuffService](/src/main/java/com/heliopolis/p3x972/spock/springboot/demo/service/StuffService.java#L28-L30) has a private "create" method that is being tested in isolation by the [StuffServiceSpec](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/service/StuffServiceSpec.groovy#L15-L24).

I will just note that "technically" this ability is logged as a "bug" by the folks behind groovy. (It has to do with the fact that groovy is made to behave like a runtime-compiled language, not a build-compiled one). So it's not recommend that "real" code takes advantage of this functionality - and because of that, IDE's like IntelliJ will try to discourage you from accessing private variables/methods. However, I just add the annotation `@SuppressWarnings('GroovyAccessibility')` to the top of each Specification class I write. That signals to the IDE that I am _purposefully_ taking advantage of exceeding privacy rights, and all the yellow squiggly lines go away! :)

### Advanced value-matching
In the "given" or "then" blocks, some advanced matching on objects being passed as method variables can be performed! A tangible example is written in the "given" block of [this test](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/controller/ThingControllerSpec.groovy#L55-L77). It mocks the "add" method:
- If the "Thing" object passed to the `add` method has a "name" value of "foobar", then throw an exception.
- Otherwise, simply return a mocked "Thing" object.

I recommend caution with using this. It's very powerful functionality, but it can also result in a test that's difficult to understand or maintain. So be judicious about its use.

### Detailed failure descriptions
Also worth mentioning is the details that Spock provides in the test output when a test case fails. Out of the box, Spock will show the full context of the object(s) involved in the test case! This reduces the necessity to personally have to debug the test itself to take a peek into the state of objects in memeory at the time of failure. Spock will have already printed out what the object values were, and it results in a more actionable failure - which leads to resolving the failure quicker and easier!

Check out the sample gradle test report that's been added to this repo in the [/example-report](/example-report/index.md) package. The report has a handful of sample failures in it that demo the experience.

## Climbing the rest of the learning curve
Practice, practice, practice! The learning curve is a bit steep with Spock -- especially if you're new to groovy syntax in general. However, it's one of those things that "once you get it, you get it". And you'll never want to write tests any other way again!

Lookup that [document I mentioned eariler](http://thejavatar.com/testing-with-spock/) to dig into any of these concepts deeper, or to learn some of the more complex features that Spock has to offer!
