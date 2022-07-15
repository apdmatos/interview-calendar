# interview-calendar
This is an API to expose a calendar for interviewers and candidates.
<br/>
The app is running on port 8080

## Get the app up and running
1. start postgresql
`docker-compose up`
2. start the service
`./graldew :bootRun`

## Run unit tests
`./gradlew :test`

## API documentation

### Get calendar availabilities
```
GET /calendars/{role}/{id}/availabilities
```

sample response:
```
{
    id: 1,
    availabilities: [
        {
            id: 1,
            from: 'ISO DATE',
            to: 'ISO DATE',
        }
    ]
}
```

### Create availability
```
POST /calendars/{role}/{id}/availabilities
{
    from: 'ISO DATE',
    to: 'ISO DATE',
}
```
sample response:
```
{
    id: number
    from: 'ISO DATE',
    to: 'ISO DATE',
}
```

### Delete availabilities
```
DELETE /calendars/{role}/{id}/availabilities/{id}
```

### Query interview slots
```
GET /availabilities/{candidate_id}?interviewers={id1},{id2}
```
sample response:
```
[
    {
        from: 'iso date'
        to: 'iso date'
    },
    {
        from: 'iso date'
        to: 'iso date'
    },
]
```

## Considerations

The dates intersection are not being done in the database because
1. We will not have many records per user
2. It's much easier to do it in the code than writing a extra complex database query
3. It's easier to write unit tests
