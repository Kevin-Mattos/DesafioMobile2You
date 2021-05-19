package com.example.desafiomobile2you.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiomobile2you.databinding.MovieRecyclerItemViewBinding
import com.example.desafiomobile2you.repository.entities.Movie

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
        val binding = MovieRecyclerItemViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding, actions)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    fun update(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder (private val mBinding: MovieRecyclerItemViewBinding, private val actions: MovieAction) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(movie: Movie) {

            with(mBinding) {
                similarMovieTitle.text = movie.title
                similarMovieDescription.text = "${movie.releaseDate.substring(0 until 4)}, ${actions.getMovieGenreById(movie.genreIds)}"

                Glide.with(mBinding.root)
                    .load(actions.getMovieImageUrl(movie.posterPath, "w185"))
                    .into(similarMovieImage)

                root.setOnClickListener {
                    movie.isSelected = !movie.isSelected
                    sparkButton.callOnClick()

                }

            }

        }

    }

}