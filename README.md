# RideForce User Service

This service handles the following endpoints (see the API documentation in the gateway service repo for all endpoints and their explanations):

- `/registration-key`
- `/login`
- `/users`
- `/offices`
- `/cars`
- `/contact-info`
- `/roles`
- `/contact-types`

## Configuring the database
Set the following environment variables are used for data that should not be exposed in the public Git repository. 

- `JDBC_URL`: *the database url*
- `JDBC_USERNAME`: *the database username*
- `JDBC_PASSWORD`: *the database password*
- `ACCESS`: *access key* (to access AWS)
- `SECRET`: *secret key* (to access AWS)

Spring Tool Suite can contain its own environment variables. To do this, go to the run menu and select `Run Configurations...`. Look for your project in the left panel and click on the "Environment" tab. Select "New" to create a new environment variable. Click "Apply" and "Close".
![Alt Text](src/main/resources/stsenv5.gif)

For *nix users, modify `.bashrc` (or for whatever shell you use):
```bash
echo export JDBC_URL=someurl JDBC_USERNAME=someusername JDBC_PASSWORD=somepassword >> ~/.bashrc && exec bash 
```
Set `ddl-auto` property to `validate` in the configuration file, located under `src/main/resources/`. For yaml:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```
If the sql script is missing the create statements, change `ddl-auto` to `create` and change it to `validate` once all the script has completed successfully. 

In SQL Developer, click on the file menu and open a new file. Select locate `OfficialDbSQL.sql`. Once it's loaded, run the script by pressing the play button.  

In SQLCL or SQL*Plus: 
```bash
sqlcl $JDBC_USERNAME/$JDBC_PASSWORD@$JDBC_URL/ @OfficialDbSQL.sql
```
