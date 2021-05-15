package com.example.desafiomobile2you.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.desafiomobile2you.R
import com.example.desafiomobile2you.repository.entities.Movie
import kotlinx.android.synthetic.main.movie_recycler_item_view.view.*

class MovieAdapter(val context: Context, val movies: MutableList<Movie> = mutableListOf(), val actions: MovieAction):
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    interface MovieAction {
        fun getMovieGenreById(genreIds: List<Long>): String
        fun getMovieImageUrl(backdropPath: String, size: String): String
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.movie_recycler_item_view,
                parent, false
            )
        return ViewHolder(viewCriada, actions, ::notifyListeners)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noticia = movies[position]
        holder.bind(noticia)
    }

    fun update(movies: List<Movie>) {
        notifyItemRangeRemoved(0, this.movies.size)
        this.movies.clear()
        this.movies.addAll(movies)
        notifyItemRangeInserted(0, this.movies.size)
    }

    fun notifyListeners() {
        notifyDataSetChanged()
    }


    class ViewHolder (itemView: View, private val actions: MovieAction, val notifyChanged: () -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.similar_movie_title.text = movie.title
            itemView.similar_movie_description.text = "${movie.releaseDate.substring(0 until 4)}, ${actions.getMovieGenreById(movie.genreIds)}"

            if(movie.isSelected && !movie.wasSelected) {
                itemView.spark_button.callOnClick()
                movie.wasSelected = true
            }
            else if(movie.isSelected) {
                itemView.spark_button.animation = null
                itemView.spark_button.isChecked = true
            } else {
                movie.wasSelected = false
            }

            Glide.with(itemView)
                .load(actions.getMovieImageUrl(movie.posterPath, "w185"))
                .into(itemView.similar_movie_image)
            itemView.setOnClickListener {
                movie.isSelected = !movie.isSelected
                notifyChanged()
            }
        }

    }

}