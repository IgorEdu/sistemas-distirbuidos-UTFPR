
# Projeto Parcial de Sistemas Distribuídos

Entrega parcial referente ao projeto desenvolvido para a disciplina de sistemas distribuídos da UTFPR.

## Informações gerais sobre o projeto:

- Linguagem utilizada: Java;
- Protocolo usado na comunicação: TCP;
- Formato dos dados que serão trocados: JSON;

## Documentação do protocolo

[Regras para troca de mensagens](https://docs.google.com/document/d/1H8Ft8iex18ZRMD5wKLlINDXaRq7vVT6xdFepw-XkGlI/edit?tab=t.0)

[Protocolo de troca de dados](https://docs.google.com/spreadsheets/d/1F66w2yRJ99iRdqlsBVWgpjJD5UbcAAcKtHqqbdS0lRQ/edit?gid=0#gid=0)


## Ferramentas utilizadas

- Jackson (JSON parser);
- Hibernate (ORM);
- MySQL (Banco de dados);

## Orientações para o uso do servidor

- Iniciar o MySQL;
- Criar o database (sistemas-distribuidos)
  ```bash
    mysql -h localhost -u root -p
    ```
  ```bash
  CREATE DATABASE 'sistemas-distribuidos';
    ```
- Alterar o persistence.xml com o usuário e senha do MySQL (src/main/java/resources/META-INF/pesistence.xml -> Alterar os valores de "jakarta.persistence.jdbc.user" e "jakarta.persistence.jdbc.password")
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
                     http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
                 version="2.2">
    
        <persistence-unit name="mysql-sistemas-distribuidos" transaction-type="RESOURCE_LOCAL">
            <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
            <class>com.domain.entities.User</class>
            <properties>
                <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/sistemas-distribuidos?useTimezone=true&amp;serverTimezone=UTC"/>
                <property name="jakarta.persistence.jdbc.user" value="root"/>
                <property name="jakarta.persistence.jdbc.password" value="root"/>
                <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
                <property name="hibernate.hbm2ddl.auto" value="update"/>
                <property name="hibernate.show_sql" value="false"/>
                <property name="hibernate.format_sql" value="true"/>
            </properties>
        </persistence-unit>
    </persistence>
    ```
- Rodar o servidor (src/main/java/com.UTFPR/server/Server)
- Escolher a porta que rodará o servidor no console

## Orientaçõe para o uso do cliente

- Rodar o cliente (src/main/java/com.UTFPR/client/Client)
- Digitar o IP do servidor no console
- Digitar a porta do servidor no console