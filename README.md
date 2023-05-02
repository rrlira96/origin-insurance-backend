# Origin Insurance Backend APP
Java backend implementation of https://github.com/OriginFinancial/origin-backend-take-home-assignment code challenge


## Running locally

First you need to build the docker image. Go to the root folder of the project and run:

`docker build -t origin-insurance-app:latest .`

then you can run the application using:

`docker run -p 8080:8080 origin-insurance-app`

URL: http://localhost:8080/api/v1/risk-profiles
