# HI1031 Laboration 1: Webbshop

### Requirements

1. IntelliJ Ultimate (för att kunna använda Java EE features och tomcat)
2. Apache tomcat v9 (kan skippas ifall docker används)
3. Docker (för att starta upp postgres och servern)

### Bibliotek som används

1. Project lombok
    1. används för att generera kod som getter/setter
2. tomcat-jdbc
    1. används för att supporta connection pooling

### Installation

1. Se till att IntelliJ Ultimate finns på datorn
2. Se till att Docker är nedladdat på datorn
3. Bygg via `./mvnw clean` och `./mvnw package`
4. Ändra namnet på `ROOT-1.0.war` till `ROOT.war`
5. Kör databas och servern via `docker-compose up --build -d`

#### Alternativ (MAC & LINUX)

1. Kör `run.sh` filen