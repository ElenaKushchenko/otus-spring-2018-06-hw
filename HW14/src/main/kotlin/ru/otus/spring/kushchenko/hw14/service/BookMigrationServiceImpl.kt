package ru.otus.spring.kushchenko.hw14.service

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BookMigrationServiceImpl(
    private val jobLauncher: JobLauncher,
    @Qualifier("bookFromJpaToMongo") private val migrationJob: Job
) : BookMigrationService {
    override fun migrateFromJpaToMongo() {
        jobLauncher.run(migrationJob, JobParameters())
    }
}
