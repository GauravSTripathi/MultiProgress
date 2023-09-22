package com.gaurav.multiprogress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.multiprogress.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var pair = ArrayList<Pair<Float, Float>>()

//        pair.add(Pair(8.5F, 10F))
//        pair.add(Pair(12F, 17F))
//        pair.add(Pair(18F, 19F))
//        pair.add(Pair(21F, 23F))


//        pair.add(Pair(05.30F, 05.80F))
//        pair.add(Pair(04.90F, 05.15F))
//        pair.add(Pair(04.65F, 04.80F))
//        pair.add(Pair(03.00F, 04.0F))
//        pair.add(Pair(02.816668F, 03.0F))
//        pair.add(Pair(2.00F, 02.65F))
//        pair.add(Pair(1.5F, 02.0F))
//        pair.add(Pair(00.816668F, 01.0F))
//        pair.add(Pair(22.816668F, 23.0F))
//        pair.add(Pair(22.5F, 22.5F))
//        pair.add(Pair(22.2F, 22.266666F))
//        pair.add(Pair(22.0F, 22.0F))
//        pair.add(Pair(21.816668F, 21.816668F))
//        pair.add(Pair(21.533333F, 21.583334F))
//        pair.add(Pair(21.4F, 21.4F))
//        pair.add(Pair(21.166666F, 21.183332F))
//        pair.add(Pair(20.766666F, 20.9F))
//        pair.add(Pair(20.45F, 20.45F))
//        pair.add(Pair(20.316668F, 20.316668F))
//        pair.add(Pair(19.166666F, 19.55F))


//        pair.add(Pair(17.816668F, 17.816668F))
//        pair.add(Pair(17.683332F, 17.683332F))
//        pair.add(Pair(17.05F, 17.333334F))
//        pair.add(Pair(16.683332F, 16.9F))
//        pair.add(Pair(01.683332F, 01.9F))
//
//        binding.multiProgressTaskOne.showHours = true
//        binding.multiProgressTaskOne.is24Hour = false
//        binding.multiProgressTaskOne.startTimeDisplay = "03:51 AM"
//        binding.multiProgressTaskOne.endTimeDisplay = "08:00 AM"
//        binding.multiProgressTaskOne.setHours(16F, 5F)
//        binding.multiProgressTaskOne.setProgress(pair)
//        binding.multiProgressTaskOne.progress_radius = 50F
//
//        pair = ArrayList()
//        pair.add(Pair(0F, 0.7F))
//        pair.add(Pair(5F, 5.2F))
//        binding.multiProgressTaskTwo.setHours(0F, 7F)
//        binding.multiProgressTaskTwo.setProgress(pair)

        pair = ArrayList()
//        pair.add(Pair(16F, 19F))
//        pair.add(Pair(22F, 5F))
//        pair.add(Pair(6F, 8F))


        pair.add(Pair(17.816668F, 17.816668F))
        pair.add(Pair(17.683332F, 17.683332F))
        pair.add(Pair(17.05F, 17.333334F))
        pair.add(Pair(16.683332F, 16.9F))
        pair.add(Pair(01.683332F, 01.9F))
        binding.multiProgressTaskThree.showHours = true
        binding.multiProgressTaskThree.is24Hour = false
        binding.multiProgressTaskThree.startTimeDisplay = "04:00 PM"
        binding.multiProgressTaskThree.endTimeDisplay = "08:00 AM"

        binding.multiProgressTaskThree.setHours(16F, 8F)
        binding.multiProgressTaskThree.setProgress(pair)


        binding.timeLineViewOne.setTime(
            dateTimeToMilliseconds("2023-09-11 17:00:14","yyyy-MM-dd HH:mm:ss"),
            dateTimeToMilliseconds("2023-09-12 07:47:14", "yyyy-MM-dd HH:mm:ss")
        )


        binding.btnGenrate.setOnClickListener {
            var pair2 = ArrayList<Pair<Long, Long>>()

            pair2.add(
                Pair(
                    dateTimeToMilliseconds("2023-09-11 17:00:14", "yyyy-MM-dd HH:mm:ss"),
                    dateTimeToMilliseconds("2023-09-11 17:47:14", "yyyy-MM-dd HH:mm:ss")
                )
            )
            pair2.add(
                Pair(
                    dateTimeToMilliseconds("2023-09-11 18:47:14", "yyyy-MM-dd HH:mm:ss"),
                    dateTimeToMilliseconds("2023-09-11 19:00:14", "yyyy-MM-dd HH:mm:ss")
                )
            )
            pair2.add(
                Pair(
                    dateTimeToMilliseconds("2023-09-11 22:00:14", "yyyy-MM-dd HH:mm:ss"),
                    dateTimeToMilliseconds("2023-09-11 23:50:14", "yyyy-MM-dd HH:mm:ss")
                )
            )
            pair2.add(
                Pair(
                    dateTimeToMilliseconds("2023-09-12 00:47:14", "yyyy-MM-dd HH:mm:ss"),
                    dateTimeToMilliseconds("2023-09-12 02:47:14", "yyyy-MM-dd HH:mm:ss")
                )
            )
            pair2.add(
                Pair(
                    dateTimeToMilliseconds("2023-09-12 04:32:14", "yyyy-MM-dd HH:mm:ss"),
                    dateTimeToMilliseconds("2023-09-12 07:23:14", "yyyy-MM-dd HH:mm:ss")
                )
            )
            binding.timeLineViewOne.setProgress(pair2)
        }



    }


    fun dateTimeToMilliseconds(dateTimeString: String, pattern: String): Long {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val date = sdf.parse(dateTimeString)
        return date?.time ?: 0
    }

}