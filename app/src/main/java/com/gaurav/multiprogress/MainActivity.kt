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

//        pair.add(Pair(8F,10F))
//        pair.add(Pair(12F,17F))
//        pair.add(Pair(18F,19F))
//        pair.add(Pair(21F,23F))


        pair.add(Pair(0F, 2F))
        pair.add(Pair(3F, 4F))
        pair.add(Pair(6F, 8F))
////        pair.add(Pair(21F,23F))

        binding.multiProgressTaskOne.setProgress(pair)

        pair = ArrayList()
        pair.add(Pair(1F, 3F))
        pair.add(Pair(5F, 7F))
//        pair.add(Pair(6F,8F))
////        pair.add(Pair(21F,23F))

        binding.multiProgressTaskTwo.setProgress(pair)

        pair = ArrayList()
        pair.add(Pair(8F, 12F))
//        pair.add(Pair(3F,4F))
        pair.add(Pair(16F, 19F))
////        pair.add(Pair(21F,23F))

        binding.multiProgressTaskThree.setProgress(pair)


    }
}