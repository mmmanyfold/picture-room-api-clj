.PHONY: run repl up down jar down db-init lint test

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

test:
	docker-compose -f ./docker-compose.test.yaml build
	docker-compose -f ./docker-compose.test.yaml up -d && clj -M:test:runner
	docker-compose -f ./docker-compose.test.yaml down
