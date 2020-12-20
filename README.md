# `picture-room-api-clj`

> A port of Picture Room API Project to Clojure


### Project Layout

```
{root}

dev
└── user.clj            # utilities for repl driven development (make repl)
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

1. `make up` # spins up docker-compose
2. `make run` # runs code locally

#### Additional dev tasks
- `make lint` # lints code via clj-kondo
- `make repl` # spins up rebel-readline

### Production

- `make jar`

### Test

- `make test` # runs integration tests

### TODO

- [ ] add actual error/exception handling (lol)
- [ ] add migration support


