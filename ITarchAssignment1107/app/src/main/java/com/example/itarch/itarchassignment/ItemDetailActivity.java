package com.example.itarch.itarchassignment;

/**
 * Created by b1013043 on 2016/11/03.
 */

import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.content.Intent;

import java.util.Locale;

public class ItemDetailActivity extends Activity implements TextToSpeech.OnInitListener {
    private TextView mTitle;
    private TextView mDescr;
    private TextToSpeech    tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();

        String title = intent.getStringExtra("TITLE");
        mTitle = (TextView) findViewById(R.id.item_detail_title);
        mTitle.setText(title);
        String descr = intent.getStringExtra("DESCRIPTION");
        mDescr = (TextView) findViewById(R.id.item_detail_descr);
        mDescr.setText(descr);

        // TextToSpeechオブジェクトの生成
        tts = new TextToSpeech(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != tts) {
            // TextToSpeechのリソースを解放する
            tts.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (TextToSpeech.SUCCESS == status) {
            Locale locale = Locale.JAPANESE;
            if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(locale);
            } else {
                Log.d("", "Error SetLocale");
            }
        } else {
            Log.d("", "Error Init");
        }

        speechText();
    }

    private void speechText() {
        String string = mDescr.getText().toString();
        if (0 < string.length()) {
            if (tts.isSpeaking()) {
                // 読み上げ中なら止める
                tts.stop();
            }

            // 読み上げ開始
            tts.speak(string, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
