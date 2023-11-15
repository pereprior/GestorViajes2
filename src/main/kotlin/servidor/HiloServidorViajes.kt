package servidor

import comun.MyStreamSocket
import gestor.GestorViajes
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.IOException
import java.net.SocketException

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del servicio de viajes.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */
internal class HiloServidorViajes(myDataSocket: MyStreamSocket, unGestor: GestorViajes) : Runnable {
    private val myDataSocket: MyStreamSocket
    private val gestor: GestorViajes

    /**
     * Construye el objeto a ejecutar por la hebra para servir a un cliente
     * @param    myDataSocket    socket stream para comunicarse con el cliente
     * @param    unGestor        gestor de viajes
     */
    init {
        // POR IMPLEMENTAR
        this.myDataSocket = myDataSocket
        gestor = unGestor
    }

    /**
     * Gestiona una sesion con un cliente
     */
    override fun run() {
        var operacion: String? = "0"
        var done = false
        // ...
        try {
            while (!done) {
                // Recibe una petición del cliente
                val peticion: String = myDataSocket.receiveMessage()
                // Extrae la operación y sus parámetros
                val parser = JSONParser()
                val jsonObject = parser.parse(peticion) as JSONObject
                operacion = jsonObject["peticion"] as String?
                println(operacion)
                when (operacion) {
                    "0" -> {
                        gestor.guardaDatos()
                        done = true
                        myDataSocket.close()
                    }

                    "1" -> {
                        // Consulta los viajes con un origen dado
                        val viajes: JSONArray = gestor.consultaViajes(jsonObject["origen"] as String?)
                        println(viajes.toJSONString())
                        myDataSocket.sendMessage(viajes.toJSONString())
                    }

                    "2" -> {}
                    "3" -> {}
                    "4" -> {}
                    "5" -> {}
                }
            } // fin while
        } // fin try
        catch (ex: SocketException) {
            println("Capturada SocketException")
        } catch (ex: IOException) {
            println("Capturada IOException")
        } catch (ex: Exception) {
            println("Exception caught in thread: $ex")
        } // fin catch
    } //fin run
} //fin class
