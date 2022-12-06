package mago.apps.medicaldeviceorotsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mago.apps.orot_medication.OrotMedicationSDK
import mago.apps.orot_medication.interfaces.IOrotMedicationSDK
import mago.apps.orot_medication.model.State

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdk = OrotMedicationSDK()

        sdk.setListener(object : IOrotMedicationSDK.MedicationStateListener {
            override fun onState(state: State, msg: String?) {

            }
        })
        sdk.connectServer()


    }
}