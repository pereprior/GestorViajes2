package comun

import java.io.*
import java.net.InetAddress
import java.net.Socket


/**
 * A wrapper class of Socket which contains
 * methods for sending and receiving messages
 * @author M. L. Liu
 */
class MyStreamSocket {
    private val socket: Socket
    private var input: BufferedReader? = null
    private var output: PrintWriter? = null

    constructor(acceptorHost: InetAddress?, acceptorPort: Int) {
        socket = Socket(acceptorHost, acceptorPort)
        setStreams()
    }

    constructor(socket: Socket) {
        this.socket = socket
        val host = socket.getInetAddress().hostName
        println("Creado socket con host: $host")
        setStreams()
    }

    @Throws(IOException::class)
    private fun setStreams() {
        // get an input stream for reading from the data socket
        val inStream = socket.getInputStream()
        input = BufferedReader(InputStreamReader(inStream))
        val outStream = socket.getOutputStream()
        // create a PrinterWriter object for character-mode output
        output = PrintWriter(OutputStreamWriter(outStream))
    }

    @Throws(IOException::class)
    fun sendMessage(message: String) {
        output!!.print(message + "\n")
        //The ensuing flush method call is necessary for the data to
        // be written to the socket data stream before the
        // socket is closed.
        output!!.flush()
    } // end sendMessage

    @Throws(IOException::class)
    fun receiveMessage(): String {
        // read a line from the data stream
        return input!!.readLine()
    } //end receiveMessage

    @Throws(IOException::class)
    fun close() {
        socket.close()
    }
} //end class
