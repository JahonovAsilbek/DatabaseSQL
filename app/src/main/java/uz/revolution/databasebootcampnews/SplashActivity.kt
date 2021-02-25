package uz.revolution.databasebootcampnews

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToMain()
    }

    private fun goToMain() {
        Handler().postDelayed({
            val mIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }
}