## ECP Service Challenge Java
## Descrição
>Projeto que realiza a autenticação, usando challenge-response conforme implementado em [ClientSamlPost](https://github.com/dsubires/SAMLClient4IoT). 
>

### Pré-requisitos

* Git
* Gradle (Para empacotar em arquivo _**.jar**_)

### Gerando arquivo _**.jar**_
1. Faça o download do projeto

2. Acesse o diretório referente ao projeto pelo terminal

3. Altere a configuração do ldap.properties em ./properties/ldap.properties de acordo com seu serviço de diretório

4. Execute o comando abaixo dentro do diretório do projeto:
  ```bash
  $ gradle shadowJar
  ```

  **Obs.:** O arquivo _**.jar**_ será gerado em `./build/libs`

O pacote deve ser instalado como um novo módulo do simpleSAMLphp*.

> * Documentação a parte
