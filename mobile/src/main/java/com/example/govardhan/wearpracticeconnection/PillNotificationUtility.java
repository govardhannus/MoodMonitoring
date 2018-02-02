package com.example.govardhan.wearpracticeconnection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;


public class PillNotificationUtility {

	public static final String PILL_CONSUMED = "PillConsumed";
	public static final String PILL_ID = "PILL_ID";
	public static final String QUANTITY = "QUANTITY";


	public static void notificationHighHeartRate(Context context, String header, String detail, String additionalInfo) {
		Intent resultIntent = new Intent(context, MonitorActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);

		PillNotificationUtility.setNotification(header, detail, additionalInfo, stackBuilder, context);
	}

	private static void setNotification(String header, String detail, String additionalInfo, TaskStackBuilder stackBuilder, Context context) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setUsesChronometer(false)
				.setContentTitle(header)
				.setContentText(detail)
				.setSubText(additionalInfo)
				.setAutoCancel(true)
				.setSmallIcon(R.drawable.notification);

		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setPriority(Notification.PRIORITY_HIGH);
		mBuilder.setContentIntent(resultPendingIntent);
		mBuilder.setColor(Color.BLUE);
		mBuilder.setLights(Color.BLUE, 3000, 3000);
		mBuilder.setWhen(0);
		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify((int)PillOutOfStockService.serialVersionUID, mBuilder.build());
	}
}
