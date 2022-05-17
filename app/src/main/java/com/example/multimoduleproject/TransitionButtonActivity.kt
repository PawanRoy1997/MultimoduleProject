package com.example.multimoduleproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.common.buttons.transitionButton.TransitionButton.StopAnimationStyle.EXPAND
import com.example.common.buttons.transitionButton.TransitionButton.StopAnimationStyle.SHAKE
import com.example.multimoduleproject.databinding.ActivityTransitionButtonBinding

class TransitionButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTransitionButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shakeTransitionButton = binding.shakeTransitionBtn
        shakeTransitionButton.setOnClickListener {
            shakeTransitionButton.startAnimation()
            Handler().postDelayed({
                shakeTransitionButton.stopAnimation(
                    SHAKE, null
                )
            }, 3000)
        }

        val expandTransitionButton = binding.expandTransitionBtn
        expandTransitionButton.setOnClickListener {
            expandTransitionButton.startAnimation()
            Handler().postDelayed({
                expandTransitionButton.stopAnimation(
                    EXPAND, null
                )
            }, 3000)
        }

        val circularImageView = binding.circularImageViewButton
        circularImageView.setOnClickListener {
            startActivity(Intent(this, CircularImageViewActivity::class.java))
            this.finish()
        }
    }
}