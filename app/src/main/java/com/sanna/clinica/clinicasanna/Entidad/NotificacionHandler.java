package com.sanna.clinica.clinicasanna.Entidad;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.sanna.clinica.clinicasanna.R;

import retrofit2.http.GET;

/**
 * Created by scott on 24/06/2018.
 */

public class NotificacionHandler extends ContextWrapper {

    private NotificationManager manager;
    public static  final  String CHANNEL_HIGHT_ID="1";
    public   final  String CHANNEL_HIGH_NAME="HIGH CHANNEL";
    public static  final  String CHANNEL_LOW_ID="2";
    public   final  String CHANNEL_LOW_NAME="LOW CHANNEL";

    public NotificacionHandler(Context context) {
        super(context);
        createChannels();
    }

    public NotificationManager getManager(){
        if(manager==null){
            manager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return  manager;
    }

    private void createChannels(){
        if (Build.VERSION.SDK_INT >= 26){
            //Creating hight Channel
            NotificationChannel hightChannel=new NotificationChannel(CHANNEL_HIGHT_ID,CHANNEL_HIGH_NAME,NotificationManager.IMPORTANCE_HIGH);

            //Extra Config..
            hightChannel.enableLights(true);
            hightChannel.setLightColor(R.color.colorRed);
           // hightChannel.setShowBadge(true);
            hightChannel.enableVibration(true);
            //hightChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            //hightChannel.setSound(defaultSoundUri,null);
            hightChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationChannel lowChannel=new NotificationChannel(CHANNEL_LOW_ID
                    ,CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);

            getManager().createNotificationChannel(hightChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public   Notification.Builder createNotification(String title,String message ,boolean isHighImportance){
        if(Build.VERSION.SDK_INT>=26){
            if(isHighImportance){
                return this.createNotificationWithChannel(title,message,CHANNEL_HIGHT_ID);
            }

            return this.createNotificationWithChannel(title,message,CHANNEL_LOW_ID);

        }
        return this.createNotificationWithoutChannel(title,message);
    }


    private Notification.Builder createNotificationWithChannel(String title,String message ,String channelId){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            return  new Notification.Builder(getApplicationContext(),channelId).setContentTitle(title)
                    .setContentText(message)
                    .setColor(getColor(R.color.colorPrimary))
                    .setSmallIcon(android.R.drawable.bottom_bar)
                    .setAutoCancel(true);
        }
        return  null;
    }


    private Notification.Builder createNotificationWithoutChannel(String title,String message ){

            return  new Notification.Builder(getApplicationContext()).setContentTitle(title)
                    .setContentText(message)

                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setAutoCancel(true);
        }

}
