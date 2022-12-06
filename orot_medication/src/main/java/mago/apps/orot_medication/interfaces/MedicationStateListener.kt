package mago.apps.orot_medication.interfaces

import mago.apps.orot_medication.model.State

interface MedicationStateListener {
    fun onState(state: State, msg: String?)
}