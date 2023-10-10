package gestor

import org.json.simple.JSONObject
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Viaje : Serializable {
    var codviaje: String? = null
        private set
    var codprop: String? = null
    var origen: String? = null
    var destino: String? = null
    var fecha: String? = null
    var precio: Long = 0
    var numplazas: Long = 0
    var pasajeros: Vector<String>? = null
        private set

    constructor(
        codprop: String?,
        origen: String?,
        destino: String?,
        fecha: String?,
        precio: Long,
        numplazas: Long
    ) : super() {
        this.codprop = codprop
        this.origen = origen
        this.destino = destino
        this.fecha = fecha
        this.precio = precio
        this.numplazas = numplazas
        pasajeros = Vector()
        codviaje = construyeCodviaje()
    }

    constructor(jsonViaje: Any?) : super()

    override fun toString(): String {
        return toJSON().toJSONString()
    }

    /**
     * Devuelve un objeto JSON con los datos del viaje
     *
     * @return	objeto JSON con los datos del Viaje
     */
    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()

        jsonObject["codviaje"] = codviaje
        jsonObject["codprop"] = codprop
        jsonObject["origen"] = origen
        jsonObject["destino"] = destino
        jsonObject["fecha"] = fecha
        jsonObject["precio"] = precio
        jsonObject["numplazas"] = numplazas

        return jsonObject
    }

    fun quedanPlazas(): Boolean {
        return numplazas > 0
    }

    fun finalizado(): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dia = LocalDate.parse(fecha, formatter)
        val hoy = LocalDate.now()
        return dia.isBefore(hoy)
    }

    fun anyadePasajero(pasajero: String): Boolean {
        if (quedanPlazas()) {
            pasajeros!!.add(pasajero)
            numplazas--
            return true
        }
        return false
    }

    fun borraPasajero(pasajero: String): Boolean {
        if (pasajeros!!.contains(pasajero)) {
            pasajeros!!.remove(pasajero)
            numplazas++
            return true
        }
        return false
    }

    private fun construyeCodviaje(): String {
        return codprop!!.substring(0, 1) + origen!!.substring(0, 1) + destino!!.substring(0, 1) + fecha!!.substring(
            0,
            2
        )
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}