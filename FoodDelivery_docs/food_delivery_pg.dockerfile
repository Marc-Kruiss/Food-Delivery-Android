docker run --name fooddelivery_pg -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=admin -d -p 5432:5432 -v dbiPostgres:/var/lib/postgres postgres:alpine