

package com.example.newspulse.core.presentaion.designsystem.components
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newspulse.R
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalPadding
import com.example.newspulse.core.presentaion.designsystem.theme.Padding

@Composable
fun NewsPulseTopBar(
    modifier: Modifier = Modifier,
    title: String,
    dimensions: Dimensions,
    padding: Padding,
    hasBackArrow: Boolean = false,
    hasCategory: Boolean = false,
    onBackClick: () -> Unit = {},
    onCategoryClick: () -> Unit = {}
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.topBarHeight)
            .padding(padding.tinyPadding)
            .padding(start = padding.smallPadding, top = padding.topBarPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(hasBackArrow){
            IconButton(
                onClick = { onBackClick() },
                enabled = true,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.arrow_back),
                )
            }
        }

        Text(
            text = title,
            modifier = Modifier.padding(start = padding.tinyPadding),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        if(hasCategory){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ){
                IconButton(
                    onClick = { onCategoryClick() },
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Category,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(R.string.arrow_back),
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun NewsPulseTopBarPreview() {
    NewsPulseTopBar(
        modifier = Modifier,
        title = "Test",
        padding = LocalPadding.current,
        dimensions = LocalDimensions.current,
        hasBackArrow = true,
        onBackClick = { },
        hasCategory = true,
        onCategoryClick = { }
    )
}
