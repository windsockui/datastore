# windsockUI Datastore
This is the datastore for windsockUI.

## Setup

WindsockUI Datastore expects a Postgres database to be running with an empty database that it can access. The database configuration can be found in `/src/main/java/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/wsui-example
spring.datasource.username=postgres
spring.datasource.password=postgres
```

# Usage
There is only one endpoint implemented at the moment, fetching data. Fetching layouts and components will come later. For now...

To fetch data for a page, you request `/data/site/path`. An example would be
`http://localhost:8080/data/www.windsockui.com` (in this case there is no path specified, which returns `/` as the root). This example should work with the one line of test data.


## Issues
None 

