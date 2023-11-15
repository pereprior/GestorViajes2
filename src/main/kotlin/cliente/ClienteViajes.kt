package cliente

import org.json.simple.JSONObject
import java.io.IOException
import java.util.*

object ClienteViajes {
    fun menu(teclado: Scanner): Int {
        var opcion: Int
        println("\n\n")
        println("=====================================================")
        println("============            MENU        =================")
        println("=====================================================")
        println("0. Salir")
        println("1. Consultar viajes con un origen dado")
        println("2. Reservar un viaje")
        println("3. Anular una reserva")
        println("4. Ofertar un viaje")
        println("5. Borrar un viaje")
        do {
            print("\nElige una opcion (0..5): ")
            opcion = teclado.nextInt()
        } while (opcion < 0 || opcion > 5)
        teclado.nextLine() // Elimina retorno de carro del buffer de entrada
        return opcion
    }

    /**
     * Programa principal. Muestra el menú repetidamente y atiende las peticiones del cliente.
     *
     * @param args    no se usan argumentos de entrada al programa principal
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val teclado = Scanner(System.`in`)

        // Crea un gestor de viajes
        var aux: AuxiliarClienteViajes? = null
        aux = try {
            AuxiliarClienteViajes("localhost", "12345")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        var obj: JSONObject?
        print("Introduce tu codigo de cliente: ")
        val codcli = teclado.nextLine()
        var opcion: Int
        do {
            opcion = menu(teclado)
            when (opcion) {
                0 -> {

                    // POR IMPLEMENTAR
                    aux?.cierraSesion()
                    println("Cerrando sesion...")
                }

                1 -> {
                    // Consultar viajes con un origen dado

                    // POR IMPLEMENTAR
                    print("Indica el origen del viaje: ")
                    val origen = teclado.nextLine()
                    val list = aux?.consultaViajes(origen)
                    println(list)
                }

                2 -> {
                    // Reservar un viaje

                    // POR IMPLEMENTAR
                    print("Indica el viaje a reservar: ")
                    val codviaje = teclado.nextLine()
                    obj = aux?.reservaViaje(codviaje, codcli)
                    println(obj)
                }

                3 -> {
                    // Anular una reserva

                    // POR IMPLEMENTAR
                    print("Indica el viaje que quieres anular: ")
                    val codviaje = teclado.nextLine()
                    obj = aux?.anulaReserva(codviaje, codcli)
                    println(obj)
                }

                4 -> {
                    // Ofertar un viaje

                    // POR IMPLEMENTAR
                    print("Dame el origen del viaje: ")
                    val origen = teclado.nextLine()
                    print("Dame el destino del viaje: ")
                    val destino = teclado.nextLine()
                    print("Dime la fecha del viaje(dd-MM-yyyy): ")
                    val fecha = teclado.nextLine()
                    print("Dime el precio del viaje: ")
                    val precio = teclado.nextLong()
                    print("Dime el numero de plazas disponibles: ")
                    val plazas = teclado.nextLong()
                    obj = aux?.ofertaViaje(codcli, origen, destino, fecha, precio, plazas)
                    println(obj)
                }

                5 -> {
                    // Borrar un viaje ofertado

                    // POR IMPLEMENTAR
                    print("Indica el viaje que quieres borrar: ")
                    val codviaje = teclado.nextLine()
                    obj = aux?.borraViaje(codviaje, codcli)
                    println(obj)
                }
            }
        } while (opcion != 0)
    } // fin de main
} // fin class
