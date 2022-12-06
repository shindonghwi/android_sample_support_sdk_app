package mago.apps.medicaldeviceorotsdk;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mago.apps.orot_medication.OrotMedicationSDK;
import mago.apps.orot_medication.interfaces.MedicationStateListener;
import mago.apps.orot_medication.model.State;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OrotMedicationSDK sdk = new OrotMedicationSDK();

        sdk.setListener(new MedicationStateListener() {
            @Override
            public void onState(@NonNull State state, @Nullable String msg) {
                Log.w("onState", "onState: " + state + " || msg:" + msg );
                switch (state){
                    case IDLE: {

                        break;
                    }
                    case CONNECTED: {

                        break;
                    }
                    case ERROR:{
                        break;
                    }
                    case CLOSED:{
                        break;
                    }
                    case ALLOWED_TRANSMISSION:{
                        sdk.sendMedicalInfo(120, 100);
                        break;
                    }
                }

            }
        });

        sdk.connectServer();
    }
}



