package online.dailyq.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import online.dailyq.api.response.HelloWorld
import online.dailyq.databinding.FragmentTodayBinding
import online.dailyq.ui.base.BaseFragment
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.util.Locale

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

    private fun initView() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val url = URL("http://10.0.2.2:5000/v1/hello-world")

            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            conn.readTimeout = 5000

            // HTTP Method
            conn.requestMethod = "GET"

            // Accept : 클라이언트가 받길 원하는 ContentType과 우선 순위를 정하는 Header
            conn.setRequestProperty("Accept", "application/json")

            conn.connect()

            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            val body = reader.readText()
            reader.close()
            conn.disconnect()

//          val json = JSONObject(body)
//          val date = json.getString("date")
//          val message = json.getString("message")

            val gson = Gson()

            // FULL : 2022년 1월 12일 수요일
            // LONG : 2022년 1월 12일 (수)
            // MEDIUM : 2022. 1. 12.
            // SHORT : 22. 1. 12.
            val format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.KOREA)
            val response = gson.fromJson(body, HelloWorld::class.java)
            val date = format.format(response.date)

            withContext(Dispatchers.Main) {
                with(binding) {
                    this.date.text = date
                    question.text = response.message
                }
            }


        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
