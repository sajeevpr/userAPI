# User API Dev Guide
* **Design**
  * There are two entities - User and Account
  * User is uniquely identified using email and account by account type and name
  * UUID has been used for the primary key of User and Account tables. This helps in future sharding of the database if required
  * OneToMany relation established from User to Account and ManyToOne relation established from Account to User
  * AccountTypes are ZIP_PAY or ZIP_MONEY
  * Either H2/MySQL database can be used. 
  * Integration test case can run with either H2/MySQL(test container) based on property
  * docker-compose.yml has been given to prepare and start the DB and application container together
* **User API**
    * **CreateUser**
        * Success Request sample
          ```
          curl -X POST --header "Content-Type: application/json"  http://localhost:8080/user -d 
          '{
             "userName":"sajurrk",
             "email":"sajurrk@gmail.com",
             "monthlyIncome":13000.00,
             "monthlyExpense":12200.00
          }'
          ```
        * Success Response sample
          ```
            {"success":true,"userId":"eff18b1b-cc8a-4e8e-8ce8-ce366680772e"}
          ```
        * Failure response (user exists with email)
           ```
            {"success":false,"errorCode":"102","errorDesc":"User creation failed. User already exists"}
          ```       
        * Failure response (invalid request - incorrect email/empty username, email, monthly income, expense)
           ```
          {"success":false,"errorCode":"101","errorDesc":"Invalid user input while creating user"}
          ```    
    * **ListUsers**
        * Request
          ```
          curl http://localhost:8080/user
          ```
        * Response
          ```
          {
          "success":true,
          "resultList":[
          {
          "id":"6decddd0-cc41-48b7-9f33-da97ccdda71e",
          "userName":"saju_rrk",
          "email":"saju_rrk@yahoo.com",
          "monthlyIncome":10000.00,
          "monthlyExpense":8000.00
          }
          ]
          }

          ``` 
    * **GetUserById**
        * Request
          ```
          curl http://localhost:8080/user/6decddd0-cc41-48b7-9f33-da97ccdda71e
          ```
        * Response
          ```
          {
          "success":true,
          "resultList":[
          {
          "id":"6decddd0-cc41-48b7-9f33-da97ccdda71e",
          "userName":"saju_rrk",
          "email":"saju_rrk@yahoo.com",
          "monthlyIncome":10000.00,
          "monthlyExpense":8000.00
          }
          ]
          }

          ``` 
    * **GetUserByEmail**
        * Request
          ```
          curl http://localhost:8080/user/email/saju_rrk@yahoo.com
          ```
        * Response
          ```
          {
          "success":true,
          "resultList":[
          {
          "id":"6decddd0-cc41-48b7-9f33-da97ccdda71e",
          "userName":"saju_rrk",
          "email":"saju_rrk@yahoo.com",
          "monthlyIncome":10000.00,
          "monthlyExpense":8000.00
          }
          ]
          }

          ``` 
* **Account API**
    * **CreateAccount**
        * Request
          ```
          curl -X POST --header "Content-Type: application/json"  http://localhost:8080/account -d 
          '
          {
          "accountType":"ZIP_MONEY",
          "accountName":"Savings4",
          "currency":"AUD",
          "userId":"580b7653-b2f7-4bdd-bdce-34f4e0343fa4"
          }'
          ```
        * Success Response
           ```
          {"success":true,"accountId":"a0095485-d81c-42fa-8089-4e63769a6ac2"}
          ```     
        * Failure Response (insufficient credit)
           ```
          {"success":false,"errorCode":"400","errorDesc":"CreditCheckFailed - Account cannot be created"}
          ```         
        * Failure Response (invalid userId)
           ```
          {"success":false,"errorCode":"301","errorDesc":"User not found"}
          ```  
    * **ListAccounts**
        * Request
          ```
          curl http://localhost:8080/account
          ```      
        * Response
          ```
          {
          "success":true,
          "resultList":[
          {
          "id":"f8c2647f-8501-45c2-a6ba-a89483a81f61",
          "accountType":"ZIP_MONEY",
          "accountName":"account1",
          "balance":0.00,
          "currency":"AUD",
          "userId":"6decddd0-cc41-48b7-9f33-da97ccdda71e"}
          ]
          }
          ```   
    * **Generic Exception Responses (Technical failures)**
        * Response 1 - DB Write Error
            ```
            {"success":false,"errorCode":"202","errorDesc":"DB Write Error"}
            ```
      * Response 2 - DB Query Error
          ```
          {"success":false,"errorCode":"201","errorDesc":"DB Query Error"}
          ```
      * Response 3 - Internal Error
          ```
          {"success":false,"errorCode":"500","errorDesc":"Internal Server Error"}
          ```
* **Error Codes**
    
    | ErrorCode | ErrorDescription |
    |-----------|------------------|
    | 101       | Invalid user input while creating user             |
    | 102       | User creation failed. User already exists        |
    | 201       | DB Query Error   |
    | 202       | DB Write Error                 |
    | 301       | User not found                 |
    | 400       | CreditCheckFailed - Account cannot be created                 |
    | 500       | Internal Server Error                 |

## Building
`./gradlew build`

`./gradlew installDist`
## Testing
`./gradlew clean test` #integration test run with H2

`./gradlew clean test -Dit.db=mysql` #integration test run with MySQL db test container

## Deploying

`./gradlew bootRun`

This will start the application with H2 as the database

`docker-compose up` 

This will start the MySQL container DB, execute the db scripts, create the application container from source and start it


`docker-compose up -d`

This will start in background

`docker-compose down`

Stopping the containers mentioned in the docker-compose.yml

`docker-compose down --rmi all`

Stopping and removing all containers mentioned in the docker-compose.yml

`docker volume prune`

Remove all unused local volumes. Unused local volumes are those which are not referenced by any containers

`docker system prune`

Remove all unused containers, networks, images (both dangling and unreferenced), and optionally, volumes.

## Additional Information
**Swagger UI** http://localhost:8080/swagger-ui.html

**Health EndPoint** curl http://localhost:8080/actuator/health

**Readiness Endpoint** curl http://localhost:8080/actuator/health/readiness

**Liveness Endpoint** curl http://localhost:8080/actuator/health/liveness 

**JavaDoc Generation** `./gradlew javadoc`
    