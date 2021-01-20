package com.marcelo.netflix.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.marcelo.netflix.model.MovieDetail;

import java.lang.ref.WeakReference;
import java.util.List;

public class MovieDetailTask  extends AsyncTask<String, Void, MovieDetail> {
    private final WeakReference<Context> context;
    private ProgressDialog dialog;
    private MovieDetailLoader movieDetailLoader;

    public MovieDetailTask(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void setMovieDetailLoader(MovieDetailLoader movieDetailLoader) {
        this.movieDetailLoader = movieDetailLoader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = this.context.get();

        if (context != null)
            dialog = ProgressDialog.show(context, "Carregando", "", true);
    }

    @Override
    protected MovieDetail doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        super.onPostExecute(movieDetail);
        dialog.dismiss();

        if (movieDetailLoader != null)
            movieDetailLoader.onResult(movieDetail);
    }

    public interface MovieDetailLoader {
        void onResult(MovieDetail movieDetails);
    }
}
