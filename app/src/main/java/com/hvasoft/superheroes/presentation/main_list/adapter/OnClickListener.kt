package com.hvasoft.superheroes.presentation.main_list.adapter

import com.hvasoft.superheroes.data.model.Superhero

interface OnClickListener {
    fun onClick(superhero: Superhero)
}