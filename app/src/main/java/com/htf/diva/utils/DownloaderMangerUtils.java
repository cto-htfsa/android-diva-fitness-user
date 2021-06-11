package com.htf.diva.utils;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.htf.diva.BuildConfig;
import com.htf.diva.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * HTF
 */
public class DownloaderMangerUtils {
    private static final String TAG = "Download Task";
    private Context context;

    public DownloaderMangerUtils(Context context, String downloadUrl) {
        this.context = context;
        //Start Downloading Task
        //new DownloadingTask().execute(downloadUrl);
        downloadAndOpenFile(context, downloadUrl);
    }

    private void downloadAndOpenFile(final Context context, final String url) {
        // Get filename
          final String filename = url.substring(url.lastIndexOf('/') + 1);
        final File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
        Log.e(TAG, "File Path:" + tempFile);
        if (tempFile.exists()) {
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", tempFile);
            askToOpenFile(context, uri, filename);
            return;
        }

        //If you want to showing progress dialog
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setCancelable(true);
        progress.setTitle(filename);
        progress.setMessage(context.getString(R.string.downloading));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        // Create the download request
        DownloadManager.Request dmRequest = new DownloadManager.Request(Uri.parse(url));
        dmRequest.setTitle(filename);
        dmRequest.setDescription(context.getString(R.string.downloading));
        dmRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        dmRequest.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename);
        dmRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        dmRequest.setAllowedOverMetered(true);
        dmRequest.setAllowedOverRoaming(true);
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!progress.isShowing()) {
                    return;
                }
                context.unregisterReceiver(this);
                progress.dismiss();
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));
                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", tempFile);
                        askToOpenFile(context, uri, filename);
                    }
                }
                c.close();
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        // Enqueue the request
        dm.enqueue(dmRequest);
    }

    private void askToOpenFile(final Context context, final Uri url, final String fileName) {
        new AlertDialog.Builder(context)
                .setTitle(fileName)
                .setMessage(R.string.do_you_want_to_open_file)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openFile(context, url);
                    }
                })
                .show();
    }

    private void openFile(Context context, Uri localUri) {
        String mimeType = "";
        try {
            if (localUri != null)
                mimeType = getMimeType(context, localUri);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(localUri, mimeType);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
            e.printStackTrace();
        }

    }

    private String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (Objects.equals(uri.getScheme(), ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private class DownloadingTask extends AsyncTask<String, String, String> {
        private String fileName;
        private String folder;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        private void showDialog() {
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            // Display File path after downloading
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());
                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;
                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "eyenak/";
                //Create androiddeft folder if it does not exist
                File directory = new File(folder);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));
                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }
    }
}