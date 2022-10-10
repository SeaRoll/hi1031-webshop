# HI1031 Laboration 1: Webbshop
Webshop labben i kursen HI1031 Distribuerade informationssystem. Alla controller methods kan dispatcha JSP filer och även hantera post requests. Filter existerar så att JSP filen inte behöver hålla koll på session variabler ifall de inte existerar. Controller -> Service -> Dao. Dao har rollen att kalla på databas samt att servicen håller koll på transaktioner.

### UML
![Controller drawio](https://user-images.githubusercontent.com/26680151/194854434-abcc0d01-5bb7-4b59-82ea-09e3f20e32b7.png)
![Service drawio](https://user-images.githubusercontent.com/26680151/194854456-ce85e578-5857-440b-9848-61162bbad7a1.png)
![Dao drawio](https://user-images.githubusercontent.com/26680151/194854472-a807cd34-eca4-4982-aa4b-74c5e6326c88.png)
![Domain drawio](https://user-images.githubusercontent.com/26680151/194854483-f80d5c4b-8273-4e52-827e-2b0a7fa4bbdc.png)

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
