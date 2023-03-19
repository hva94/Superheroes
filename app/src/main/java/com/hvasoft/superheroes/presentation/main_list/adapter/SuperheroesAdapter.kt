package com.hvasoft.superheroes.presentation.main_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.superheroes.R
import com.hvasoft.superheroes.core.Utils.loadImage
import com.hvasoft.superheroes.data.model.Superhero
import com.hvasoft.superheroes.databinding.ItemSuperheroBinding

class SuperheroesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Superhero, RecyclerView.ViewHolder>(SuperheroDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_superhero, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val superhero = getItem(position)

        with(holder as ViewHolder) {
            setListener(superhero)

            binding.tvName.text = superhero.name
            binding.ivPhoto.loadImage(superhero.image.url)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSuperheroBinding.bind(view)

        fun setListener(superhero: Superhero) {
            binding.root.setOnClickListener {
                onClickListener.onClick(superhero)
            }
        }
    }

    class SuperheroDiffCallback : DiffUtil.ItemCallback<Superhero>() {
        override fun areItemsTheSame(oldItem: Superhero, newItem: Superhero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Superhero, newItem: Superhero): Boolean {
            return oldItem == newItem
        }
    }

}