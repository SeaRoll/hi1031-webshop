CALL mvnw.cmd clean -DskipTests
CALL mvnw.cmd package -DskipTests
ren .\target\ROOT-1.0.war ROOT.war
docker-compose rm -s -f -v
docker-compose up --build -d
docker image prune --force
pause