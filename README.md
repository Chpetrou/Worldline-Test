# Worldline Test

A simple REST service for managing task lists and tasks.

## Install
To get started with this project:

```bash
git clone https://github.com/Chpetrou/Worldline-Test.git
cd Worldline-Test
```
Verify that you're located in the root of the project directory to run any of the following commands.

## Build & Run
To build and run the application:

```bash
./gradlew bootRun
```

## Test
To run the tests:

```bash
./gradlew test
```

## API
The following API calls are available:

- `GET /api/lists` - List all task lists and tasks
- `POST /api/lists` - Create a new list (`{"name": "List Name"}`)
- `POST /api/lists/{listId}/tasks` - Add a task to a list (`{"name": "Task Name","description": "Task Description"}`)
- `PUT /api/lists/{listId}/tasks/{taskId}` - Update a task (`{"name": "Updated Task Name","description": "Updated Task Description"}`)
- `DELETE /api/lists/{listId}/tasks/{taskId}` - Delete a task
- `DELETE /api/lists/{listId}` - Delete a list
- `POST /api/lists/tasks/move` - Move a task (`{"fromListId":1,"toListId":2,"taskId":3}`)
