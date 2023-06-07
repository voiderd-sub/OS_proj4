package com.example.pj4test.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pj4test.MainActivity
import com.example.pj4test.ProjectConfiguration
import com.example.pj4test.SoundPlayer
import com.example.pj4test.audioInference.SpeechClassifier
import com.example.pj4test.databinding.FragmentAudioBinding

class AudioFragment: Fragment(), SpeechClassifier.DetectorListener {
    private val TAG = "AudioFragment"

    private var _fragmentAudioBinding: FragmentAudioBinding? = null

    private val fragmentAudioBinding
        get() = _fragmentAudioBinding!!

    // classifiers
    private var speechClassifier: SpeechClassifier? = null

    // views
    lateinit var snapView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAudioBinding = FragmentAudioBinding.inflate(inflater, container, false)

        return fragmentAudioBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snapView = fragmentAudioBinding.SnapView

        (activity as MainActivity).initializeSpeechClassifier = {
            speechClassifier = SpeechClassifier()
            speechClassifier!!.initialize(requireContext())
            speechClassifier!!.setDetectorListener(this)
        }
    }

    override fun onPause() {
        super.onPause()
        speechClassifier?.stopInferencing()
    }

    override fun onResume() {
        super.onResume()
        speechClassifier?.startInferencing()
    }

    override fun onResults(score: Float) {
        activity?.runOnUiThread {
            if (score > SpeechClassifier.THRESHOLD) {
                snapView.text = "BE QUIET"
                snapView.setBackgroundColor(ProjectConfiguration.activeBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.activeTextColor)
                SoundPlayer.play(SoundPlayer.DING_DONG)

            } else {
                snapView.text = "KEEP BEING QUIET"
                snapView.setBackgroundColor(ProjectConfiguration.idleBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.idleTextColor)
            }
        }
    }
}