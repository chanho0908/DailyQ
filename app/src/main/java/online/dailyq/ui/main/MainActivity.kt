package online.dailyq.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import online.dailyq.R
import online.dailyq.databinding.ActivityMainBinding
import online.dailyq.ui.base.BaseActivity
import online.dailyq.ui.profile.ProfileFragment
import online.dailyq.ui.timeline.TimeLineFragment
import online.dailyq.ui.today.TodayFragment

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.navView.setOnItemSelectedListener {
            val ft = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.timeline -> {
                    ft.replace(R.id.host, TimeLineFragment())
                    supportActionBar?.setTitle(R.string.title_timeline)
                }
                R.id.today -> {
                    ft.replace(R.id.host, TodayFragment())
                    supportActionBar?.setTitle(R.string.title_today)
                }
                R.id.profile -> {
                    ft.replace(R.id.host, ProfileFragment())
                    supportActionBar?.setTitle(R.string.title_profile)
                }
            }
            ft.commit()
            true
        }

        binding.navView.selectedItemId = R.id.today
    }
}