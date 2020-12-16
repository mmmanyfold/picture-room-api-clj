PHONY: run repl up down jar down db-init lint

run:
	clj -M:run

repl:
	clj -M:repl

jar:
	clj -X:depstar jar :jar picture-room.jar

up:
	docker-compose up -d
	docker-compose logs -f

down:
	docker-compose down

db-init:
	clj -M:migratus init

lint:
	clj -M:clj-kondo --lint src
