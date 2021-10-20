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
