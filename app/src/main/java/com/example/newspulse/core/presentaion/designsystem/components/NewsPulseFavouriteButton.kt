package com.example.newspulse.core.presentaion.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.Padding

@Composable
fun NewsPulseFavouriteButton(
    modifier: Modifier = Modifier,
    dimensions: Dimensions,
    isFavourite: Boolean = false,
    onFavouriteClick: () -> Unit
){
    IconButton(
        onClick = { onFavouriteClick() },
        modifier = modifier.size(dimensions.favouriteImageSize),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Icon(
            imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favourite",
            tint = if (isFavourite) Color.Red else Color.Gray
        )
    }
}


@Preview
@Composable
private fun NewsPulseFavouriteButtonPreview() {
    NewsPulseFavouriteButton(
        modifier = Modifier,
        dimensions = LocalDimensions.current,
        isFavourite = false,
        onFavouriteClick = { }
    )
}