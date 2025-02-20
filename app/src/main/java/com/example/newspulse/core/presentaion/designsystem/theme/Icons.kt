package com.example.newspulse.core.presentaion.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.newspulse.R


val Logo: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.news_logo)

val Navigation_News: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.newspaper_24)

val Navigation_Search: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.search_24)

val Navigation_Save: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.save_24)



val Image_Place_Holder: Painter
    @Composable
    get() = painterResource(R.drawable.image_placeholder_24)
val Image_Place_Holder_Error: Painter
    @Composable
    get() = painterResource(R.drawable.image_place_holder_error_24)

