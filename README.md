# Spock Demo (with Spring Boot)

This project is a small demo of Spock tests on a Spring Boot app.

[HERE](http://thejavatar.com/testing-with-spock/) is one of my favorite cheat-sheat resources on the internet that I still reference often. It does a great job of outlining pretty much everything that Spock is capable of.

This project demonstrates some of the more simple and most-commonly-used types of tests that I find myself writing from day to day.

Some particular features highlighted in this demo:
- Testing with/out mocks in a Controller/Service/Repo architecture
- Testing with feature toggles ([Togglz](https://www.togglz.org/), in this case)
- Multiple test cases in the same Spec ("where" table)
- Mocking different types of responses based on different input
- Behavior-driven testing (Spies)
    - counting invocation
    - validating call-order
- Taking advantage of "private" access


Run using:
```shell script
gradle wrapper test
```

### Some more detailed commentary:
Spock refers to the classes to be run by JUnit as "Specifications" (aka "Spec's"). Public methods defined in these specs are the individual tests that will be picked up by the runner. No `@Test` annotations necessary to signal JUnit - instead, if you don't want the method to be executed by the test-runner, then make it a private method, and it will be ignored.

The tests themselves are usually written using an explicit "given/when/then" structure. The actual execution lifecycle of these blocks within the Spock framework can be a little bit complicated (might be worth doing that research after you've taken some time to get on your feet with the basics), but conceptually it's like this:
- `given:` is used to set up the variables surrounding the test. If you need to put the class-under-test in a certain state, it should be done here. Input variables, for example, can also be defined here.
- `when:` is where the action to be tested happens. Usually, this block is one line of code -- the call to the method-under-test.
- `then:` is where the validations for the test results are performed. Each line of this block should be a boolean function. If the line evaluates to `true`, then that test detail passes. If `false`, then Spock marks the test as failed. 
- BONUS SECTION! `where:` is used to define multiple testing scenarios to be executed by the same test code. See the "@Unroll" section below for more on that!

#### Feature toggling
I set us up for the standardized testing of feature toggles by creating the BaseSpec.groovy file as an abstract class all of our Specifications will extend. Here is defined the JUnit rule which will signal our feature toggle framework (Togglz) to enable all feature toggles by default for each test. A helper method for easily enabling/disabling toggles is provided here as well.

Using this to test feature toggle behavior can be found [here](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/service/ThingServiceSpec.groovy#L98-L109) and [here](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/service/ThingServiceSpec.groovy#L111-L125)

#### Using Mock/Spy
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

Make sure the `delete` method is called _first_:
```groovy
when:
serviceUnderTest.methodUnderTest()

then:
1 * spyService.delete(thing)

then:
1 * spyService.get(_)
```

#### @Unroll test iterations
One of my favorite quality-of-life features offered by Spock is the ability to define a test once, and "unroll" different scenarios using the same exact test.

You can see this used throughout the project. Basically test variables can be defined in the "where" clause of the test one one of two ways...

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

Then the @Unroll annotation on the test itself will instruct Spock to run the test separately for each set of values defined. (ie: in the examples above, there are three rows in the table and three elements in the list, so Spock will perform three tests).

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

#### Advanced value-matching
In the "given" or "then" blocks, some advanced matching on objects being passed as method variables can be performed! A tangible example is written in the "given" block of [this test](/src/test/groovy/com/heliopolis/p3x972/spock/springboot/demo/controller/ThingControllerSpec.groovy#L55-L77). It mocks the "add" method:
- If the "Thing" object passed to the `add` method has a "name" value of "foobar", then throw an exception.
- Otherwise, simply return a mocked "Thing" object.

I recommend caution with using this. It's very powerful functionality, but it can also result in a test that's difficult to understand or maintain.

#### Climbing the rest of the learning curve
Practice, practice, practice! The learning curve is a bit steep with Spock -- especially if you're new to groovy syntax in general. However, it's one of those things that "once you get it, you get it". And you'll never want to write tests any other way again!

Lookup that [document I mentioned eariler](http://thejavatar.com/testing-with-spock/) to dig into any of these concepts deeper, or to learn some of the more complex features that Spock has to offer!
