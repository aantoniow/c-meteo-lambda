# c-meteo-lambda
This project is being done for recruitment purposes.

I'll mainly use java, since I have the most experience with it, despite knowing that in "AWS Lambda" environment it probably doesn't have the best performance (cold start).

I won't be using Spring-Boot, beacuse it would further complicate generated jar and reduce performance, but in pure REST Api model, or some platform easy to get logs from - I would definitively add it.

## Attention!
In prototype that I'm currently working on, my code won't be executed asynchronously.
I'm using jackson version 2.X, with known CVE problem but since this code works as homework it should be sufficient.

### Open-Meteo
It's using parameter, "current=temperature_2m", as it is mentioned in documentation to return only current weather information, temperature_2m means "Air temperature at 2 meters above ground"