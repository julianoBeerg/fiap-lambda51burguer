# Fiap 51 Burguer

Sistema de pedidos de lanche. Segundo tech challenge do curso de Pós Tech - turma 6SOAT de Software Architecture para aplicar conceitos de clean code e clean architecture.

## 👨‍🔧👩‍🔧 Integrantes
Carlos Jafet - RM 354076 - cjafet07@gmail.com

Guilherme Macedo Moreira - RM 353750 - guilherme.macedomoreira@gmail.com

Isabella Bellinazzi da Silva - RM 354143 - isabellinazzi@hotmail.com

Juliano Silva Nunes - RM 354144 - silva.juliano8130@gmail.com

Thiago Augusto Nery - RM 355063 - doomerbr@gmail.com


## 📁 Acesso ao projeto
Você pode acessar os arquivos do projeto clicando [aqui](https://github.com/GuiMM/fiap-51burguer),

Tambem pode acessar o video da segunda fase do projeto clicando  [aqui](https://www.youtube.com/watch?v=jiOKUzZcc_Y&ab_channel=PosTech-SoftwareArchitectureGrupo51),
 
E aos diagramas de DDD clicando [aqui](https://miro.com/app/board/uXjVKTKDZGE=/).


## 🔧 Após a execução do projeto

Você pode acessar a lista de endpoints ja configurada para importar no postman clicando [aqui](https://github.com/GuiMM/fiap-51burguer/blob/master/FIAP%20-%20Burger%20API.postman_collection.json),

Ou acesse os endpoints pelo Swagger no link [link](http://localhost:8080/swagger-ui/index.html).


## 🔨 Funcionalidades do projeto

    - CRUD Cliente.
                      
    - Identificação do Cliente via CPF.
 
    - CRUD produtos.
                      
    - Buscar produtos por categoria.
                       
    - Fake checkout, apenas enviar os produtos escolhidos para a fila. O checkout é a finalização do pedido.

    - CRUD pedidos.
                     
    - Listar os pedidos.

    - Checkout do pedido que deve receber os produtos solicitados e retornar a identificação do pedido.

    - Consultar status do pagamento do pedido.

    - Webhook recebendo confirmação de pagamento.


## ✔️ Técnicas e tecnologias utilizadas

**Faça uma lista de tecnologias e técnicas utilizadas (a justificativa e descrição são opcionais)**:

- `Aplicação`: Java 22
- `Banco de dados`: My SQL
- `Arquitetura`: Clean Architecture
- `Containerização`: Docker
- `Orquestação`: Kubernets
- `Design de software`: DDD


## 📐 Diagrama de arquitetura

```mermaid
    graph TB
    subgraph Kubernetes Cluster
        subgraph Namespace
            style Namespace fill:#e6e6e6,stroke:#000,stroke-width:2px
            subgraph Deployment-Burguer-app
                style Deployment-Burguer-app fill:#cce5ff,stroke:#007bff,stroke-width:2px
                Pod1[Pod 1]
                Pod2[Pod 2]
                Pod3[Pod 3]
            end
            subgraph Deployment-DB
                style Deployment-DB fill:#cce5ff,stroke:#007bff,stroke-width:2px
                DatabasePod[(Database Pod)]
            end
            AppService[Service burguer-app]
            style AppService fill:#d4edda,stroke:#155724,stroke-width:2px
            HPA[Horizontal Pod Autoscaler]
            style HPA fill:#fff3cd,stroke:#856404,stroke-width:2px
            ConfigMap[ConfigMap]
            style ConfigMap fill:#f8d7da,stroke:#721c24,stroke-width:2px
            DatabaseService[Service DB]
            style DatabaseService fill:#d4edda,stroke:#155724,stroke-width:2px
        end
        MetricsServer[Metrics Server]
        style MetricsServer fill:#e2e3e5,stroke:#383d41,stroke-width:2px
    end

    User[Usuário] -->|HTTP Request| AppService
    AppService --> Pod1
    AppService --> Pod2
    AppService --> Pod3
    HPA --> Deployment-Burguer-app
    HPA --> MetricsServer
    Pod1 --> ConfigMap
    Pod2 --> ConfigMap
    Pod3 --> ConfigMap
    Pod1 -->|SQL Queries| DatabaseService
    Pod2 -->|SQL Queries| DatabaseService
    Pod3 -->|SQL Queries| DatabaseService
    DatabaseService --> DatabasePod
    DatabasePod --> ConfigMap
    Deployment-Burguer-app --> ConfigMap
``` 

## 📐 Diagrama de Sequencia
https://github.com/GuiMM/fiap-51burguer/blob/master/Diagrama%20de%20sequencia.png

## 🛠️ Abrir e rodar o projeto

**Instruções necessárias para abrir e executar o projeto**

Deverá ter instalado:

    - JDK 22
    - Docker(Certifique-se de que as opções de kubernets estejam habilitadas no docker desktop)

Após a instalação executar os comandos ordenados abaixo na pasta raiz da aplicação:

1 . **Aplicar ConfigMap:**

    - kubectl apply -f .\k8s\configmap-burguer-app.yaml


2 . **Aplicar Métricas:**

    - kubectl apply -f .\k8s\metrics.yaml


3 . **Aplicar Deployment do Banco de Dados:**

     - kubectl apply -f .\k8s\deployment-db.yaml


4 . **Aplicar Service do Banco de Dados:**

     - kubectl apply -f .\k8s\service-db.yaml


5 . **Aplicar Deployment da Aplicação:**

     - kubectl apply -f .\k8s\deployment-burguer-app.yaml


6 . **Aplicar Service da Aplicação:**

     - kubectl apply -f .\k8s\service-burguer-app.yaml


7 . **Aplicar Horizontal Pod Autoscaler:**

     - kubectl apply -f .\k8s\hpa-burguer-app.yaml

     
MER - Modelo de entidade relacionamento
https://github.com/GuiMM/fiap-51burguer/pull/12/commits/6e8aa20c961ead8507bafe190a84e0affc7ab932

## 📚 Mais informações do curso
**Pós Tech - Turma 6SOAT**

**Tech challenge 2: Refatoração do projeto seguindo os padrões de clean code e clean architecture e orquestração de containers de forma escalável**

**Tech challenge 3: Refatoração da arquitetura para provisionar o cluster em cloud(AWS) e o banco no RDS.**


## 📄 Licença
Não se aplica.
