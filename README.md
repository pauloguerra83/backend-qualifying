# qualify-backend


 Tutorial de instalação:

 aplicação em container docker, passos de instalação:

 Necessário Docker na maquina (caso use o windows, recomendo usar o wsl2 )

 Buildar a imagem:

 docker build -t backend-qualifyng:latest .

Rodar a imagem

docker run -d  -p 8080:8080 backend-qualifyng

Para ter certeza que a imagem esteja de pé digitar:

 docker ps ou docker ps -a (para ver tbm as paradas)

caso queira ver o log de subida somente digitar:

"docker images" pegar o {id_image}

docker logs id_image



Frameworks utilizados:


mapStruct - Para implementar a conversão do Hotel para o HotelDTO e seus filhos

Framework que através da interface é criada as implementações de parse, ele utiliza o plugin do maven para gerar a implementação

Lombok - Para a geração de getters, setters, construtores, builders e afins.

Logbok - para a implementação de log de forma agil, sem precisar configurar na mão e definir pattern

Paralelismo:

Utilização de @Async do pacote do spring boot em conjunto com a classe CompletableFuture da do pacote java para a implementação do paralelismo

Cache:

Ehcache - Para a utilização do cache (implementei mas testei e por causa de algum tipo de config, não está guardando no cache)

Documentação:

 Utilizado a implementação do SpringFox para a geracao do swagger swagger

Url de Sandbox para teste
 http://localhost:8080/swagger-ui/index.html#/

 Caso deseje também é só importar a collection do postman que estará no github

 Testes:

 Implementação de RestAssured testando chamadas e retornos 200 e 404 (subindo uma instancia do server toda vez que o mesmo é executado)
 Implementação de teste unitário na classe que faz o calculo da regra de negocio

 para executar somente chamar o goal "mvn clean install" que ele deleta a pasta compila, exucuta os testes e gera um novo artefato no .m2

 Exceções:

 Criado um handler que escuta alguns tipos de exceções (não consegui especializar as exceções da forma mais granular possivel, mas farei numa v2)

 Observabilidade:

 Inclui 2 plugins

 Actuator e Micrometer
 Para que eu pudesse configurar o Prometheus na aplicação e gerar métricas

 Prometheus:

 Para que o mesmo possa coletar as metricar eu configurei os paths necessários.


 http://localhost:8080/actuator - Para coletar todas as metricas num contexto geral

 http://localhost:8080/actuator/prometheus - Para as metricas do prometheus


