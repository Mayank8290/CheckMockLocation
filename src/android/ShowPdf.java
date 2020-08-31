package com.example.plugin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowPdf extends Activity implements OnLoadCompleteListener {

    private TextView txt; 
    private PDFView pdf;
    String pdfUrl = "https://herocompass.com/assets/technical-leaves/technical-leaves/2020-04-06-18-53-32_VCI-POSTER--2-.pdf";
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pdfUrl = getIntent().getStringExtra("pdflink");
        RelativeLayout relParent = new RelativeLayout(this);
        RelativeLayout.LayoutParams relParentParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relParent.setLayoutParams(relParentParam);
        pdf = new PDFView(this,null);
        RelativeLayout.LayoutParams pdfViewParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        pdf.setLayoutParams(pdfViewParam);
        relParent.addView(pdf);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        setContentView(relParent, relParentParam);

        try{
            new RetrievePdfStream().execute(pdfUrl);
        }
        catch (Exception e){
            Toast.makeText(this, "Failed to load Url :" + e.toString(), Toast.LENGTH_SHORT).show();
        }



        mProgressDialog = new ProgressDialog(ShowPdf.this);
        mProgressDialog.show();
        mProgressDialog.setMessage("Loading Please Wait ...");
        mProgressDialog.setMax(100000);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);





    }

    @Override
    public void loadComplete(int nbPages) {
        mProgressDialog.dismiss();
    }


    class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;

            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdf.fromStream(inputStream).onLoad(ShowPdf.this).load();
        }

    }
}