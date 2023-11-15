package cliente

import comun.MyStreamSocket
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.InetAddress


/**
 * Esta clase es un modulo que proporciona la logica de aplicacion
 * para el Cliente del sevicio de viajes usando sockets de tipo stream
 */
class AuxiliarClienteViajes internal constructor(hostName: String?, portNum: String) {
    private val mySocket // Socket de datos para comunicarse con el servidor
            : MyStreamSocket
    var parser: JSONParser

    /**
     * Construye un objeto auxiliar asociado a un cliente del servicio.
     * Crea un socket para conectar con el servidor.
     * @param    hostName    nombre de la maquina que ejecuta el servidor
     * @param    portNum        numero de puerto asociado al servicio en el servidor
     */
    init {

        // IP del servidor
        val serverHost = InetAddress.getByName(hostName)
        // Puerto asociado al servicio en el servidor
        val serverPort = portNum.toInt()
        //Instantiates a stream-mode socket and wait for a connection.
        mySocket = MyStreamSocket(serverHost, serverPort)
        /**/println("Hecha peticion de conexion")
        parser = JSONParser()
    } // end constructor

    /**
     * Devuelve los viajes ofertados desde un origen dado
     *
     * @param origen    origen del viaje ofertado
     * @return array JSON de viajes desde un origen. array vacio si no hay ninguno
     */
    fun consultaViajes(origen: String?): JSONArray? {
        // POR IMPLEMENTAR
        val consulta = JSONObject()
        consulta["peticion"] = 1
        consulta["origen"] = origen
        val respuesta: String
        var array = JSONArray()
        try {
            mySocket.sendMessage(consulta.toJSONString())
            respuesta = mySocket.receiveMessage()
            array = parser.parse(respuesta) as JSONArray
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return array // cambiar por el retorno correcto
    } // end consultaViajes

    /**
     * Un pasajero reserva un viaje
     *
     * @param codviaje        codigo del viaje
     * @param codcliente    codigo del pasajero
     * @return    objeto JSON con los datos del viaje. Vacio si no se ha podido reservar
     */
    fun reservaViaje(codviaje: String?, codcliente: String?): JSONObject? {
        // POR IMPLEMENTAR
        return null // cambiar por el retorno correcto
    } // end reservaViaje

    /**
     * Un pasajero anula una reserva
     *
     * @param codviaje        codigo del viaje
     * @param codcliente    codigo del pasajero
     * @return    objeto JSON con los datos del viaje. Vacio si no se ha podido reservar
     */
    fun anulaReserva(codviaje: String?, codcliente: String?): JSONObject? {
        // POR IMPLEMENTAR
        return null // cambiar por el retorno correcto
    } // end anulaReserva

    /**
     * Un cliente oferta un nuevo viaje
     *
     * @param codprop    codigo del cliente que hace la oferta
     * @param origen    origen del viaje
     * @param destino    destino del viaje
     * @param fecha        fecha del viaje en formato dd/mm/aaaa
     * @param precio    precio por plaza
     * @param numplazas    numero de plazas ofertadas
     * @return    viaje ofertado en formatoJSON. Vacio si no se ha podido ofertar
     */
    fun ofertaViaje(
        codprop: String?,
        origen: String?,
        destino: String?,
        fecha: String?,
        precio: Long,
        numplazas: Long
    ): JSONObject? {
        // POR IMPLEMENTAR
        return null // cambiar por el retorno correcto
    } // end ofertaViaje

    /**
     * Un cliente borra una oferta de viaje
     *
     * @param codviaje        codigo del viaje
     * @param codcliente    codigo del pasajero
     * @return    objeto JSON con los datos del viaje. Vacio si no se ha podido reservar
     */
    fun borraViaje(codviaje: String?, codcliente: String?): JSONObject? {
        // POR IMPLEMENTAR
        return null // cambiar por el retorno correcto
    } // end borraViaje

    /**
     * Finaliza la conexion con el servidor
     */
    fun cierraSesion() {
        // POR IMPLEMENTAR
    } // end done
}