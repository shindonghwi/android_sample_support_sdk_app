package mago.apps.orot_medication.interfaces

import mago.apps.orot_medication.model.State

interface IOrotMedicationSDK {
    /** 오롯코드 서버에 연결 요청을 한다. */
    fun connectServer()

    fun setListener(listener: MedicationStateListener)

    /** 오롯코드 서버에 연결해제 요청을 한다. */
    fun closeServer()

    /** 의료장비에서 측정된 정보를 서버에 보낸다. */
    fun sendMedicalInfo()



}


