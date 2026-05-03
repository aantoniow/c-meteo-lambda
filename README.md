# c-meteo-lambda
This project is being done for recruitment purposes.
This README.md file is being treated as my informal thoughts and some documentation.


## General information
I'll mainly use java, since I have the most experience with it, despite knowing that in "AWS Lambda" environment it probably doesn't have the best performance (cold start).

I won't be using Spring-Boot, beacuse it would further complicate generated jar and reduce performance, but in pure REST Api model, or some platform I'm accustomed to - I would definitively add it.

I approach this project having in mind all specified Tasks in mind, to reduct time needed for refactoring with each next task.

### Open-Meteo Client
I've implemented this client having in mind that Task #2, needs cities as input parameter and Task #4, suggests usage of other weather providers.
To do City-Latitude/Longitude conversion, I've decided to use open-meteo geolocation api, since it was presented almost at the top of documentation 
(at https://open-meteo.com/en/docs/geocoding-api?name=&count=1#geocoding_search) and it doesn't really complicates that much I think. And will be calling this feature "Geocoding" from now on.

For purpose of this project, Geocoding was included inside Open-Meteo client, but I've considered that it maybe should behave separately - since a lot of other weather APIs use latitude and longitude as their input mechanism. Also results from this client never change actually, so caching mechanism inside would provide very useful.

I started writing this class with using HttpClient (at https://docs.oracle.com/en/java/javase/21/docs/api/java.net.http/java/net/http/HttpClient.html), which is provided by java standard library. But having in mind Task #4 and interface of WeatherClient, it
was fitting that I would extract http calls to separate service, which helps in keeping single responsibility in order. Also it should simplify unit testing.

### AppConfig
This class is for configuration of httpClient and objectMapper.
ObjectMapper is a class and a core component of Jackson library, which I've included inside this application. I configured it here, so no unmapped fields won't
cause exceptions.
HttpClient is configured having in mind short TTL, time to live has duration of couple seconds to not lock application on some networking problem. (Application
in current state is making synchronous http calls, TTL is crucial)

### WeatherService
This class is a very simple one, that delegates what needs to be called without any knowledge on how to make that call. Also it couples category with if
statements, to create expected Response.
I've decided to anticipate WatherRequest instead of simple String, because in the possible future maybe that request could be expanded and I'm most
comfortable with controller-service-repository (or client here) pattern structure.

### Tests
I've written couple unit tests, to check implementation of WeatherService and Open-Meteo client response parsing and preparing answer.
Added dependencies to pom.xml won't be impacting performance or created jar file, because they're in "test" scope - meaning, they only are used and
appear in the testing phase of maven build.

### AWS handler - WeatherHandler
I was worried here about constant calls from aws, that would create newer and newer instances. But from I could find in docs, only handlerRequest() method is called, so if I create instances of classes inside constructor I'm safe with single instances. (at https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html#java-handler-setup)
To build jar file readable by aws lambda, I added dependency to pom.xml specified here:
"https://docs.aws.amazon.com/lambda/latest/dg/java-package.html#java-package-maven".


### Encountered Problems
- First problem was, I've added dependency to the wrong plugin section.
- Second, that I thought Jackson inside implementation was the same one that would be used - That's not true, AWS does deserialisation on their part for the input
Json. Empty constructor and setter fixed the problem.


## Attention!
- In prototype that I'm currently working on, my code won't be executed asynchronously! Potentially in future, another HttpService implementation that would
  meet such expectation.

- I'm using jackson version 2.X, with known CVE problem but since this code works as homework it should be sufficient.

### Open-Meteo usage
It's using parameter, "current=temperature_2m", as it is mentioned in documentation to return only current weather information, "temperature_2m" means "Air temperature at 2 meters above ground" (at https://open-meteo.com/en/docs?latitude=51.1&longitude=17.0333&forecast_days=1&current=temperature_2m#location_and_time)