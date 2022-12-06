package mago.apps.orot_medication

import android.util.Log
import com.google.gson.Gson
import mago.apps.orot_medication.interfaces.IOrotMedicationSDK
import mago.apps.orot_medication.interfaces.MedicationStateListener
import mago.apps.orot_medication.model.MessageProtocol
import mago.apps.orot_medication.model.State
import okhttp3.*

class OrotMedicationSDK : IOrotMedicationSDK {

    private val url: String = "ws://demo-health-stream.mago52.com/ws/chat"
    private var request: Request? = null
    private var client: OkHttpClient? = null
    private var webSocket: WebSocket? = null
    private var medicationStateListener: MedicationStateListener? = null

    override fun connectServer() {

        if (medicationStateListener == null) {
            Log.e(TAG, "listener not initialization: ${State.ERROR}")
            return
        }

        try {
            request = Request.Builder().url(url).build()
            client = OkHttpClient()
        } catch (e: Exception) {
            medicationStateListener?.onState(State.ERROR, e.message)
            return
        }

        request?.run {
            try {
                webSocket = client!!.newWebSocket(this, object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        super.onOpen(webSocket, response)
                        medicationStateListener?.onState(State.CONNECTED, null)
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        super.onMessage(webSocket, text)

                        Log.w(TAG, "onMessage: $text")
                        try {
                            val receivedMsg: MessageProtocol =
                                Gson().fromJson(text, MessageProtocol::class.java)
                            Log.w(TAG, "onMessage: $receivedMsg")
                        } catch (e: Exception) {
                            Log.e(TAG, "onMessage: ${e.message}")
                        }
                        medicationStateListener?.onState(State.CONNECTED, text)
                    }

                    override fun onFailure(
                        webSocket: WebSocket, t: Throwable, response: Response?
                    ) {
                        super.onFailure(webSocket, t, response)
                        medicationStateListener?.onState(State.ERROR, t.message.toString())
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosed(webSocket, code, reason)
                        medicationStateListener?.onState(State.CLOSED, reason)
                    }
                })
            } catch (e: Exception) {
                medicationStateListener?.onState(State.ERROR, e.message.toString())
            }
        }
    }

    override fun setListener(listener: MedicationStateListener) {
        medicationStateListener = listener
    }

    override fun closeServer() {
        try {
            webSocket?.cancel()
            request = null
            client = null
            webSocket = null
        } catch (e: Exception) {
            medicationStateListener?.onState(State.ERROR, e.message.toString())
        }
    }

    override fun sendMedicalInfo() {
        try {
//            webSocket.send()
        } catch (e: Exception) {
            medicationStateListener?.onState(State.ERROR, e.message.toString())
        }
    }
}