package com.topupmama.weather.common

import org.junit.Before
import org.junit.Test

class AppConstantsTest {

    @Test
    fun `city id's should not have a trailing comma`(){
        val comma:Char = ',';
        val endingChar = AppConstants.CITY_IDS[AppConstants.CITY_IDS.length - 1]
        assert(comma != endingChar){
            "The city id's have a trailing comma"
        }
    }

}