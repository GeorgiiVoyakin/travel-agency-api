# travel-agency-api
REST API for travel agency

# API endpoints

## Cities
### Get all cities
```HTTP
GET /cities
```
#### Response
```json
[
  {
    "id" : 1,
    "name" : "Moscow"
  },
  {
    "id" : 2,
    "name" : "Tambov"
  }
]
```
### Get city by id
```HTTP
GET /cities/{id}
```
#### Response
```json
{
  "id" : 1,
  "name" : "Moscow"
}
```

### Add new city
```HTTP
POST /cities
```
#### Request params
| Param | Type   | Description |
|-------|--------|-------------|
| name  | String | City name   |
#### Response
```json
{
  "id": 3,
  "name": "Tomsk"
}
```

### Update city
```HTTP
PATCH /cities/{id}
```

To make tours from city to country pass country id in request.
#### Request params
| Param             | Type   | Description |
|-------------------|--------|-------------|
| optionalName      | String | City name   |
| optionalCountryId | Long   | Country id  |
#### Response
```json
{
  "id": 3,
  "name": "Omsk"
}
```

### Delete city by id
```HTTP
DELETE /cities/{id}
```

## Countries
### Get all countries
```HTTP
GET /countries
```
#### Response
```json
[
  {
    "id" : 1,
    "name" : "Russia"
  },
  {
    "id" : 2,
    "name" : "Turkey"
  }
]
```
### Get country by id
```HTTP
GET /countries/{id}
```
#### Response
```json
{
  "id" : 1,
  "name" : "Russia"
}
```

### Add new country
```HTTP
POST /countries
```
#### Request params
| Param | Type   | Description    |
|-------|--------|----------------|
| name  | String | Country name   |
#### Response
```json
{
  "id": 3,
  "name": "Italy"
}
```

### Update country
```HTTP
PATCH /countries/{id}
```

#### Request params
| Param             | Type   | Description |
|-------------------|--------|-------------|
| optionalName      | String | City name   |
| optionalCityId    | Long   | City id     |
| optionalTourId    | Long   | Tour id     |
#### Response
```json
{
  "id": 3,
  "name": "Omsk"
}
```

### Delete country by id
```HTTP
DELETE /countries/{id}
```

## Tours
### Get all tours
```HTTP
GET /tours
```
#### Response
```json
[
  {
    "id" : 1,
    "name" : "tour-name-1",
    "description" : "Description",
    "price" : 3000,
    "distance" : 500,
    "seaResort" : true,
    "beach" : "Sand",
    "wirelessInternet" : true,
    "meal" : true
  },
  {
    "id" : 2,
    "name" : "tour-name-2",
    "description" : "Description",
    "price" : 5000,
    "distance" : null,
    "seaResort" : false,
    "beach" : null,
    "wirelessInternet" : false,
    "meal" : true
  }
]
```
### Get tour by id
```HTTP
GET /tours/{id}
```
#### Response
```json
{
  "id" : 1,
  "name" : "tour-name-1",
  "description" : "Description",
  "price" : 3000,
  "distance" : 500,
  "seaResort" : true,
  "beach" : "Sand",
  "wirelessInternet" : true,
  "meal" : true
}
```

### Add new tour
```HTTP
POST /tours/countries/{country_id}
```

County id - id of country where tour is located.
#### Request params
| Param            | Type    | Description                              |
|------------------|---------|------------------------------------------|
| name             | String  | City name                                |
| description      | String  | Description of the tour                  |
| price            | Long    | Tour price per one day for one person    |
| optionalDistance | Short   | Distance to the sea                      |
| seaResort        | boolean | Shows the tour is next to the sea or not |
| optionalBeach    | String  | Type of the beach                        |
| wirelessInternet | boolean | Tour has wireless internet               |
| meal             | boolean | Tour provides meal                       |
#### Response
```json
{
  "id" : 3,
  "name" : "tour-name-3",
  "description" : "Description",
  "price" : 3000,
  "distance" : 300,
  "seaResort" : true,
  "beach" : "Pebble",
  "wirelessInternet" : true,
  "meal" : true
}
```

### Delete tour by id
```HTTP
DELETE /tours/{id}
```

### Find tours in country by parameters
```HTTP
GET /tours/find/{country_id}
```
Returns list of available tours from city to country with calculated prices.
#### Request params
| Param  | Type | Description      |
|--------|------|------------------|
| cityId | Long | City id          |
| days   | int  | Amount of days   |
| people | int  | Amount of people |
#### Response
```json
[
  {
    "id" : 1,
    "name" : "tour-name-1",
    "description" : "Description",
    "calculatedPrice" : 6000,
    "distance" : 500,
    "seaResort" : true,
    "beach" : "Sand",
    "wirelessInternet" : true,
    "meal" : true
  },
  {
    "id" : 2,
    "name" : "tour-name-2",
    "description" : "Description",
    "calculatedPrice" : 10000,
    "distance" : null,
    "seaResort" : false,
    "beach" : null,
    "wirelessInternet" : false,
    "meal" : true
  }
]
```
