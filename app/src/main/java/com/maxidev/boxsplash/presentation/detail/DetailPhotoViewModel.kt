package com.maxidev.boxsplash.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxidev.boxsplash.domain.handlers.Resource
import com.maxidev.boxsplash.domain.model.PhotoIdDomain
import com.maxidev.boxsplash.domain.repository.PhotoDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailPhotoViewModel @Inject constructor(
    private val repository: PhotoDetailRepository
): ViewModel() {

    private val _detailState: MutableStateFlow<Resource<PhotoIdDomain>> =
        MutableStateFlow(Resource.Loading<PhotoIdDomain>())
    val detailState: StateFlow<Resource<PhotoIdDomain>> = _detailState.asStateFlow()

//    init {
//        photoDetailStatus()
//    }

    fun photoDetailStatus(id: String) {
        viewModelScope.launch {
            _detailState.value = Resource.Loading()

            _detailState.value = try {
                Resource.Success(data = repository.fetchPhotoDetail(id))
            } catch (e: IOException) {
                Resource.Error(message = e.message.toString())
            } catch (e: HttpException) {
                Resource.Error(message = e.message.toString())
            } catch (e: Exception) {
                Resource.Error(message = e.message.toString())
            }
        }
    }
}