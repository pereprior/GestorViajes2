import gestor.Viaje
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.text.ParseException
import org.json.simple.parser.JSONParser;

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class GestorViajes {
    private var os: FileWriter? = null
    private var `is`: FileReader? = null
    private var mapa: HashMap<String?, Viaje> = HashMap<String?,Viaje>()
    private val file = File("viajes.json")

    /**
     * Constructor del gestor de viajes
     * Crea o Lee un fichero con datos de prueba
     */
    fun GestorViajes() {
        try {
            if (!file.exists()) {
                // Si no existe el fichero de datos, los genera y escribe
                os = FileWriter(file)
                generaDatos()
                escribeFichero(os!!)
                os!!.close()
            }
            // Si existe el fichero o lo acaba de crear, lo lee y rellena el diccionario con los datos
            `is` = FileReader(file)
            leeFichero(`is`)
            `is`!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Cuando cada cliente cierra su sesion volcamos los datos en el fichero para mantenerlos actualizados
     */
    fun guardaDatos() {
        try {
            os = FileWriter(file)
            escribeFichero(os!!)
            os!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        println("Los datos se han guardado correctamente")
    }

    /**
     * Escribe en el fichero un array JSON con los datos de los viajes guardados en el diccionario
     *
     * @param os stream de escritura asociado al fichero de datos
     */
    private fun escribeFichero(os: FileWriter) {
        val jsonArray = JSONArray()

        for (code in mapa.keys) {
            jsonArray.add(mapa[code])
        }

        try {
            os.write(jsonArray.toString())
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }


    /**
     * Genera los datos iniciales
     */
    private fun generaDatos() {
        var viaje = Viaje("pedro", "Castellón", "Alicante", "28-05-2023", 16, 1)
        mapa[viaje.codviaje] = viaje
        viaje = Viaje("pedro", "Alicante", "Castellón", "29-05-2023", 16, 1)
        mapa[viaje.codviaje] = viaje
        viaje = Viaje("maria", "Madrid", "Valencia", "07-06-2023", 7, 2)
        mapa[viaje.codviaje] = viaje
        viaje = Viaje("carmen", "Sevilla", "Barcelona", "12-08-2023", 64, 1)
        mapa[viaje.codviaje] = viaje
        viaje = Viaje("juan", "Castellón", "Cordoba", "07-11-2023", 39, 3)
        mapa[viaje.codviaje] = viaje
    }

    /**
     * Lee los datos del fichero en formato JSON y los añade al diccionario en memoria
     *
     * @param is stream de lectura de los datos del fichero
     */
    private fun leeFichero(`is`: FileReader?) {
        val parser = JSONParser()
        try {
            // Leemos toda la información del fichero en un array de objetos JSON
            val array: JSONArray = parser.parse(`is`) as JSONArray
            // Rellena los datos del diccionario en memoria a partir del JSONArray
            rellenaDiccionario(array)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    /**
     * Rellena el diccionario a partir de los datos en un JSONArray
     *
     * @param array JSONArray con los datos de los Viajes
     */
    private fun rellenaDiccionario(array: JSONArray) {
        for (i in 0..<array.size) {
            val jObject = array[i] as JSONObject
            val viaje = Viaje(jObject)
            mapa[jObject["codviaje"] as String] = viaje
        }
        // POR IMPLEMENTAR
    }

    /**
     * Devuelve los viajes disponibles con un origen dado
     *
     * @param origen
     * @return JSONArray de viajes con un origen dado. Vacío si no hay viajes disponibles con ese origen
     */
    fun consultaViajes(origen: String?) {

        for (i in 0..mapa.size){
            val index = i.toString()
            val viaje = mapa[index] as Viaje

            println(viaje.toString())
            if (viaje.origen==origen){
                println(viaje.toString())
            }
        }

    }
    /**
     * El cliente codcli reserva el viaje codviaje
     *
     * @param codviaje
     * @param codcli
     * @return JSONObject con la información del viaje. Vacío si no existe o no está disponible
     */
    fun reservaViaje(codviaje: String, codcli: String): JSONObject {
        //POR IMPLEMENTAR
        val prueba = JSONObject()

        val viaje = mapa[codviaje] as Viaje

        if (viaje.quedanPlazas() && !viaje.finalizado()) {
            prueba[codcli] = viaje.anyadePasajero(codcli)
        }

        return prueba
    }
    /**
     * El cliente codcli anula su reserva del viaje codviaje
     *
     * @param codviaje    codigo del viaje a anular
     * @param codcli    codigo del cliente
     * @return JSON del viaje en que se ha anulado la reserva. JSON vacio si no se ha anulado
     */
    fun anulaReserva(codviaje: String, codcli: String): JSONObject {
        //POR IMPLEMENTAR
        val prueba = JSONObject()

        return prueba
    }

    /**
     * Devuelve si una fecha es válida y futura
     * @param fecha
     * @return
     */
    fun es_fecha_valida(fecha: String): Boolean {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            val dia: LocalDate = LocalDate.parse(fecha, formatter)
            val hoy: LocalDate = LocalDate.now()
            dia.isAfter(hoy)
        } catch (e: DateTimeParseException) {
            System.out.println("Fecha invalida: $fecha")
            false
        }
    }
    /**
     * El cliente codcli oferta un Viaje
     * @param codcli
     * @param origen
     * @param destino
     * @param fecha
     * @param precio
     * @param numplazas
     * @return JSONObject con los datos del viaje ofertado
     */
    fun ofertaViaje(codcli: String, origen: String, destino: String, fecha: String, precio: Long, numplazas: Long): JSONObject {
        //POR IMPLEMENTAR
        return JSONObject()
    }

    /**
     * El cliente codcli borra un viaje que ha ofertado
     *
     * @param codviaje    codigo del viaje a borrar
     * @param codcli    codigo del cliente
     * @return JSONObject del viaje borrado. JSON vacio si no se ha borrado
     */
    fun borraViaje(codviaje: String, codcli: String): JSONObject {
        //POR IMPLEMENTAR
        return JSONObject()
    }
}