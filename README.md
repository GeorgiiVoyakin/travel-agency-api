# travel-agency-api
REST API for travel agency

## API endpoints

### Cities
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


```HTTP
POST /cities
```
#### Request params
| param     | type     | Description|
|name       | String   | City name  |
#### Response
```json
{
  "id": 3,
  "name": "Tomsk"
}
```
