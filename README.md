# REST and idempotency principle sample


# API documentation

## Users

### Data types

#### User

**application/json**

```javascript
{
  "id": 5000,
  "name": "Pepe",
  "age": 54
}
 ```
 
**application/xml**

```xml
<user>
  <id>5000</id>
  <name>5000</Pepe>
  <age>54</age>
</user>
 ```
 
| Field        | Type           | Required | Description  |
| ------------- |:-------------:|:-------------:| -----|
| id      | int | true | User identifier. E.g: 3009 |
| name     | string | true | User name. E.g: Manuel |
| age     | int | true | User age. E.g: 62 |
 
### Resources

#### GET Users

URL: **/api/users**

Method: **GET**

Accept: **application/json**, **application/xml**

#### GET User

URL: **/api/users/{userId}**

Method: **GET**

Accept: **application/json**, **application/xml**

Path param: **userId** = user identifier. E.g: /api/users/5001 

#### Add User

URL: **/api/users**

Method: **POST**

Accept: **application/json**, **application/xml**

Content-Type: **application/json**, **application/xml**

Body: **user** to create (id is not required. See user data type) 

#### Update User

URL: **/api/users/{userId}**

Method: **PUT**

Accept: **application/json**, **application/xml**

Content-Type: **application/json**, **application/xml**

Path param: **userId** = user identifier. E.g: /api/users/5001 

Body: **user** to update (id is not required in body. See user data type) 
 
