package no.arweb.yatzy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import no.arweb.yatzy.databinding.FragmentSetupGameBinding
import no.arweb.yatzy.util.Util

class SetupGameFragment : Fragment() {

    private var _binding: FragmentSetupGameBinding? = null

    // Only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private fun addPlayer() {
        View.inflate(
            context,
            R.layout.player_name_edit,
            binding.players
        )

        val playerField: LinearLayout =
            binding.players.getChildAt(binding.players.childCount - 1) as LinearLayout
        for (child in playerField.children) {
            if (child is EditText) {
                child.requestFocus()
            }

            if (child is ImageButton) {
                child.setOnClickListener(this::removePlayer)
            }
        }
    }

    private fun removePlayer(view: View) {
        val parent: ViewParent = view.parent
        if (parent is View) {
            binding.players.removeView(parent)
        }
    }

    private fun startGame(view: View) {
        val playerEdits: List<EditText> =
            Util.getViewsByTag(binding.players, getString(R.string.player_edit_tag))
                .filterIsInstance<EditText>()
        val players: List<String> =
            playerEdits.map { it.text.toString() }.filter { it.isNotEmpty() }

        if (players.size < 2) {
            val diagBuilder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }
            diagBuilder?.setTitle(R.string.not_enough_players)
                ?.setMessage(R.string.min_players)
                ?.setPositiveButton(R.string.add_players) { _, _ -> }

            diagBuilder?.show()
        } else {
            val variationRadio: RadioButton =
                binding.variation.findViewById(binding.variation.checkedRadioButtonId)
            val variation: Variation =
                if (variationRadio.text == "Forced") Variation.FORCED else Variation.MAXI

            val action = SetupGameFragmentDirections.startGame(variation, players.toTypedArray())

            view.findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupGameBinding.inflate(inflater, container, false)

        binding.addPlayerButton.setOnClickListener { this.addPlayer() }
        binding.startGameButton.setOnClickListener(this::startGame)

        (activity as AppCompatActivity).supportActionBar?.title = "Setup game"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
