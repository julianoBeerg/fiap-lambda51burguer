# Fiap 51 Burguer

Sistema de pedidos de lanche. Segundo tech challenge do curso de Pós Tech - turma 6SOAT de Software Architecture para aplicar conceitos de clean code e clean architecture.

## 👨‍🔧👩‍🔧 Integrantes
Carlos Jafet - RM 354076 - cjafet07@gmail.com

Guilherme Macedo Moreira - RM 353750 - guilherme.macedomoreira@gmail.com

Isabella Bellinazzi da Silva - RM 354143 - isabellinazzi@hotmail.com

Juliano Silva Nunes - RM 354144 - silva.juliano8130@gmail.com

Thiago Augusto Nery - RM 355063 - doomerbr@gmail.com


# FIAP 51Burger - Projeto

## 📁 Acesso ao Projeto

### Repositórios no GitHub

- **Infraestrutura Kubernetes com Terraform:**  
  [fiap-k8s51burguer](https://github.com/GuiMM/fiap-k8s51burguer)

- **Infraestrutura de Banco de Dados Gerenciáveis com Terraform:**  
  [fiap-db51burguer](https://github.com/GuiMM/fiap-db51burguer)

- **Aplicação Executada no Kubernetes:**  
  [fiap-51burguer](https://github.com/GuiMM/fiap-51burguer)

- **Funções Lambda:**  
  [fiap-lambda51burguer](https://github.com/julianoBeerg/fiap-lambda51burguer)

### Recursos Adicionais

- **Collection para Importar no Postman:**  
  [FIAP - Burger API.postman_collection.json](https://github.com/GuiMM/fiap-51burguer/blob/master/FIAP%20-%20Burger%20API.postman_collection.json)

- **Vídeo da Segunda Fase do Projeto:**  
  [YouTube - PosTech Software Architecture Grupo 51](https://www.youtube.com/watch?v=jiOKUzZcc_Y&ab_channel=PosTech-SoftwareArchitectureGrupo51)

- **Vídeo da Terceira Fase do Projeto:**  
  [YouTube - PosTech Software Architecture Grupo 51](https://www.youtube.com/watch?v=jiOKUzZcc_Y&ab_channel=PosTech-SoftwareArchitectureGrupo51)

- **PDF com Vídeo, Diagramas e Modelagens:**  
  [Documento Google](https://docs.google.com/document/d/1Ay-OWOHbjec_wPjQI0ntPJny1N1lfZJFQqEHw97hONQ/edit?usp=drive_link)

- **PDF com Repositórios e Collection:**  
  [Documento Google](https://docs.google.com/document/d/1B933OMeg6z2DDZ-wWG-_dW9d0Q6TT2UhvOLmSbwQLnw/edit)
  

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
- `Banco de dados`: Postgres
- `Arquitetura`: Clean Architecture
- `Containerização`: Docker
- `Orquestação`: Kubernets
- `Design de software`: DDD
- `Nuvem`: AWS


## 📐 Diagramas e documentações
 
- [**Diagramas de Sequência:**](https://github.com/GuiMM/fiap-51burguer/blob/master/Diagrama%20de%20sequencia.png)

- [**Diagrama da arquitetura de autenticação**](https://drive.google.com/file/d/1mVJoEI81gEIqISXHRPgA1j_1fxkFm1ty/view)

- [**Diagrama da arquitetura do Sistema**](https://drive.google.com/file/d/1mVJoEI81gEIqISXHRPgA1j_1fxkFm1ty/view)

- [**MER - Modelo de entidade relacionamento**](https://drive.google.com/file/d/1-e6vfEpsNNS0aMtH_256b9I7Zsa-4o-2/view)

- [**Documentação da Modelagem de dados**](https://drive.google.com/file/d/1FF9qA6Z8XMe3DRzVudoB_-Rd9O4iAeow/view?usp=drive_link)

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


## 📚 Mais informações do curso
**Pós Tech - Turma 6SOAT**

**Tech challenge 2: Refatoração do projeto seguindo os padrões de clean code e clean architecture e orquestração de containers de forma escalável**

**Tech challenge 3: Refatoração da arquitetura para provisionar o cluster em cloud(AWS), banco no RDS e sistema de autenticação com Serveless lambda e cognito.**


## 📄 Licença
Não se aplica.
