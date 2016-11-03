package com.example.itarch.itarchassignment;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by b1013043 on 2016/11/03.
 */

public class MyService extends Service implements TextToSpeech.OnInitListener
{

    private TextToSpeech tts;
    private static final String TAG = MyService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");

        // TextToSpeechオブジェクトの生成
        tts = new TextToSpeech(this, this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        if (null != tts) {
            // TextToSpeechのリソースを解放する
            tts.shutdown();
        }
    }

    private IServiceMethod.Stub mStub = new IServiceMethod.Stub()
    {
        @Override
        public void CallServiceMethod(String description) throws RemoteException {

                if (0 < description.length()) {
                    if (tts.isSpeaking()) {
                        // 読み上げ中なら止める
                        tts.stop();
                    }

                    // 読み上げ開始
                    tts.speak(description, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
    };

    @Override
    public IBinder onBind(Intent intent) {
            return mStub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean result = super.onUnbind(intent);
        Log.d(TAG, "onUnbind:" + result);
        return result;
    }

    @Override
    public void onInit(int status) {
        if (TextToSpeech.SUCCESS == status) {
            Locale locale = Locale.ENGLISH;
            if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(locale);
            } else {
                Log.d("", "Error SetLocale");
            }
        } else {
            Log.d("", "Error Init");
        }
    }

}
