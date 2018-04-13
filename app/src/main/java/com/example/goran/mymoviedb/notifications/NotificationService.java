package com.example.goran.mymoviedb.notifications;


import com.example.goran.mymoviedb.Constants;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


/**
 * Created by Goran on 27.1.2018..
 */

public class NotificationService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        String title = jobParameters.getExtras().getString(Constants.EXTRA_MOVIE_TITLE);

        NotificationUtils.showNotification(this, title);

        jobFinished(jobParameters, false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
