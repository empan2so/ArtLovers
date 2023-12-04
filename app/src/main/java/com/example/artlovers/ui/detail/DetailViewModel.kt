package com.example.artlovers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artlovers.data.model.Artwork
import com.example.artlovers.data.repository.ArtworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * View model for detail screen state
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ArtworkRepository,
    private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _artwork = MutableLiveData<Artwork?>()
    val artwork: LiveData<Artwork?>
        get() = _artwork

    fun updateModel(artwork: Artwork?) {
        Log.i("DetailViewModel", "Updating model ${artwork}")
        _artwork.postValue(artwork)
    }

    fun updateDetailFromNetwork(id: Long, isLoved: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            repository.getArtworkFromRemote(id.toString()).collect {
                val artwork = it?.copy(isLoved = isLoved)
                updateModel(artwork)
                if (artwork?.isLoved == true) {
                    repository.updateIsLoved(artwork)
                }
            }
        }
    }

    //TODO get artwork from db if exists before making network request
//    fun updateDetailFromDB(id: Long) {
//        viewModelScope.launch {
//            _artwork.postValue()
//            repository.getArtworkFromDB(id).let {
//                updateModel(it.value)
//            }
//        }
//    }

    fun updateIsLoved() {
        viewModelScope.launch(ioDispatcher) {
            artwork.value?.let {
                val updated = it.copy(isLoved = it.isLoved?.not())
                repository.updateIsLoved(updated)
                updateModel(updated)
            }
        }
    }
}