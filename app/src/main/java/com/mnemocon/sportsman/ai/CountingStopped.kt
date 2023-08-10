package com.mnemocon.sportsman.ai

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnemocon.sportsman.ai.databinding.FragmentCountingStoppedBinding


private const val PUSHUPS = "pushups"
private const val SQUATS = "squats"
private const val TIME = "time"


class CountingStopped : Fragment() {

    private lateinit var binding: FragmentCountingStoppedBinding

    private var pushups: Int? = null
    private var squats: Int? = null
    private var time: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pushups = it.getInt(PUSHUPS)
            squats = it.getInt(SQUATS)
            time = it.getInt(TIME)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = FragmentCountingStoppedBinding.inflate(layoutInflater, container, false)

        binding.imageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.pushup.text = pushups.toString()  + " " + getString(R.string.squats_count)
        binding.squat.text = squats.toString() + " " + getString(R.string.pushups_count)
        binding.time.text = getTime()

        return binding.root
    }

    private fun getTime(): String {
        var hour = time!!.div(3600)
        time = time!! % 3600
        var minute = time!!.div(60)
        time = time!! % 60
        var sec = time

        var ans : String = getString(R.string.time_label) + " "

        if( hour == 0 && minute == 0 ) {
            ans += sec.toString() + getString(R.string.second)
        }
        else {
            if(hour > 0) ans += hour.toString() + getString(R.string.hour)
            if(minute > 0) ans += minute.toString() + getString(R.string.min)
            if (sec != null) {
                if(sec > 0) ans += sec.toString() + getString(R.string.second)
            }
        }
        return ans
    }

    override fun onDetach() {
        super.onDetach()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}