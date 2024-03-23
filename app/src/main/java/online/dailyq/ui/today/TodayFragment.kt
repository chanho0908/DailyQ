package online.dailyq.ui.today

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import online.dailyq.R
import online.dailyq.databinding.FragmentTodayBinding
import online.dailyq.ui.base.BaseFragment


class TodayFragment : BaseFragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTodayBinding.inflate(layoutInflater)
        return binding.root
    }


}