package com.firstzoom.sleep.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstzoom.sleep.database.SleepDatabase
import com.firstzoom.sleep.database.SleepDatabaseDao
import com.firstzoom.sleep.database.SleepNight
import kotlinx.coroutines.launch

class SleepQualityViewModel(private val nightKey:Long, val database:SleepDatabaseDao) : ViewModel() {
    var _navigateToSleepTracker=MutableLiveData<Boolean?>()
    val navigateToSleepTracker:LiveData<Boolean?>
            get()=_navigateToSleepTracker

    fun doneNavigating(){
        _navigateToSleepTracker.value=null
    }
    fun onSetQuality(quality: Int) {
        viewModelScope.launch {
            var night=database.get(nightKey)?:return@launch
            night?.sleepQuality=quality
            _navigateToSleepTracker.value=true
        }
    }

}