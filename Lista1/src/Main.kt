import Unit.Funcionario
import Unit.Pessoa

fun main() {
    println("Hello World!")
    // 1. Crie uma função que receba um número inteiro e retorne verdadeiro se o número for par e falso caso contrário.

    println("---------------1----------------")
    val num = 3;
    println(isOdd(num));

    // 2. Crie uma função que receba um array de inteiros e retorne o maior número.

    println("---------------2----------------")
    val nums = listOf(132,1,50,2)
    println(returnBiggestNumber(nums.toTypedArray()))


    // 3. Crie uma classe chamada "Unit.Pessoa" com os atributos "nome" e "idade". Em seguida, crie uma lista de objetos "Unit.Pessoa" e ordene a lista em ordem alfabética pelo atributo "nome".

    println("---------------3----------------")
    val pessoa1 = Pessoa("Bruno",21)
    val pessoa2 = Pessoa("Clebinho", 42)
    val pessoa3 = Pessoa("Alistair", 37)

    val pessoas = mutableListOf(pessoa1,pessoa2,pessoa3)

    pessoas.sortBy { it.nome }
    for(i in 0..pessoas.size-1){
        println(pessoas.get(i).nome)
    }


    // 4. Crie uma função que receba uma string e retorne verdadeiro se a string for um palíndromo (ou seja, pode ser lida da mesma forma de trás para frente).

    println("---------------4----------------")
    isPalindromo("paap")
    isPalindromo("Onibus")

    // 5. Implemente uma função lambda que retorne o maior valor entre dois números.
    println("---------------5----------------")

    val NumeroMaiorLambda = {num1:Int, num2:Int ->
        if(num1>num2){
            "Num: $num1 é maior"
        }
        else{
            "Num: $num2 é maior"
        }
    }

    println(NumeroMaiorLambda(1512,2))

    println("---------------6----------------")
    // 6. Crie uma classe "ContaBancaria" com os atributos "saldo" e "limite". Adicione um método chamado "saque" que recebe um valor como parâmetro e subtrai do saldo da conta. Se o valor do saque for maior que o saldo da conta, o método deve lançar uma exceção com a mensagem "Saldo insuficiente".

    val conta1 = ContaBancaria(1000f,2500f)
    val valorSaque = 030f;
    println("Sua conta possui:")
    println(conta1.saldo)
    println("Sacando: $valorSaque")
    println("...")
    conta1.saque(valorSaque)
    println("Sua conta possui:")
    println(conta1.saldo)

    println("---------------7----------------")
    // 7. Crie uma função que receba uma lista de strings e retorne a string mais longa da lista.

    val listaString = mutableListOf<String>("Jacaré","Medusa","Ônibus","Lua", "Assombração")
    println("lista: $listaString")
    listaString.sortBy {it.length}
    println("String mais longa: ${listaString.last()}")


    // 8. Crie uma classe "Unit.Funcionario" com os atributos "nome", "idade" e "salario". Crie uma função que receba uma lista de funcionários e retorne o funcionário com o maior salário.

    println("---------------8----------------")
    val funcionario1: Funcionario = Funcionario("Jaime",22,1700f)
    val funcionario2: Funcionario = Funcionario("Bruninho", 21, 7500f)
    val funcionario3: Funcionario = Funcionario("Bagriel Aires", 23, 200f)


    val funcionarios= mutableListOf<Funcionario>(funcionario1,funcionario2,funcionario3)
    funcionarios.sortBy { it.salario }
    funcionarios.reverse()
    println("O funcionario com o maior salario é: ${funcionarios.first().nome}, seu salario é : R$${funcionarios.first().salario}")


    // 9. Crie uma função que receba uma lista de números inteiros e retorne uma lista com os números em ordem crescente, sem usar o método de ordenação da linguagem.
    println("---------------9----------------")

    val numerosInteiros = mutableListOf<Int>(7,22,582,12,9,66)
    println(orderList(numerosInteiros))
}



fun isOdd(num:Int):Boolean{
    if(num%2==0){
        return true
    }
    else
    {
        return false;
    }
}

fun returnBiggestNumber(numeros:Array<Int>):Int{
    numeros.sort()
    return numeros.last()
}

fun isPalindromo(palavra:String):Boolean{

    val letras = palavra.get(1)
    val contrario = palavra.toCharArray()
    contrario.reverse()
    if(contrario.contentEquals(palavra.toCharArray())){
        println("$palavra é palindromo:")
    }
    else
        println("$palavra não é Palindromo:")
    println(contrario)
    return false;
}

fun orderList(numToOrder: MutableList<Int>):MutableList<Int>{
    for(i in 0..numToOrder.size){

    }
    return mutableListOf(0)
}