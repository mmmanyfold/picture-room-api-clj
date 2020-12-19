# `picture-room-api-clj`

> A port of Picture Room API Project to Clojure


### Project Layout

```
{root}

src
└── clj
    └── api
        ├── bigcommerce.clj # functions for interacting with big-commerce API
        ├── core.clj        # app's main entry and middleware configurations
        ├── cron.clj        # routine-like job setup for syncing products to DB
        ├── db.clj          # DB config details
        ├── handlers.clj    # handlers that interact with the DB
        ├── jdbc.clj        # support for various SQL <> Clojure data types
        └── routes.clj      # API routes
```

### Requirements

- Java8+
- Clojure
- Docker

### Setup

##### create alias for docker mac
- `sudo ifconfig lo0 alias 10.254.254.254` 

##### or linux
- `sudo apt-get install net-tools -y`
- `sudo ifconfig lo:0 10.254.254.254.254`

### Development

- `make up` # spins up docker-compose
- `make run` # runs code locally (no live-reload yet)
- `make lint`
- `make repl`

### Production

- `make jar`

### Test

- `make integration-test` # runs integration tests

### TODO

- [ ] add live reload
- [ ] add migration support


