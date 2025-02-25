@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newspulse.core.presentaion.designsystem.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalPadding
import com.example.newspulse.core.presentaion.designsystem.theme.Padding

@Composable
fun NewsPulseSpinner(
    modifier: Modifier = Modifier,
    dimensions: Dimensions,
    padding: Padding,
    options: Map<String,String>,
    label: String = "Choose an option",
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary)
                    },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow"
                )
            },
            modifier = Modifier
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth().height(dimensions.categoryHeight)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = dimensions.categoryMenuHeightRange)
        ) {
            Column(
                modifier = Modifier.padding(end = padding.smallPadding)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option.key)
                        },
                        onClick = {
                            onOptionSelected(option.value)
                            expanded = false
                        },
                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NewsPulseSpinnerPreview() {
    NewsPulseSpinner(
        modifier = Modifier,
        dimensions = LocalDimensions.current,
        padding = LocalPadding.current,
        options = emptyMap(),
        onOptionSelected = {},
        selectedOption = "Select an option"
    )
}
