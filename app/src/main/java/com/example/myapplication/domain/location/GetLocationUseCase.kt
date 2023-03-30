package com.example.myapplication.domain.location

class GetLocationUseCase(
    private val repository: LocationRepository
) {

    suspend operator fun invoke() = repository.getLocation()
}