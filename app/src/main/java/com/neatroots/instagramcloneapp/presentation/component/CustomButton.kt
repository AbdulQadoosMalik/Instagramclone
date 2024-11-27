package com.neatroots.instagramcloneapp.presentation.component


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neatroots.instagramcloneapp.presentation.util.Const.MediumLargeFontSize
import com.neatroots.instagramcloneapp.ui.theme.AccentColor
import com.neatroots.instagramcloneapp.ui.theme.IconDark


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor),
        shape = RoundedCornerShape(size = 9.dp),
        onClick = onClick
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = IconDark)
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                fontSize = MediumLargeFontSize,
                color = Color.White,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }
}