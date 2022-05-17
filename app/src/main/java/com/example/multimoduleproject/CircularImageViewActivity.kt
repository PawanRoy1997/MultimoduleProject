package com.example.multimoduleproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.multimoduleproject.databinding.ActivityCircularImageViewBinding

class CircularImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCircularImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircularImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}