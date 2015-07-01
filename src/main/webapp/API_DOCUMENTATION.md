FORMAT: 1A

# Orders API

API to manage orders

## Orders [/api/orders]
Resource representing collection of orders

+ Attributes (object)
    + orders (array[Order])

### Get orders [GET]
+ Request
    
    + Headers
        
            Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    + Attribute(Orders)

### Create "virtual" order [POST]
+ Request
    + Headers
        
            Accept: application/json

+ Response 204
    
    + Headers
            
            Location: /context/api/orders/12346_newvirtualorderid


## Order [/api/orders/{id}]
Resource representing an order

+ Parameters
    + id : 999992_bd84fe278c6152c821a80ee6038effe6 (string, required)

+ Attributes (object)
    + id: bd84fe278c6152c821a80ee6038effe6 (string)
    + price: 2.77 (number)
    + vouncher: ACR0347Z (string) 
    + status: 1 (number) - Order status. It must be 1 (CREATED), 2 (FINISHED) or 3 (CANCELED)


### Get existing order [GET]
+ Request
    
    + Headers
        
            Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    + Attributes(Order)

### Get unexisting order [GET]
+ Parameters
    + id : 00000_bd84fe278c6152c821a80ee6038effe6 (string, required)

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
    
    + Attributes(Order)

### Update invalid order id [PUT]
+ Parameters
    + id : 0098789_ff31 (string, required) 

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
Resource representing a collection of users

+ Attributes
    + users(array[User])

### Get all users [GET]
+ Request
    + Headers
        
            Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    + Attributes(Users)

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
Resource representing an user

+ Attributes (object)
    + id (number)
    + age (number)
    + name (string)

+ Parameters
    + id : 5000 (number, required)

### Get existing user [GET]
+ Request
    + Headers
        
            Accept: application/json

+ Response 200 (application/json;charset=UTF-8) 
    
    + Attributes(User)

### Get unexisting user [GET]
+ Parameters
    + id : 0 (number, required)

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
   