package com.lucasprioste.rickandmorty.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lucasprioste.rickandmorty.R
import com.lucasprioste.rickandmorty.core.containsSpecialCharacters
import com.lucasprioste.rickandmorty.domain.model.FilterInfo
import com.lucasprioste.rickandmorty.domain.model.FilterState
import com.lucasprioste.rickandmorty.domain.model.Gender
import com.lucasprioste.rickandmorty.domain.model.Status
import com.lucasprioste.rickandmorty.domain.use_case.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharacters: GetCharacters
): ViewModel() {

    private val _filtersState = MutableStateFlow(
        FilterState(
            searchName = "",
            filterStatus = listOf(
                FilterInfo(resourceId = Status.Alive.resourceId, isSelected = false, requestName = Status.Alive.name),
                FilterInfo(resourceId = Status.Dead.resourceId, isSelected = false, requestName = Status.Dead.name),
                FilterInfo(resourceId = Status.Unknown.resourceId, isSelected = false, requestName = Status.Unknown.name),
            ),
            filterGender = listOf(
                FilterInfo(resourceId = Gender.Male.resourceId, isSelected = false, requestName = Gender.Male.name),
                FilterInfo(resourceId = Gender.Female.resourceId, isSelected = false, requestName = Gender.Female.name),
                FilterInfo(resourceId = Gender.Genderless.resourceId, isSelected = false, requestName = Gender.Genderless.name),
                FilterInfo(resourceId = Gender.Unknown.resourceId, isSelected = false, requestName = Gender.Unknown.name),
            )
        )
    )
    val filterState = _filtersState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val characters = filterState.flatMapLatest {
        val selectedStatus = it.filterStatus.find { it.isSelected }
        val selectedGender = it.filterGender.find { it.isSelected }

        getCharacters.getAll(
            searchName = it.searchName,
            gender = selectedGender?.requestName,
            status = selectedStatus?.requestName
        )
    }.cachedIn(viewModelScope).cancellable()

    fun onEvent(event: HomeContract.HomeEvent){
        when(event){
            is HomeContract.HomeEvent.OnSearch -> {
                if (containsSpecialCharacters(event.query)) return
                _filtersState.update { it.copy(searchName = event.query) }
            }
            is HomeContract.HomeEvent.OnChangeFilterGender -> {
                _filtersState.update { filter ->
                    filter.copy(filterGender = filter.filterGender.map {
                        if (it == event.item)
                            it.copy(isSelected = !it.isSelected)
                        else
                            it.copy(isSelected = false)
                    })
                }
            }
            is HomeContract.HomeEvent.OnChangeFilterStatus -> {
                _filtersState.update { filter ->
                    filter.copy(filterStatus = filter.filterStatus.map {
                        if (it == event.item)
                            it.copy(isSelected = !it.isSelected)
                        else
                            it.copy(isSelected = false)
                    })
                }
            }
        }
    }
}