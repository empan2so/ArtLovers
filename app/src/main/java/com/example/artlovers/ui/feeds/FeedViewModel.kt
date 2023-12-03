package com.example.artlovers.ui.feeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artlovers.data.model.Artwork
import com.example.artlovers.data.repository.ArtworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * View model for home and loved state
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: ArtworkRepository,
    private val ioDispatcher: CoroutineDispatcher,
    ): ViewModel() {

    private val _homeArtworks = MutableLiveData<List<Artwork>?>()
    val homeArtworks: LiveData<List<Artwork>?>
        get() = _homeArtworks

    private val _lovedArtworks = MutableLiveData<List<Artwork>?>()
    val lovedArtworks: LiveData<List<Artwork>?>
        get() = repository.lovedArtwork

    private var isFirstLoad = true

    init {
        _homeArtworks.postValue(emptyList())
        if (isFirstLoad) {
            loadFromNetwork()
            isFirstLoad = false
        }
    }

    private fun updateHomeModel(artworks: List<Artwork>) {
        Log.i("FeedViewModel", "Updating home feed model ${artworks.size}")
        _homeArtworks.postValue(artworks)
    }

    fun loadFromNetwork() {
        viewModelScope.launch(ioDispatcher) {
            updateHomeModel(repository.getListArtworkRemote(1, 50))
        }
    }

    fun loadSearchResults(search: String) {
        viewModelScope.launch(ioDispatcher) {
            if (search.isNotBlank()) {
                updateHomeModel(repository.getSearchResultsRemote(search))
            } else {
                loadFromNetwork()
            }
        }
    }

    fun updateIsLoved(artwork: Artwork) {
        viewModelScope.launch {
            repository.updateIsLoved(artwork)
        }
    }

}