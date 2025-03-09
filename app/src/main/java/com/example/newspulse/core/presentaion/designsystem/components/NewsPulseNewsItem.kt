package com.example.newspulse.core.presentaion.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.Image_Place_Holder
import com.example.newspulse.core.presentaion.designsystem.theme.Image_Place_Holder_Error
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalPadding
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme
import com.example.newspulse.core.presentaion.designsystem.theme.Padding

@Composable
fun NewsPulseNewsItem(
    modifier: Modifier = Modifier,
    padding: Padding,
    dimensions: Dimensions,
    imageUrl: String,
    title: String,
    description: String,
    source: String,
    publishedAt: String,
    onFavouriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.smallPadding)
            .height(dimensions.itemHeight)
            .clickable { onItemClick() }
    ) {
        Column(
            modifier = Modifier.padding(end = padding.smallPadding)
        ){
            AsyncImage(
                model = imageUrl,
                modifier = modifier
                    .height(dimensions.imageSizeHeight)
                    .width(dimensions.imageSizeWidth)
                    .clip(RoundedCornerShape(dimensions.imageRadius)),
                contentDescription = "News Image",
                contentScale = ContentScale.Crop,
                placeholder = Image_Place_Holder,
                error = Image_Place_Holder_Error
            )
            Spacer(modifier = Modifier.height(padding.smallPadding))
            Text(
                text = source,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(padding.smallPadding))
            Text(
                text = publishedAt,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            modifier = Modifier.weight(0.52f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(padding.smallPadding))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            modifier = Modifier
                .weight(0.08f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NewsPulseFavouriteButton(
                modifier = Modifier,
                dimensions = dimensions,
                isFavourite = false,
                onFavouriteClick = onFavouriteClick
            )
        }
    }
}

@Preview
@Composable
private fun NewsItemPreview() {
    NewsPulseTheme {
        NewsPulseNewsItem(
            padding = LocalPadding.current,
            dimensions = LocalDimensions.current,
            imageUrl = "Test",
            title = "Title",
            description = "Description",
            source = "Source",
            publishedAt = "Published at",
            onFavouriteClick = {},
            onItemClick = {},
        )
    }
}