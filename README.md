# Origin Insurance Backend APP
Java backend implementation of https://github.com/OriginFinancial/origin-backend-take-home-assignment code challenge


## Running locally
### Requirements:
- Docker
- Maven

First, go to the root folder of the project and run:

`mvn install`

Now you need to build the docker image:

`docker build -t origin-insurance-app:latest .`

then you can run the application using:

`docker run -p 8080:8080 origin-insurance-app`

URL: http://localhost:8080/api/v1/risk-profiles

POST body example:
```json
{
    "age": 35,
    "dependents": 2,
    "house": {
        "ownership_status": "owned"
    },
    "income": 0,
    "marital_status": "married",
    "risk_questions": [
        0,
        1,
        0
    ],
    "vehicle": {
        "year": 2018
    }
}


