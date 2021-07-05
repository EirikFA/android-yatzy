package no.arweb.yatzy

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import no.arweb.yatzy.databinding.FragmentGameBinding
import no.arweb.yatzy.util.Util

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null

    private val args: GameFragmentArgs by navArgs()

    // Only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val scoreFields: ArrayList<ScoreField> = ArrayList()

    private fun calculateScores() {
        scoreFields
            .filter { Util.SPECIAL_COMBINATIONS.contains(it.combination) }
            .forEach { it.updateScore(scoreFields) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Game"

        binding.calculateButton.setOnClickListener { calculateScores() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        renderCells(binding.yatzyTable)
    }

    private fun renderCells(grid: GridLayout) {
        var combinations: List<Combination> = Combination.values().toList()
        if (args.variation == Variation.FORCED) combinations =
            combinations.filter { !it.isMaxiOnly }

        grid.apply {
            columnCount = args.players.size + 1
            rowCount = combinations.size + 1
        }

        args.players.forEachIndexed { index, player ->
            val params: GridLayout.LayoutParams = GridLayout.LayoutParams(
                GridLayout.spec(0, 1f),
                GridLayout.spec(index + 1, 1f)
            )
            params.setGravity(Gravity.FILL)

            val cell: TextView =
                TextView(ContextThemeWrapper(activity, R.style.YatzyNameCell)).apply {
                    id = View.generateViewId()
                    layoutParams = params
                    text = player
                }

            grid.addView(cell)
        }

        combinations.forEachIndexed { index, combination ->
            val combParams: GridLayout.LayoutParams = GridLayout.LayoutParams(
                GridLayout.spec(index + 1, 1f),
                GridLayout.spec(0, 1f)
            )
            combParams.setGravity(Gravity.FILL)

            val combCell: TextView =
                TextView(ContextThemeWrapper(activity, R.style.YatzyCombinationCell))
                    .apply {
                        id = View.generateViewId()
                        layoutParams = combParams
                        text = combination.friendlyName(context)
                    }

            if (Util.SPECIAL_COMBINATIONS.contains(combination)) combCell.typeface =
                Typeface.DEFAULT_BOLD

            val scoreCells: ArrayList<TextView> = ArrayList()

            for (i in args.players.indices) {
                val scoreParams: GridLayout.LayoutParams = GridLayout.LayoutParams(combParams)
                scoreParams.columnSpec = GridLayout.spec(i + 1, 1f)

                val themeWrapper = ContextThemeWrapper(activity, R.style.YatzyCell)

                val cell: TextView =
                    if (Util.SPECIAL_COMBINATIONS.contains(combination)) TextView(themeWrapper).apply {
                        typeface = Typeface.DEFAULT_BOLD
                    }
                    else EditText(themeWrapper)

                scoreCells.add(cell.apply {
                    id = View.generateViewId()
                    layoutParams = scoreParams
                    background =
                        ContextCompat.getDrawable(context, R.drawable.black_border_right_bottom)
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(3))
                })

                scoreFields.add(ScoreField(combination, args.players[i], args.variation, cell))
            }

            grid.addView(combCell)
            scoreCells.map(grid::addView)
        }
    }
}
