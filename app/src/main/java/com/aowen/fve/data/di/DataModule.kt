package com.aowen.fve.data.di

import com.aowen.fve.data.repository.FveVehicleRepository
import com.aowen.fve.data.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsVehicleRepository(
        vehicleRepositoryImpl: FveVehicleRepository
    ): VehicleRepository
}