package com.example.trabalho1_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editUrl;
    private Button btnDownload;
    private ProgressBar pgbProgresso;
    private ImageView imgDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUrl = findViewById(R.id.edtUrl);
        pgbProgresso = findViewById(R.id.pgbProgresso);
        imgDownload = findViewById(R.id.imgDownload);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        btnDownload.setEnabled(false);
        pgbProgresso.setVisibility(View.VISIBLE);
        carregarImagem();
    }


    public void carregarImagem() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(5000);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String url = editUrl.getText().toString();
                                    InputStream in = new URL(url).openStream();
                                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                                    in.close();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imgDownload.setImageBitmap(bitmap);
                                            pgbProgresso.setVisibility(View.INVISIBLE);
                                            imgDownload.setVisibility(View.VISIBLE);
                                            btnDownload.setEnabled(true);
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
    }
}