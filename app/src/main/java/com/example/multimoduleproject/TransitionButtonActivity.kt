package com.example.multimoduleproject

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.common.buttons.transitionButton.TransitionButton
import com.example.common.buttons.transitionButton.TransitionButton.StopAnimationStyle.EXPAND
import com.example.common.buttons.transitionButton.TransitionButton.StopAnimationStyle.SHAKE

class TransitionButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_button)

        val shakeTransitionButton = findViewById<TransitionButton>(R.id.shake_transition_btn)
        shakeTransitionButton.setOnClickListener {
            shakeTransitionButton.startAnimation()
            Handler().postDelayed({
                shakeTransitionButton.stopAnimation(
                    SHAKE, null
                )
            }, 3000)
        }

        val expandTransitionButton = findViewById<TransitionButton>(R.id.expand_transition_btn)
        expandTransitionButton.setOnClickListener {
            expandTransitionButton.startAnimation()
            Handler().postDelayed({
                expandTransitionButton.stopAnimation(
                    EXPAND, null
                )
            }, 3000)
        }
    }
}