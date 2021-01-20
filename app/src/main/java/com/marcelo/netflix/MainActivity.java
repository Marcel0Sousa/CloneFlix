package com.marcelo.netflix;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcelo.netflix.model.Category;
import com.marcelo.netflix.model.Movie;
import com.marcelo.netflix.util.CategoryTask;
import com.marcelo.netflix.util.ImageDownloaderTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryTask.CategoryLoader {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_main);

        List<Category> categories = new ArrayList<>();
        /*
        *Mock
        *
        *
        for (int j = 0; j < 10; j++) {
            Category category = new Category();
            category.setName("Category " + j);

            List<Movie> movies = new ArrayList<>();

            for (int i = 0; i < 30; i++){
                Movie movie = new Movie();
               // movie.setCoverUrl(R.drawable.movie);
                movies.add(movie);
            }

            category.setMovies(movies);
            categories.add(category);
        }*/

        mainAdapter = new MainAdapter(categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mainAdapter);

        CategoryTask categoryTask = new CategoryTask(this);
        categoryTask.setCategoryLoader(this);
                categoryTask.execute("https://tiagoaguiar.co/api/netflix/home");
    }

    @Override
    public void onResult(List<Category> categories) {
        mainAdapter.setCategories(categories);
        mainAdapter.notifyDataSetChanged();
    }

    private static class CategoryHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        RecyclerView recyclerViewMovie;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            recyclerViewMovie = itemView.findViewById(R.id.recycler_view_movie);
        }
    }

    private static class MovieHolder extends RecyclerView.ViewHolder{
        final ImageView imageViewCover;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);
        }
    }

    // vertical
    private class MainAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<Category> categories;

        private MainAdapter(List<Category> categories) {
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parente, int viewType) {

            return new CategoryHolder(getLayoutInflater().inflate(R.layout.category_item, parente, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
           Category category = categories.get(position);
            holder.textViewTitle.setText(category.getName());
            holder.recyclerViewMovie.setAdapter(new MovieAdapter (category.getMovies()));
            holder.recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.HORIZONTAL, false));

        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        void setCategories(List<Category> categories) {
            this.categories.clear();
            this.categories.addAll(categories);
        }
    }

    //horizontal
    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parente, int viewType) {

            return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item, parente, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            // holder.imageViewCover.setImageResource(movie.getCoverUrl());
            new ImageDownloaderTask(holder.imageViewCover).execute(movie.getCoverUrl());

        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

}