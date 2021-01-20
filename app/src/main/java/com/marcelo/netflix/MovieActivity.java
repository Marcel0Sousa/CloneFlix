package com.marcelo.netflix;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelo.netflix.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtCast;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.text_view_title);
        txtDesc = findViewById(R.id.text_view_desc);
        txtCast = findViewById(R.id.text_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);

        }

        LayerDrawable drawable = (LayerDrawable)
                ContextCompat.getDrawable(this, R.drawable.shadows);

        if (drawable != null) {
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4);
            drawable.setDrawableByLayerId(R.id.cover_drawble, movieCover);
            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
        }

        txtTitle.setText("Batman Begins");
        txtDesc.setText("O jovem Bruce Wayne cai em um poço e é atacado por morcegos. Bruce acorda de um pesadelo sobre seu passado e descobre que é prisioneiro no Butão. É apresentado a Henri Ducard, que fala por Ra's Al Ghul, líder da Liga das Sombras, e o convida para ser treinado pela elite de vigilantes.");
        txtCast.setText(getString(R.string.cast, "Christian Bale" + ",Michael Caine" + ",Liam Neeson" + ",Katie Holmes" + ",Gary Oldman" + ",Cillian Murphy" + ",Tom Wilkinson" + ",Morgan Freeman"));

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Movie movie = new Movie();
            movies.add(movie);
        }

        recyclerView.setAdapter(new MovieAdapter(movies));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    private static class MovieHolder extends RecyclerView.ViewHolder{
        final ImageView imageViewCover;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parente, int viewType) {

            return new MovieHolder(getLayoutInflater()
                    .inflate(R.layout.movie_item_similar, parente, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            // holder.imageViewCover.setImageResource(movie.getCoverUrl());

        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }
}