# B-ValleCode

### Pasos generales:
- Asegúrate que todas las dependencias y configuraciones se resuelvan correctamente en el nuevo entorno (Windows, Based on Linux, Mac OS) con el siguiente comando:

  ```TXT
  mvn clean install
  ```


-  Debes agregar el siguiente modelo dentro de la carpeta resources para la conexión al motor de base de datos **MySQL**:
  
    ```YML
    # Modelo
    # Nombra al archivo de esta manera: application.yml
    spring:
      datasource:
        url: jdbc:mysql//localhost:3306/dbvallecode
        username: root
        password: 
        driver-class-name: com.mysql.cj.jdbc.Driver
      jpa:
        hibernate:
          ddl-auto: none
          naming:
            physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
        show-sql: true
        database-platform: org.hibernate.dialect.MySQLDialect
      mail:
        host: smtp.gmail.com
        username: # Tu correo electrónico
        password: # Contraseña generada por Gmail 
        port: 587
        properties:
          mail:
            smtp:
              starttls:
                enable: true
              auth: true
    cors:
      allowed-methods: "*"
      allowed-headers: "*"
      exposed-headers: "*"
    ```


> [!IMPORTANT]
> Script de la base de datos [MySQL](https://gist.github.com/PineberryCode/f25ebe116b6ad6e0f28cbed79de2d7d8).

- - -

#### Requisitos
> Maven

> JDK 17
