package com.weavebytes.httpfrompost;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.testapptouploadimagewithtwoparameters.R;

/**
 * class that let user to choose file from the device and send it to the server
 */
public class UploadActivity extends AppCompatActivity  implements View.OnClickListener, FileUploadObserver{

    private Button          btnselect;
    private Button          btnupload;
    private Context         context;
    private ImageView       imageSelected;
    private EditText        edtPath;
    private ProgressDialog  progress;

    String path = null;
    String TAG = "UploadActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.upload);
        context = this;
        
        btnselect       = (Button)findViewById(R.id.btnselect);
        btnupload       = (Button)findViewById(R.id.btnupload);
        imageSelected   = (ImageView)findViewById(R.id.imgselected);
        edtPath         = (EditText)findViewById(R.id.edtpath);
        progress        = new ProgressDialog(context);

        Log.d("UserId - "+Config.U_ID ,"Email - "+Config.E_MAIL);
        btnupload.setOnClickListener(this);
        btnselect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // called when select Button Clicked
            case R.id.btnselect:

            new FileChooser(UploadActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(final File file) {
                        path = file.getPath().toString();
                        edtPath.setText(path);
                        Log.d("File Path", "************" + path);
                        if (path.endsWith(".jpg") || path.endsWith(".png")) {
                            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                            Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
                            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                            imageSelected.setImageBitmap(bitmap);
                        }
                    }
                }).showDialog();
                break;

            //called when upload Button Clicked
            case R.id.btnupload:
                if (path!=null  ) {
                    SendPostRequest();

                } else {
                    Config.toast(context, "No File selected!! Please Select a File to upload.");
                }
        }
    }

//method to post request to server
    public void SendPostRequest() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                progress.setMessage("Uploading Data.. wait..");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                File sourceFile = new File(path);
                String charset = "UTF-8";

                if (!sourceFile.isFile()) {
                    progress.dismiss();
                    Log.d("uploadFile", "Source File not exist :" + path);
                    Config.toast(context, "Source File doesn't Exist");
                }
                //int ret = HttpUtils.uploadFile(Config.API_URL, path, UploadActivity.this);

                try {
                    MultiPartUtility multipart = new MultiPartUtility(Config.API_URL, charset);

                    multipart.addHeaderField("User-Agent", "android app");
                  
                    multipart.addFormField("userid", Config.U_ID+"");
                    multipart.addFormField("filetype", Config.FILE_TYPE);

                    multipart.addFilePart("file", sourceFile);

                    List<String> response = multipart.finish();

                    System.out.println("SERVER REPLIED:");

                    String resp = "";
                    for (String line : response) {
                        System.out.println(line);
                        resp = resp + line + "\n";
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                    Toast.makeText(context, "Got exceptinon: " + ex, Toast.LENGTH_LONG);
                }

                progress.dismiss();
                return null;
            }

            @Override
            protected void onPostExecute(Void res) {
                progress.hide();

                Config.toast(context, "File Successfully Uploaded");
                edtPath.setText("");
                imageSelected.setImageBitmap(null);

            }
        }.execute();
        }
        @Override
    public void uploadStatusUpdate(int error, long totalBytesRead, long totalBytes) {
        Log.d(TAG, "====> file upload totalBytesRead . :  " + totalBytesRead);
        Log.d(TAG, "====> file upload totalBytes . . . :  " + totalBytesRead);
        Log.d(TAG, "====> file upload progress . . . . :  " + (float) totalBytesRead / totalBytes);
    }
}//UploadActivity
