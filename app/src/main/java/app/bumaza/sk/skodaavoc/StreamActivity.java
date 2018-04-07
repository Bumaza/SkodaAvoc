package app.bumaza.sk.skodaavoc;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.github.library.bubbleview.BubbleTextView;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import app.bumaza.sk.skodaavoc.utils.AsyncTaskCompleteListener;
import app.bumaza.sk.skodaavoc.utils.StreamSpeechFetcher;

public class StreamActivity extends AppCompatActivity implements AsyncTaskCompleteListener<String> {

    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    BubbleTextView stream_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        stream_tv = findViewById(R.id.text_stream);

        Byte[] array = ArrayUtils.toObject(tobyteArray("daco"));

        for(int i = 0; i < array.length - 22051; i += 21900) {

            Byte[] chunk = Arrays.copyOfRange(array, i, i + 22050);
            new StreamSpeechFetcher(getApplicationContext(), this).execute(chunk);
        }

    }

    @Override
    public void onTaskComplete(String result) {
        stream_tv.setText(result);
        Log.d("CHANGE TEXT", "onTaskComplete: " + result);
    }

    private byte[] tobyteArray(String WAV_FILE){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(getApplicationContext().getResources().openRawResource(R.raw.fam2));

            int read = 100;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0)
            {
                out.write(buff, 0, read);
            }
            out.flush();
            byte[] audioBytes = out.toByteArray();

            return audioBytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
