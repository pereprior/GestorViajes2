package servidor

import comun.MyStreamSocket
import gestor.GestorViajes
import java.io.IOException
import java.net.ServerSocket

/**
 * Este modulo contiene la logica de aplicacion del servidor.
 * Utiliza sockets en modo stream para llevar a cabo la comunicacion entre procesos.
 * Puede servir a varios clientes de modo concurrente lanzando una hebra para atender a cada uno de ellos.
 * Se le puede indicar el puerto del servidor en linea de ordenes.
 */
/**
 * En esta version del servidor, todos los hilos que atienden concurrentemente las sesiones de
 * distintos clientes comparten un mismo gestor de alquileres.
 * Este gestor se pasa como argumento a cada hilo, junto con el socket.
 */
object ServidorViajes {
    private var gestor: GestorViajes? = null // todas las hebras comparten un mismo objeto de esta clase
    @JvmStatic
    fun main(args: Array<String>) {
        var serverPort = 12345 // puerto por defecto
        var myConnectionSocket: ServerSocket? = null
        gestor = GestorViajes() // Crea el gestor que, a su vez, crea/sobreescribe el fichero de viajes
        if (args.size == 1) serverPort = args[0].toInt()
        try {
            // Instancia un socket stream para aceptar conexiones
            myConnectionSocket = ServerSocket(serverPort)
            /**/println("Servidor de la casa de Subastas listo.")
            while (true) {  // Bucle infinito aceptando y sirviendo conexiones
                // Espera una conexion de un cliente
                /**/
                println("Esperando conexion de algun cliente.")
                val myDataSocket = MyStreamSocket(myConnectionSocket.accept())
                /**/println("Conexion aceptada")
                // Arranca una nueva hebra para atender la sesion de un nuevo cliente
                val theThread = Thread(
                    HiloServidorViajes(
                        myDataSocket,
                        gestor!!
                    )
                )
                theThread.start()
                // y pasa a esperar otros clientes.
            } // fin del bucle infinito
        } // fin try
        catch (ex: Exception) {
            ex.printStackTrace()
        } // fin catch
        finally {
            try {
                myConnectionSocket!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            gestor!!.guardaDatos()
        }
    } //fin main
} // fin class
