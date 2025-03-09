package com.example.newspulse.core.domain.errorwrapper

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError{
        DISK_IS_FULL,            // Device storage is full
        NO_DATA,                 // No cached data available
        DATABASE_FAILURE,        // Unable to read/write from database
        DATA_CORRUPTED,          // Data format is invalid
        UNAUTHORIZED_ACCESS,     // Access to local DB is restricted
        CONCURRENT_MODIFICATION  // Multiple conflicting database operations
    }
}