package app.bumaza.sk.skodaavoc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import app.bumaza.sk.skodaavoc.utils.AsyncTaskCompleteListener;
import app.bumaza.sk.skodaavoc.utils.UploadSpeechFetcher;

public class UploadActivity extends AppCompatActivity implements AsyncTaskCompleteListener<String> {


    private TextView speech_tv;
    private FloatingActionButton emailButton;

    private ImageButton picker, recorder;

    private boolean isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        isRecording = false;

        Byte[] array = ArrayUtils.toObject(tobyteArray("daco"));
        speech_tv = findViewById(R.id.speech_tv);
        speech_tv.setText("JANKO");

        picker = findViewById(R.id.picker_bt);
        recorder = findViewById(R.id.record_bt);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        recorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecording = !isRecording;

                if(isRecording){
                    recorder.setImageDrawable(getResources().getDrawable(R.drawable.red_small));
                }else{
                    recorder.setImageDrawable(getResources().getDrawable(R.drawable.green_small));
                }
            }
        });

//        emailButton = (FloatingActionButton) findViewById(R.id.email_button);
//        emailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"budacjozef98@gmail.com"});
//                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(UploadActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        new UploadSpeechFetcher(getApplicationContext(), this).execute(array);

    }



    private byte[] tobyteArray(String WAV_FILE){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(getResources().openRawResource(R.raw.fam2));

            int read;
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

    @Override
    public void onTaskComplete(String result) {
        speech_tv.setText(result);
        emailButton.setVisibility(View.VISIBLE);
    }
}
