package online.dailyq.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import online.dailyq.databinding.FragmentTodayBinding
import online.dailyq.ui.base.BaseFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TodayFragment : BaseFragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val url = URL("http://10.0.2.2:5000/v1/hello-world")

            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.requestMethod = "GET"

            conn.connect()

            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            val body = reader.readText()
            reader.close()
            conn.disconnect()

            withContext(Dispatchers.Main){
                binding.question.text = body
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
