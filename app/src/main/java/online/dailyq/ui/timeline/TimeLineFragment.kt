package online.dailyq.ui.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import online.dailyq.R
import online.dailyq.databinding.FragmentTimeLineBinding


class TimeLineFragment : Fragment() {
    private var _binding: FragmentTimeLineBinding ?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimeLineBinding.inflate(layoutInflater)
        return binding.root
    }

}