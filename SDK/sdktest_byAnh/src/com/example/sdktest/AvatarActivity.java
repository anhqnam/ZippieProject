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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sdktest.api.APIHelper;

public class AvatarActivity extends Activity implements OnClickListener {

    /** The avatarwrapper. */
    private AvatarWrapper avatarwrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        Init();
    }

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
                String MyResult = avatarwrapper.getAvatarFullPath("1873514");
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
                String myResult = avatarwrapper.getThumbnailFilename("1873514");
                ((TextView) findViewById(R.id.tv_avatarResult)).setText(myResult);
                break;
            }
            case R.id.loadAvatarImageBitmap: {
                new LoadAvatarBMtask().execute();
                Log.d("loadAvatarImageBitmap", "loadbitmap");
                break;
            }
            case R.id.loadMyAvatar: {
                new LoadMyAvatarBMtask().execute();
                Log.d("loadMyAvatar", "loadbitmap");
                break;
            }
            case R.id.loadTempThumbnailImageBitmap: {
                new LoadTempThumbnailimageBMtask().execute();
                Log.d("loadTempThumbnailImageBitmap", "loadbitmap");
                break;
            }
            case R.id.loadThumbnailImageBitmap: {
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

    //
    private class DownloadFilesTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String str = "/storage/sdcard0/Download/03_01_ContactsList_02.jpg";
            final String mResult = avatarwrapper.sendAvatar(str);
            return mResult;
        }

        @Override
        protected void onPostExecute(String result) {
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(result);
        }
    }

    //
    private class getAvatartask extends AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Integer getavatar = avatarwrapper.getAvatar("1874827");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Integer result) {
            String MyResult = result + "";
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(MyResult);
        }
    }

    //
    private class getTempThumbnailtask extends
    AsyncTask<String, String, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Integer getavatar = avatarwrapper.getTempThumbnailAvatar("1873514");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Integer result) {
            String MyResult = result + "";
            ((TextView) findViewById(R.id.tv_avatarResult)).setText(MyResult);
        }
    }

    //
    private class LoadAvatarBMtask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper.loadAvatarImageBitmap("1873514");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

    //
    private class LoadMyAvatarBMtask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper.loadMyAvatar();
            return getavatar;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

    //
    private class LoadTempThumbnailimageBMtask extends
    AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper
                    .loadTempThumbnailImageBitmap("1874827");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

    //
    private class LoadThumbnailimageBMtask extends
    AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap getavatar = avatarwrapper
                    .loadThumbnailImageBitmap("1874827");
            return getavatar;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                ((TextView) findViewById(R.id.tv_avatarResult)).setText("null");
            } else {
                ((ImageView) findViewById(R.id.img_avatar))
                .setImageBitmap(result);
            }

        }
    }

}
