### Business Requirements
Create a backend for simple **News Management** application. Pay attention that your application should be without view layer (UI).

#### The system should expose REST APIs to perform the following operations:

* CRUD operations for News.
* If new tags and(or) Author are passed during creation or modification of a News, then they should be created in the database. (Save news message with author and tags should go as one step in service layer.)
* CRUD operations for Tag.
* CRUD operations for Author.
* Get news. All request parameters are optional and can be used in conjunction.
* Sort news by date or tag or author;
* Search according SearchCriteria (see details in Tools and Implementation Requirements section);
* Add tag/tags for news message;
* Add news author;
* Each news should have only one author;
* Each news may have more than 1 tag;
* Count all news;

#### Technical requirements
##### General Requirements:
* Code should be clean and should not contain any “developer-purpose” constructions.
* Application should be designed and written with respect to OOD and SOLID principles.
* Code should contain valuable comments where appropriate.
* Public APIs should be documented using Javadoc.
* A clear layered structure should be used: responsibilities of each application layer should be defined.
* JSON should be used as a format of client-server communication messages.
* Convenient error and exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side.
* Abstraction should be used to avoid code duplication.
* Database schema should be adjusted as described on image. Create 2 separate db scripts: one for db schema generation and one for loading default data (near 20 items for each table);

##### Tools and Implementation Requirements:
Please note that only GA versions of tools, frameworks, and libraries are allowed.

* JDK version: 8. Use Streams, java.time.*, etc. where it is appropriate.
* Application package root: com.epam.lab
* Database connection pooling: HikariCP.
* Spring JDBC Template should be used for data access.
* Java Code Convention is mandatory. Exception: margin size – 120 chars.
* Build tool: Apache Maven 3.5+. Multi-module project.
* Web server: Apache Tomcat.
* Application container: Spring IoC. Spring Framework +.
* Spring should be configured via Java config (no spring.xml).
* Database: PostgreSQL 9.+ or 10.+
* Testing: JUnit 4.+ or 5.+, Mockito.
* Service layer should be covered with unit tests. Unit tests coverage should be not less than 80%.
* Repository layer should be tested using integration tests with an in-memory embedded database. All operations with news should be covered with integration tests.
* APIs should be demonstrated using Postman tool. Postman collections with APIs should be prepared for the demo.
* For search functionality use SeachCriteria object which may contain author entity and list of tags (will be using for constructing SQL queries in DAO layer);

#### Restrictions

It is forbidden to use:
* Spring Boot,
* Spring Data Repositories,
* JPA,
* Lombok,
* Powermock.

#### Task #4. Create module for accessing the database.

##### Technology stack:

* Application container: Spring IoC (Spring Framework version 5.x).
* Data access: JPA (Hibernate as JPA Provider).
* Bean validation: Hibernate Validator.

##### Tech requirements:

* Create new module for data access to use JPA. Use different Criteria Queries (but be sure to speak and learn theory about other types of queries: Named Queries, Native Queries).
* Enable logging of SQL queries.
* Add transactions support using Spring declarative transactions management. Don't forget to test your transactional methods.
* There is option switching between JPA+Hibernate or Spring JDBC in configuration file.

#### Task #5. CI with Jenkins.

##### Link to project: http://epbyminw7596.minsk.epam.com:8080/news/

##### Jenkins (developer: developer): http://epbyminw7596:8081/job/News/

#####  SonarQube: http://epbyminw7596:9000/dashboard?id=com.epam.lab%3Anews

##### Rest API:

##### News:
* POST: /add - adds news.
```
curl --location --request POST 'http://epbyminw7596.minsk.epam.com:8080/news/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Attention",
    "shortText": "News description",
    "fullText": "News full text",
    "author": {
        "name": "Name",
        "surname": "Surname"
    },
    "tags": [
        {
            "name": "important news"
        }
    ]
}'
```
* GET: /find/id - finds news by ID.
* PUT: /edit - updates news.
```
curl --location --request PUT 'http://epbyminw7596.minsk.epam.com:8080/news/edit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 5,
    "title": "New title",
    "shortText": "New description",
    "fullText": "New text",
    "author": {
        "name": "Danila",
        "surname": "Bobrov"
    },
    "tags": [
        {
            "id": 5,
            "name": "football"
        }
    ]
}'
```
* DELETE: /delete/id - deletes news by ID.
* GET: /findAll - finds the total number of news.
* PUT: /add_tags/id - adds tags for news by news ID.
```
curl --location --request PUT 'http://epbyminw7596.minsk.epam.com:8080/news/add_tags/5' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "id": 4,
        "name": "car"
    },
    {
        "id": 7,
        "name": "hockey"
    }
]'
```
* GET: /search_by - searches news by several parameters.
 Example: http://epbyminw7596.minsk.epam.com:8080/news/search_by?name=igor&surname=bikov&tags=CAR&tags=hockey&orderBy=date&orderBy=author_name&orderBy=author_surname
1. name = author name (Ex. Igor)
2. surname = author surname (Ex. Bikov)
3. tags = tag name (Ex. car, hockey)
4. orderBy = column name for order operation (Ex. date, author_name or author_surname).
##### Author:
* POST: /author/add - adds author.
```
curl --location --request POST 'http://epbyminw7596.minsk.epam.com:8080/news/author/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Mike",
    "surname": "Davidson"
}'
```
* GET: /author/find/id - finds author by ID.
* PUT: /author/edit - updates author.
```
curl --location --request PUT 'http://epbyminw7596.minsk.epam.com:8080/news/author/edit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 54,
    "name": "David",
    "surname": "Davidson"
}'
```
* DELETE: /author/delete/id - deletes author by ID.
##### Tag:
* POST: /tag/add - adds tags.
```
curl --location --request POST 'http://epbyminw7596.minsk.epam.com:8080/news/tag/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Java"
}'
}'
```
* GET: /tag/find/id - finds tag.
* PUT: /tag/edit - updates tag.
```
curl --location --request PUT 'http://epbyminw7596.minsk.epam.com:8080/news/tag/edit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 5,
    "name": "Good news!"
}'
```
* DELETE: /tag/delete/id - deletes tag by ID.

#### Task #8. Security  
  
Application should support user-based authentication. This means a user is stored in a database with some basic information and a password.  
  
##### User Permissions:  
 - Guest
  
1. Read operations for News.  
2. Sign up.  
3. Log in.  
  
* User:  
  
1. All read operations.  
2. All create operations.  
3. All update operations.  
  
* Administrator (can be added only via database call):  
  
 1. All operations.  
  
##### Tools and Implementation Requirements:  
Please note that only GA versions of tools, frameworks, and libraries are allowed.  
  
- Spring Boot.  
- Server should support only stateless user authentication and verify integrity of JWT token.  
- Use OAuth2 as an authorization protocol.  
- OAuth2 scopes should be used to restrict data.  
- Implicit grant and Resource owner credentials grant should be implemented.  
- Implement CSRF protection.  
- APIs should be demonstrated using Postman tool.  
- For demo, prepare Postman collections with APIs.  
  
##### Restrictions:  
It is forbidden to use:  
  
- Spring Data Repositories,,  
- Lombok,  
- Powermock.
