package com.example.otpsender.util

import java.util.*

class RandomUtil {

    companion object{

        fun generateRandomNumbers() : String {

            var result = ""
            val r = Random()

            var i = 0
            while(i<6){
                var temp = r.nextInt(8)+1
                result += temp
                i++
            }

            return result
        }


    }
}