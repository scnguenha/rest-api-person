package mz.co.scn.restapi.sync;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;

import com.fasterxml.jackson.databind.ObjectMapper;

import mz.co.scn.restapi.utils.ObjectMapperProvider;

/**
 * Created by Simao on 11/25/2016.
 */
public abstract class BaseAsyncTask<Param, Progress,Result> extends AsyncTask<Param, Progress, Result> {

    protected final String LOG_TAG = "VIATURA";
    protected ProgressDialog prgDialog;
    protected Context context;
    protected ObjectMapper objectMapper = new ObjectMapperProvider().getContext();
    protected NotificationManager mNotifyManager;
    protected NotificationCompat.Builder build;
    protected int progresso = 0;
    protected SharedPreferences sharedPreferences;

    /**
     * @param context - O contexto.
     */
    protected BaseAsyncTask(Context context) {
        this.context = context;

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @param msg - A mensagem.
     */
    protected void showProgressBar(String msg, boolean cancelable, boolean canceledOnTouchOutside) {
        prgDialog = ProgressDialog.show(context, null, msg, true);
        prgDialog.setCancelable(cancelable);
        prgDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        prgDialog.setMax(100);

        if (cancelable)
            prgDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                }
            });
    }

    /**
     * @param title - O titulo.
     * @param msg   - A mensagem.
     */
    protected void showProgressBar(String title, String msg) {
        prgDialog = ProgressDialog.show(context, title, msg, true);
        prgDialog.setCancelable(true);
        prgDialog.setCanceledOnTouchOutside(true);
        prgDialog.setMax(100);
        prgDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });
    }

    /**
     * @param msg - A mensagem.
     */
    protected void showProgressBar(String msg) {
        prgDialog = ProgressDialog.show(context, null, msg, true);
        prgDialog.setCancelable(true);
        prgDialog.setCanceledOnTouchOutside(true);
        prgDialog.setMax(100);
        prgDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });
    }

    /**
     * @param title          - O titulo.
     * @param text           - A mensagem.
     * @param smallIcon      - O resourse id do icon.
     * @param resumeActivity - A activity.
     */
    protected void showNotificationStatus(int id, String title, String text, int smallIcon, Class<?> resumeActivity) {
        // Displays the progress bar for the first time.
        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resumeIntent = new Intent(context, resumeActivity);
        resumeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resumePendingIntent = PendingIntent.getActivity(context, 2, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        build = new NotificationCompat.Builder(context);
        build.setContentInfo("0/100");
        build.setContentTitle(title);
        build.setContentText(text);
        build.setSmallIcon(smallIcon);
        build.setProgress(100, 0, false);
        build.setContentIntent(resumePendingIntent);
        build.setAutoCancel(true);

        mNotifyManager.notify(id, build.build());
    }

    /**
     * @param values - Os valores.
     */
    protected void updateProgress(Progress[] values) {
        // Update progress from progress dialog
        if (prgDialog != null)
            prgDialog.setProgress((Integer) values[0]);
    }

    /**
     * @param id     - O id do progresso.
     * @param values - Os valores.
     */
    protected void updateProgress(int id, Progress[] values) {
        // Update progress from progress dialog
        if (prgDialog != null)
            prgDialog.setProgress((Integer) values[0]);

        // Update progress from notifications
        build.setProgress(100, (Integer) values[0], false);
        build.setContentInfo(values[0] + "/100");

        mNotifyManager.notify(id, build.build());
    }

    /**
     * @param id   - O id do progresso.
     * @param text - A mensagem.
     */
    protected void finalizeProgress(int id, String text) {
        // Dismiss Progress Dialog
        if (prgDialog != null)
            prgDialog.dismiss();

        // Removes the progress bar from notifications
        build.setContentText(text);
        build.setProgress(0, 0, false);

        mNotifyManager.notify(id, build.build());
    }

    /**
     *
     */
    protected void finalizeProgress() {
        // Dismiss Progress Dialog
        if (prgDialog != null)
            prgDialog.dismiss();
    }

    /**
     * @param id - O id do progresso.
     */
    protected void cancelProgress(int id, String text) {
        // Removes the progress bar from notifications
        build.setContentText(text);
        build.setProgress(0, 0, false);

        mNotifyManager.notify(id, build.build());
    }

}
