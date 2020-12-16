# `picture-room-api-clj`

> A port of Picture Room API Project to Clojure


### Project Layout

```
{root}

TODO
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

- `make test`

### TODO

- [ ] add live reload


