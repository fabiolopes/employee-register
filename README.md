## employee-register

Aplicação exemplo de utilização de crud com JPA, documentada com OpenApi e Swagger.

Para subir a aplicação, executar a classe _com.bione.employeeregister.EmployeeRegisterApplication_.

Dentro da pasta postman, temos uma sugestão de environment e collection para acessar essa aplicação.

A documentação da API se encontra na url {{host}}//swagger-ui/index.html, sendo o valor de host default http://localhost:8080/.

Dentro do diretório test temos testes unitários do service e testes de integração sobre os endpoints
expostos no controller. Para testes, a base H2 será diferente da base comum.