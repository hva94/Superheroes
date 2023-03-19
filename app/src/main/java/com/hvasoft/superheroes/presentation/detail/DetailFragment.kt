package com.hvasoft.superheroes.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hvasoft.superheroes.R
import com.hvasoft.superheroes.core.Utils.loadImage
import com.hvasoft.superheroes.data.model.Superhero
import com.hvasoft.superheroes.databinding.FragmentDetailBinding
import com.hvasoft.superheroes.presentation.HostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var _hostActivity: HostActivity? = null
    private val hostActivity get() = _hostActivity!!

    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var superhero: Superhero

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar()
        setupView()
    }

    private fun setupActionBar() {
        _hostActivity = activity as HostActivity

        hostActivity.supportActionBar?.title = getString(R.string.title_detail_superhero)
    }

    private fun setupView() {
        superhero = args.currentSuperhero
        
        binding.apply { 
            tvName.text = superhero.name
            ivPhoto.loadImage(superhero.image.url)
            bindPowerstats(tvPowerStats, superhero)
            bindBiography(tvBiography, superhero)
            bindAppearance(tvAppearance, superhero)
            bindWork(tvWork, superhero)
            bindConnections(tvConnections, superhero)
        }
    }

    private fun bindPowerstats(tvPowerStats: TextView, superhero: Superhero) {
        tvPowerStats.text = buildSpannedString {
            bold { appendLine("POWERSTATS: ") }
            italic { append("Intelligence: ") }
            bold { appendLine(superhero.powerstats.intelligence) }
            italic { append("Strength: ") }
            bold { appendLine(superhero.powerstats.strength) }
            italic { append("Speed: ") }
            bold { appendLine(superhero.powerstats.speed) }
            italic { append("Durability: ") }
            bold { appendLine(superhero.powerstats.durability) }
            italic { append("Power: ") }
            bold { appendLine(superhero.powerstats.power) }
            italic { append("Combat: ") }
            bold { appendLine(superhero.powerstats.combat) }
        }
    }

    private fun bindBiography(tvBiography: TextView, superhero: Superhero) {
        tvBiography.text = buildSpannedString {
            bold { appendLine("BIOGRAPHY: ") }
            italic { append("Full Name: ") }
            bold { appendLine(superhero.biography.fullName) }
            italic { append("Alter Egos: ") }
            bold { appendLine(superhero.biography.alterEgos) }
            italic { append("Aliases: ") }
            for (alias in superhero.biography.aliases) { bold { appendLine(alias) } }
            italic { append("Place of Birth: ") }
            bold { appendLine(superhero.biography.placeOfBirth) }
            italic { append("First Appearance: ") }
            bold { appendLine(superhero.biography.firstAppearance) }
            italic { append("Publisher: ") }
            bold { appendLine(superhero.biography.publisher) }
            italic { append("Alignment: ") }
            bold { appendLine(superhero.biography.alignment) }
        }
    }

    private fun bindAppearance(tvAppearance: TextView, superhero: Superhero) {
        tvAppearance.text = buildSpannedString {
            bold { appendLine("APPEARANCE: ") }
            italic { append("Gender: ") }
            bold { appendLine(superhero.appearance.gender) }
            italic { append("Race: ") }
            bold { appendLine(superhero.appearance.race) }
            italic { append("Height: ") }
            for (height in superhero.appearance.height) { bold { appendLine(height) } }
            italic { append("Weight: ") }
            for (weight in superhero.appearance.weight) { bold { appendLine(weight) } }
            italic { append("Eye Color: ") }
            bold { appendLine(superhero.appearance.eyeColor) }
            italic { append("Hair Color: ") }
            bold { appendLine(superhero.appearance.hairColor) }
        }
    }

    private fun bindWork(tvWork: TextView, superhero: Superhero) {
        tvWork.text = buildSpannedString {
            bold { appendLine("WORK: ") }
            italic { append("Occupation: ") }
            bold { appendLine(superhero.work.occupation) }
            italic { append("Base of operation: ") }
            bold { appendLine(superhero.work.base) }
        }
    }

    private fun bindConnections(tvConnections: TextView, superhero: Superhero) {
        tvConnections.text = buildSpannedString {
            bold { appendLine("CONNECTIONS: ") }
            italic { append("Group Affiliation: ") }
            bold { appendLine(superhero.connections.groupAffiliation) }
            italic { append("Relatives: ") }
            bold { appendLine(superhero.connections.relatives) }
        }
    }

    override fun onDestroyView() {
        hostActivity.supportActionBar?.title = getString(R.string.app_name)
        _hostActivity = null
        _binding = null
        super.onDestroyView()
    }
}