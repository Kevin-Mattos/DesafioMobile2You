# Desafio Mobile2You
> Implementação de tela de detalhes app TodoMovies
## Arquitetura
Escolhi a arquitetura MVVM pois é a que estou mais familiarizado.
## Principais Tecnologias
* LiveData
	* Para alterar o layout conforme alterações
* Coroutines
	* Obter dados de maneira assíncrona e atualizar a LiveData conforme a resposta. 
* Mockito e JUnit
	* Testes
* Retrofit
	* Chamadas para a API
* Glide
	* Cache e carregamento de imagens

## Design
Tentei deixar o mais parecido possível com a tela, mas utilizando um padrão mais parecido com o do android, como usar uma appbar colapsável e o voltar padrão. Outros detalhes que deixei diferente foram: o ícone de selecionado nos filmes similares, e o ícone de like, que fica na parte de cima junto ao título no exemplo, optei por deixá-lo centralizado caso o título do filme ocupe mais de uma linha.

![image](https://user-images.githubusercontent.com/60194291/118407545-93063680-b657-11eb-9e97-d2e16767db66.png) vs ![image](https://user-images.githubusercontent.com/60194291/118407566-b204c880-b657-11eb-89ac-9d1197c0cdde.png)
## Idioma
O App buscará os filmes de acordo com o idioma padrão do celular. Porém os resources só suportam pt-br e en-us. Caso seja outro irá exibi-los em inglês.

## Testes
Deixei para fazer os testes depois, o que se mostrou um erro pois tive que fazer alterações no viewModel para poder testar os LiveDatas mockando o repositório. A parte mais díficil foi conseguir executar uma lambda, onde eu altero os liveDatas do viewModel e que recebe um LiveData de resource de erro ou sucesso, passada como parâmetro de uma função do repositório que está mockado.

# Obrigado! :)
