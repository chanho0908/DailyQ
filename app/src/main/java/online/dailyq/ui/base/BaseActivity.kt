package online.dailyq.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import online.dailyq.R
import online.dailyq.databinding.ActivityMainBinding

abstract class BaseActivity: AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}