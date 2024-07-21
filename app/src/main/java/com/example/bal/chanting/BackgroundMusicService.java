package com.example.bal.chanting;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicService extends Service {
    private MediaPlayer mediaplayer = new MediaPlayer();
    public BackgroundMusicService() {

    }
    public void onCreate() {
        super.onCreate();
    }
    public BackgroundMusicService(Context context, int FilePath)
    {
        mediaplayer = MediaPlayer.create(context, FilePath);
    }
    public void Play()
    {
        mediaplayer.start();
    }
    public void SetLooping(boolean val)
    {
        mediaplayer.setLooping(val);
    }
    public void Stop()
    {
        mediaplayer.stop();
    }
    public void Pause() {
        mediaplayer.pause();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}