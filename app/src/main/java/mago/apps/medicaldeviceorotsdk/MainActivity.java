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
                Log.w("onState", "onState: " + state + " || msg:" + msg);
                switch (state) {

                    // 아무것도 하지 않은 초기 상태
                    case IDLE: {
                        break;
                    }

                    // 서버에 연결 시 호출 ( 웹 소켓 기반 )
                    case CONNECTED: {
                        break;
                    }

                    // 서버나 웹 소켓쪽에서 에러가 발생했을때 호출
                    case ERROR: {
                        Log.e("onState", "error msg:" + msg);
                        break;
                    }

                    // 서버에 연결이 해제 되었을때 호출
                    case CLOSED: {
                        break;
                    }

                    // 디바이스에서 측정한 의료데이터를 서버에 보내라는 신호
                    case ALLOWED_TRANSMISSION: {
                        sdk.sendMedicalInfo(120, 100); // (Int 수축기 혈압, Int 공복 혈당) 값 입니다.
                        break;
                    }
                }

            }
        });

        sdk.connectServer();
    }
}



