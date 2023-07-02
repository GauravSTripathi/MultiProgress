package com.gaurav.multiprogress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.multiprogress.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var pair = ArrayList<Pair<Float, Float>>()

        pair.add(Pair(8F, 10F))
        pair.add(Pair(12F, 17F))
        pair.add(Pair(18F, 19F))
        pair.add(Pair(21F, 23F))
        binding.multiProgressTaskOne.setHours(8F, 23F)
        binding.multiProgressTaskOne.setProgress(pair)
        binding.multiProgressTaskOne.progress_radius = 50F
//
        pair = ArrayList()
        pair.add(Pair(0F, 0.7F))
        pair.add(Pair(5F, 5.2F))
        binding.multiProgressTaskTwo.setHours(0F, 7F)
        binding.multiProgressTaskTwo.setProgress(pair)

        pair = ArrayList()
        pair.add(Pair(16F, 19F))
        pair.add(Pair(22F, 5F))
        pair.add(Pair(6F, 8F))

        binding.multiProgressTaskThree.setHours(16F, 8F)
        binding.multiProgressTaskThree.setProgress(pair)


    }
}