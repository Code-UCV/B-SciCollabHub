# B-ValleCode

Debes agregar el siguiente modelo dentro de la carpeta resources para la conexión a MySQL y MongoDB:
```YML
# Modelo
spring:
  data:
    mongodb:
      uri: # Uri proporcionada por MongoDB Atlas
      database: # Nombre de la base de datos
  datasource:
    url: 
    username: root
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: none
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
> Descargar la base de datos [MySQL](https://gist.github.com/PineberryCode/f25ebe116b6ad6e0f28cbed79de2d7d8)
