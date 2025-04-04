package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComposable(
    modifier: Modifier = Modifier,
    query: String,
    isExpanded: Boolean,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    placeholder: @Composable (() -> Unit),
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit),
    content: @Composable ColumnScope.() -> Unit
) {
    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = isExpanded,
                onExpandedChange = onExpandedChange,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        DockedSearchBar(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentHeight(Alignment.Top),
            inputField = inputField,
            expanded = isExpanded,
            onExpandedChange = onExpandedChange,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            content = content
        )
    }
}