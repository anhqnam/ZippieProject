/*
 * 
 */
package com.example.sdktest;

import unique.packagename.sdkwrapper.avatars.AvatarWrapper;
import unique.packagename.sdkwrapper.avatars.IAvatarsListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sdktest.api.APIHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class AvatarActivity.
 */
public class AvatarActivity extends Activity implements OnClickListener {

    /** The avatarwrapper. */
    private AvatarWrapper avatarwrapper;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        Init();
    }

    /**
     * Inits the.
     */
    private void Init() {
        avatarwrapper = APIHelper.getInstance().getAvatarWrapper();
        // execute button
        findViewById(R.id.addAvatarListener).setOnClickListener(this);
        findViewById(R.id.getAvatar).setOnClickListener(this);
        findViewById(R.id.getAvatarFullPath).setOnClickListener(this);

        findViewById(R.id.getMyAvatar).setOnClickListener(this);
        findViewById(R.id.getTempThumbnailAvatar).setOnClickListener(this);
        findViewById(R.id.getThumbnailFilename).setOnClickListener(this);

        findViewById(R.id.loadAvatarImageBitmap).setOnClickListener(this);
        findViewById(R.id.loadMyAvatar).setOnClickListener(this);
        findViewById(R.id.loadTempThumbnailImageBitmap)
        .setOnClickListener(this);

        findViewById(R.id.loadThumbnailImageBitmap).setOnClickListener(this);
        findViewById(R.id.removeAvatarListener).setOnClickListener(this);
        findViewById(R.id.sendAvatar).setOnClickListener(this);
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addAvatarListener: {
                avatarwrapper.addAvatarListener(new IAvatarsListener() {
                    @Override
                    public void onNewAvatarThumbnailDownloaded(String arg0) {
                        Log.d("addAvatarListener", "onNewAvatarThumbnailDownloaded");
                    }

                    @Override
                    public void onNewAvatarDownloaded(String arg0) {
                        Log.d("addAvatarListener", "onNewAvatarDownloaded");
                    }

                    @Override
                    public void onMyProfileAvatarDownloaded() {
                        Log.d("addAvatarListener", "onMyProfileAvatarDownloaded");
                    }
                });
                break;
            }
            case R.id.getAvatar: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                new getAvatartask().execute();
                break;
            }
            case R.id.getAvatarFullPath: {
                String MyResult = avatarwrapper.getAvatarFullPath("1882919");
                ((TextView) findViewById(R.id.tv_avatarResult)).setText(MyResult);
                break;
            }
            case R.id.getMyAvatar: {
                avatarwrapper.getMyAvatar();
                Log.d("Get My Avatar : ", "result");
                break;
            }
            case R.id.getTempThumbnailAvatar: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                new getTempThumbnailtask().execute();
                break;
            }
            case R.id.getThumbnailFilename: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                new getThumbnailfilenametask().execute();
                break;
            }
            case R.id.loadAvatarImageBitmap: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(null);
                new LoadAvatarBMtask().execute();
                Log.d("loadAvatarImageBitmap", "loadbitmap");
                break;
            }
            case R.id.loadMyAvatar: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(null);
                new LoadMyAvatarBMtask().execute();
                Log.d("loadMyAvatar", "loadbitmap");
                break;
            }
            case R.id.loadTempThumbnailImageBitmap: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(null);
                new LoadTempThumbnailimageBMtask().execute();
                Log.d("loadTempThumbnailImageBitmap", "loadbitmap");
                break;
            }
            case R.id.loadThumbnailImageBitmap: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(null);
                new LoadThumbnailimageBMtask().execute();
                Log.d("loadThumbnailImageBitmap", "loadbitmap");
                break;
            }
            case R.id.removeAvatarListener: {
                avatarwrapper.removeAvatarListener(new IAvatarsListener() {

                    @Override
                    public void onNewAvatarThumbnailDownloaded(String arg0) {
                        Log.d("removeAvatarListener", "onNewAvatarThumbnailDownloaded");
                    }

                    @Override
                    public void onNewAvatarDownloaded(String arg0) {
                        Log.d("removeAvatarListener", "onNewAvatarDownloaded");
                    }

                    @Override
                    public void onMyProfileAvatarDownloaded() {
                        Log.d("removeAvatarListener", "onMyProfileAvatarDownloaded");
                    }
                });
                break;
            }
            case R.id.sendAvatar: {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("");
                new DownloadFilesTask().execute();
                break;
            }
            default:
                break;
        }
    }

    /**
     * The Class getThumbnailfilenametask.
     */
    private class getThumbnailfilenametask extends AsyncTask<String, String, String>{

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(String... params) {
            final String mResult = avatarwrapper.getThumbnailFilename("1874827");
            return mResult;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(result);
        }
    }
    /**
     * The Class DownloadFilesTask.
     */
    private class DownloadFilesTask extends AsyncTask<String, String, String> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(String... params) {
            String str = Environment.getExternalStorageDirectory().getPath() + "/Vippie/avatars/temp/1874827.jpg";
            final String mResult = avatarwrapper.sendAvatar(str);
            return mResult;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(result);
        }
    }

    //
    /**
     * The Class getAvatartask.
     */
    private class getAvatartask extends AsyncTask<String, String, Integer> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Integer doInBackground(String... params) {
            Integer getavatar = avatarwrapper.getAvatar("1882919");
            return getavatar;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Integer result) {
            String MyResult = result + "";
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(MyResult);
        }
    }

    //
    /**
     * The Class getTempThumbnailtask.
     */
    private class getTempThumbnailtask extends
    AsyncTask<String, String, Integer> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Integer doInBackground(String... params) {
            Integer getavatar = avatarwrapper.getTempThumbnailAvatar("1882919");
            return getavatar;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Integer result) {
            String MyResult = result + "";
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(MyResult);
        }
    }


    /**
     * The Class LoadAvatarBMtask.
     */
    private class LoadAvatarBMtask extends AsyncTask<String, String, Bitmap> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper.loadAvatarImageBitmap("1882919");
            return getavatar;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("LoadAvatarBMtask + null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

    //
    /**
     * The Class LoadMyAvatarBMtask.
     */
    private class LoadMyAvatarBMtask extends AsyncTask<String, String, Bitmap> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper.loadMyAvatar();
            return getavatar;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("LoadMyAvatarBMtask + null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

    //
    /**
     * The Class LoadTempThumbnailimageBMtask.
     */
    private class LoadTempThumbnailimageBMtask extends
    AsyncTask<String, String, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper
                    .loadTempThumbnailImageBitmap("1872514");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("LoadTempThumbnailimageBMtask + null");
            } else
            {
                ((ImageView) findViewById(R.id.img_avatar)).setImageBitmap(result);
            }

        }
    }

    //
    /**
     * The Class LoadThumbnailimageBMtask.
     */
    private class LoadThumbnailimageBMtask extends
    AsyncTask<String, String, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params)
        {
            Bitmap getavatar = avatarwrapper
                    .loadThumbnailImageBitmap("1882919");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText(" LoadThumbnailimageBMtask + null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

}
