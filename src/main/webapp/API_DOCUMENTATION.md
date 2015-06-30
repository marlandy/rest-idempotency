FORMAT: 1A

# Orders API

## Orders [/api/orders]

+ Model
    
        {
            "orders": [
                {
                    "id": "1200047_4665ffaee",
                    "price": 1.17,
                    "vouncher": "AFFC091H",
                    "status": 1
                },
                {
                    "id": "1200054_4665cce10",
                    "price": 2.77,
                    "vouncher": "CBAC091H",
                    "status": 3
                }
            ]            
        }

### Get orders [GET]
+ Request
    + Headers
        
        Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    [Orders][]

### Create "virtual" order [POST]
+ Request
    + Headers
        
        Accept: application/json

+ Response 204
    + Headers
        Location: /context/api/orders/12346_newvirtualorderid


## Order [/api/orders/{id}]

+ Parameters
    + id : 999992_bd84fe278c6152c821a80ee6038effe6 (string)

+ Model
    
        {
            "id": "1200047_4665ffacd",
            "price": 1.17,
            "vouncher": "AFFC091H",
            "status": 1
        }

### Get existing order [GET]
+ Request
    + Headers
        
        Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    [Order][]

### Get unexisting order [GET]
+ Parameters
    + id : 00000_bd84fe278c6152c821a80ee6038effe6 (string)

+ Request
    + Headers
        
        Accept: application/json

+ Response 404


### Update order [PUT]
+ Request
    + Headers
        Content-Type: application/json
        Accept: application/json
    
    + Body
        {
            "price" : 33.1,
            "vouncher" : "ACC092",
            "status" : 1
        }

+ Response 200 (application/json;charset=UTF-8) 
    
    [Order][]

### Update invalid order id [PUT]
+ Parameters
    + id : 0098789_ff31 (string) 

+ Request
    + Headers
        Content-Type: application/json
        Accept: application/json
    
    + Body
        {
            "price" : 33.1,
            "vouncher" : "ACC092",
            "status" : 1
        }

+ Response 400 (application/json;charset=UTF-8) 
    {
        "cause": "Order id 0098789_ff31 is invalid"
    }

# Users API

## Users [/api/users]

+ Model
    
        {
            "users": [
                {
                    "id": 1,
                    "age": 17,
                    "name": "Juan"
                },
                {
                    "id": 8,
                    "age": 42,
                    "name": "Pepe"
                }
            ]            
        }

### Get all users [GET]
+ Request
    + Headers
        
        Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    [Users][]

### Create user [POST]
+ Request
    + Headers
        
        Accept: application/json
        Content-Type: application/json

    + Body
        {
            "age" : 25,
            "name" : "David"
        }

+ Response 201 (application/json;charset=UTF-8) 
    + Headers
        Location: /appctx/api/users/123

    + Attributes(User)

## User [/api/users/{id}]    

+ Attributes (object)
        + id (number)
        + age (number)
        + name (string)

+ Parameters
    + id : 5000 (int)

### Get existing user [GET]
+ Request
    + Headers        
        Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    + Attributes(User)

### Get unexisting user [GET]
+ Parameters
    + id : 0 (int)

+ Request
    + Headers        
        Accept: application/json

+ Response 404

### Update existing user [PUT]
+ Request
    + Headers        
        Accept: application/json
        Content-Type: application/json
    + Body
        {
            "age" : 57,
            "name" : "Victor"
        }

+ Response 200 (application/json;charset=UTF-8) 
    
    + Attributes(User)

### Update existing user [PUT]
+ Request
    + Headers        
        Accept: application/json
        Content-Type: application/json
    + Body
        {            
            "name" : "Victor"
        }

+ Response 400 (application/json;charset=UTF-8) 
    {
        "cause": "User age is required"
    }
    