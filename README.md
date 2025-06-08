# Worldline Test

## Build & Run

```sh
./gradlew bootRun
```

## API

- `GET /api/lists` - List all task lists and tasks
- `POST /api/lists` - Create a new list (`{"name": "List Name"}`)
- `POST /api/lists/{listId}/tasks` - Add a task to a list (`{"name": "Task Name","description": "Task Description"}`)
- `PUT /api/lists/{listId}/tasks/{taskId}` - Update a task (`{"name": "Updated Task Name","description": "Updated Task Description"}`)
- `DELETE /api/lists/{listId}/tasks/{taskId}` - Delete a task
- `DELETE /api/lists/{listId}` - Delete a list
- `POST /api/lists/tasks/move` - Move a task (`{"fromListId":1,"toListId":2,"taskId":3}`)

## Test

```sh
./gradlew test
```