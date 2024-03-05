# Proposta de Projeto

## Tabela de conteúdos

1. [Contexto](#contexto)
    - 1.1. [Tolerância a falhas e mecanismos de resiliência](#tolerância-a-falhas-e-mecanismos-de-resiliência)
    - 1.2. [Bibliotecas como mecanismos de resiliência](#bibliotecas-como-mecanismos-de-resiliência)
    - 1.3. [KMP](#kmp)
    - 1.4. [Ktor](#ktor)
2. [Problema](#problema)
3. [Solução](#solução)
4. [Demonstração](#demonstração)
5. [Potenciais Riscos e Desafios](#potenciais-riscos-e-desafios)
6. [Planeamento](#planeamento)
7. [Notas Adicionais](#notas-adicionais)

## Contexto

### Tolerância a falhas e mecanismos de resiliência

- É a capacidade de um sistema distribuído de continuar a funcionar, mesmo que um ou mais dos seus
  componentes/serviços falhem;
- De forma a responder a esta necessidade, estes sistemas adotam mecanismos de resiliência. Seguem-se alguns exemplos:
    - **Retry**: Consiste em tentar novamente uma operação que falhou, aumentando a probabilidade de sucesso;
    - **Rate Limiter**: Consiste em limitar a taxa de requisições que um serviço pode receber;
    - **Circuit Breaker**: Consiste em interromper, temporariamente, a comunicação com um serviço que está a falhar, de
      forma a evitar que o mesmo sobrecarregue o sistema;
    - **Fallback**: Consiste em fornecer um valor ou executar uma ação alternativa, caso uma operação falhe.

### Bibliotecas como mecanismos de resiliência

- Fornecem mecanismos para lidar com as eventuais falhas em sistemas distribuídos e garantir a
  disponibilidade e confiabilidade dos serviços que disponibilizam;
- Exemplos de bibliotecas de resiliência no mercado:

  | Biblioteca                                              | Linguagem   | Plataforma |
  |---------------------------------------------------------|-------------|------------|
  | Netflix's [Hystrix](https://github.com/Netflix/Hystrix) | Java        | JVM        |
  | [Resilience4j](https://resilience4j.readme.io/docs)     | Java/Kotlin | JVM        |
  | [Polly](https://github.com/App-vNext/Polly)             | C#          | .NET       |

### KMP

- Ao contrário de código em Kotlin que compila para a JVM, ficando o mesmo intrinsecamente comprometido com esta
  plataforma, o
  KMP (Kotlin Multiplatform) permite que o código base e lógico da aplicação, independente da plataforma, seja
  compartilhado entre as mesmas;
- Para cada plataforma alvo, regularmente denominado como _target_, é necessário fornecer implementações específicas
  para as mesmas; Exemplos de targets:
    - JVM;
    - JS,
    - Android;
    - iOS;
    - Linux
- O seu objetivo principal é maximizar a reutilização de código, delegando apenas para código específico de cada plataforma aquilo que não é possível implementar em código de base comum (expect/actual pattern). Dessa forma, é importante que o mesmo seja o mais reduzido possível, visto que terá que ser replicado para cada plataforma suportada.
- Poderá se necessário construir _adapters_ para plataformas que não possuem suporte direto para o KMP, como por exemplo, JS, já Android e JVM possuem suporte direto.

| ![KMP Architecture](./docs/imgs/kmp-architecture.png) |
|:-----------------------------------------------------:|
|                  Arquitetura do KMP                   |

### Ktor

- É uma framework para KMP desenhada para ajudar na criação de aplicações assíncronas de
  servidor e cliente, que se tornou popular devido à sua simplicidade e facilidade de utilização.
- Utiliza a linguagem _Kotlin_ e o sistema de _Coroutines_, permitindo este último, de forma simplificada, a execução
  assíncrona de código de forma sequencial e sem bloqueio de threads.

## Problema

- Não existem bibliotecas de resiliência em Kotlin que sejam multiplataforma, comprometendo o desenvolvimento de
  certas aplicações KMP.
- A biblioteca _Resilience4j_ já providencia
  um [módulo](https://github.com/resilience4j/resilience4j/tree/master/resilience4j-kotlin) para Kotlin, porém, este é
  específico para a JVM.

## Solução

- Construir uma biblioteca que forneça os mencionados mecanismos de resiliência e tolerância a falhas, para o maior
  número
  de plataformas possível, utilizando para esse efeito o KMP.
- Realizar extensões para a framework Ktor, de modo a facilitar a integração da biblioteca com a mesma.

## Demonstração

- A biblioteca será demonstrada através de demonstradores de código, que evidenciarão a sua utilização em diferentes
  plataformas, incluindo a integrada com a framework Ktor e que farão parte da bateria de testes da biblioteca.

## Potenciais Riscos e Desafios

### Desafios

- Primeiro projeto em KMP do arguente.
- Precisar de funcionalidades que não estão na biblioteca standard do Kotlin, mas que são necessárias para a
  implementação da biblioteca.
- Testar a biblioteca em diferentes plataformas.
- Adaptar plataformas ao código comum (aka, falar sobre a arquitetura do KMP), como por exemplo JS -> Adapter from JS Main -> Common Main.
- Integrar a biblioteca com a framework Ktor.

### Riscos

Como designam algo que pode acontecer e que está fora do nosso controlo:
- Bugs do KMP visto que é uma tecnologia recente e em constante evolução.

## Planeamento

| Data       | Tarefa                          |
|------------|---------------------------------|
| 18/03/2024 | Entrega da proposta de projeto  |
| 22/04/2024 | Apresentação de progresso       |
| 03/06/2024 | Entrega da versão beta          |
| 13/07/2024 | Entrega da versão final         |

## Notas Adicionais

- O target iOS será, provavelmente excluído da demonstração, visto que o arguente não possui um dispositivo
  iOS ou outro meio de
  testar a aplicação nessa plataforma.
- A biblioteca será open-source e disponibilizada no GitHub.
