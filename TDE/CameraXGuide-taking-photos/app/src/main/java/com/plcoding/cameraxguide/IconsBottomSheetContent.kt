package com.plcoding.cameraxguide

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonochromePhotos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


enum class BottomSheetIconType{
    GRAY, FILTER, CONTRAST, EDGE
}

@Composable
fun BottomSheetIconScaffold(
    modifier: Modifier = Modifier,
    onIconClick: (BottomSheetIconType) -> Unit
){
    Row (
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        BottomSheetIcon(
            icon = Icons.Filled.MonochromePhotos,
            type = BottomSheetIconType.GRAY,
            onClick = onIconClick
        )
    }
}

@Composable
fun BottomSheetIcon(
    icon:ImageVector,
    type: BottomSheetIconType,
    onClick:(BottomSheetIconType) -> Unit
){
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier
            .clickable { onClick(type) }
            .padding(8.dp))
}