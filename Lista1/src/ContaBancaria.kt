import Exceptions.BadRequestException

class ContaBancaria(saldo:Float, limite:Float) {
    var saldo:Float = saldo
    var limite:Float = limite


    fun saque(valor:Float):Boolean {
        if(valor > saldo) {
            throw BadRequestException("Valor Invalido")
            return false
        }

        saldo -=valor
        return true

    }
}