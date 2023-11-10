# Smarthack

```
docker pull postgres

docker run -d -p 5555:5432 --name db -e POSTGRES_DB=postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=mysecretpassword postgres:latest
```