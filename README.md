# A Jornada da Conquista

Este é um aplicativo Android que desenvolvi utilizando Kotlin e Jetpack Compose, chamado *"A Jornada da Conquista"*. A ideia do jogo é simular uma jornada rumo a uma conquista pessoal, onde o usuário interage com o aplicativo para avançar por diferentes estágios, representados por imagens.

## Sobre o Projeto

O objetivo do jogo é simples: o usuário deve alcançar uma "conquista" final clicando repetidamente em uma imagem. O número de cliques necessários é gerado de forma aleatória (entre 1 e 50). O usuário também tem a opção de desistir a qualquer momento, podendo iniciar um novo jogo ou simplesmente sair.

## Funcionalidades Implementadas

- *Imagens Representando Estágios da Jornada*:
  - **Imagem Inicial (R.drawable.primeira): Representa o começo da jornada.
  - **Imagem Mediana (R.drawable.segunda): Mostra o progresso intermediário.
  - **Imagem Final (R.drawable.terceira): Indica que a conquista está próxima.
  - **Imagem de Conquista (R.drawable.conquista): Celebra o sucesso alcançado.
  - **Imagem de Desistência (R.drawable.desistir): Aparece se o usuário optar por desistir.

- *Lógica do Jogo*:
  - Um número aleatório (targetClicks) é gerado para definir quantos cliques são necessários para atingir a conquista.
  - O progresso é acompanhado pela variável clicks, que registra o número atual de cliques.
  - As imagens mudam de acordo com o número de cliques feitos pelo usuário (gerenciado pela variável currentImage).
  - Quando o número de cliques atinge o total necessário, o jogo exibe um diálogo de congratulação (showCongratulationDialog).

- *Opção de Desistência*:
  - O usuário pode clicar no botão "Desistir" a qualquer momento, o que ativa a exibição da Imagem de Desistência (showSurrenderImage). Em seguida, o usuário pode decidir se quer jogar novamente (onPlayAgain) ou sair do jogo (onExit).

## Componentes do Projeto

- **MainActivity**: A atividade principal que carrega todo o conteúdo do jogo.
- **GameScreen**: Função composable principal que gerencia o estado do jogo e exibe a interface.
- **CongratulationDialog e SurrenderDialog**: Funções composables usadas para mostrar diálogos de congratulação ou desistência.

## Requisitos Técnicos

- *Linguagem*: Kotlin
- *Framework*: Jetpack Compose
- *Ferramenta de Desenvolvimento*: Android Studio
- *Gerenciamento de Estado*: Uso de mutableStateOf e rememberSaveable para controlar o estado do jogo.

## Como Executar

1. Clone o repositório do projeto:
   git clone https://github.com/luishf2014/JogoMine
2. Abra o projeto no Android Studio.
3. Compile e execute o aplicativo em um emulador ou dispositivo Android.
