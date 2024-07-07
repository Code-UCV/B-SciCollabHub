# B-ValleCode

### Pasos generales:
- Asegúrate que todas las dependencias y configuraciones se resuelvan correctamente en el nuevo entorno (Windows, Based on Linux, Mac OS) con el siguiente comando:

  ```TXT
  mvn clean install
  ```


-  Debes agregar el siguiente modelo dentro de la carpeta resources para la conexión a MySQL:
  
    ```YML
    # Modelo
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
> Descargar la base de datos [MySQL](https://gist.github.com/PineberryCode/f25ebe116b6ad6e0f28cbed79de2d7d8)

- - -

#### ¿Cómo crear un usuario?
- Modificar el *endpoint* `admin/signup` a **permitAll()** para deshabilitar la autorización.
```JAVA
  /*
   * HttpSecurityConfig.java
   */
    authConfig.requestMatchers(HttpMethod.POST, "/admin/signup").permitAll();
```
- Antes de crear un nuevo usuario con el rol **STUDENT**, necesitarás crear un usuario **ADMIN**:
```JSON
{
    "codeAlumni": "5555555555",
    "email": "admin@ucvvirtual.edu.pe",
    "username": "admin",
    "names": "",
    "lastNames": "",
    "password": "123",
    "role": "ADMIN",
    "status": "Habilitado"
}
```
> [!NOTE]
> Realiza una petición `POST` a esta ruta: `http://localhost:8080/admin/signup`.
> Puedes usar Postman para realizar las peticiones correspondientes.

> Configurar la petición:
- `body` > `raw` > `JSON`

> Ejemplo del `JSON` a enviar:
```JSON
{
    "codeAlumni": "7777777777",
    "email": "alumni@ucvvirtual.edu.pe",
    "username": "alum2024",
    "names": "namexample",
    "lastNames": "lastnamexample",
    "password": "123",
    "role": "STUDENT",
    "status": "Habilitado"
}
```
- - -

#### Requisitos
- Maven
- JDK 17
