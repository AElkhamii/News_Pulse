package com.example.newspulse.core.presentaion.ui

import com.example.newspulse.R
import com.example.newspulse.core.domain.errorwrapper.DataError

fun DataError.asUiText(): UiText{
    return when(this){
        DataError.Local.DISK_IS_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
            R.string.error_unauthorized
        )

        DataError.Network.CONFLICT -> UiText.StringResource(
            R.string.error_disk_full
        )
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_many_request
        )
        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )
        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )
        else -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}