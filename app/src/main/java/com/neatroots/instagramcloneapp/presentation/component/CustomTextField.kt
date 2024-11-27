package com.neatroots.instagramcloneapp.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.neatroots.instagramcloneapp.ui.theme.AccentColor
import com.neatroots.instagramcloneapp.ui.theme.FormFieldBgDark
import com.neatroots.instagramcloneapp.ui.theme.FormFieldBgLight
import com.neatroots.instagramcloneapp.ui.theme.FormFieldBorderDark
import com.neatroots.instagramcloneapp.ui.theme.FormFieldBorderLight
import com.neatroots.instagramcloneapp.ui.theme.IconDark
import com.neatroots.instagramcloneapp.ui.theme.IconLight


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisible by remember { mutableStateOf(false) }
    val keyboardTrans = if (visualTransformation == PasswordVisualTransformation()) {
        if (passwordVisible) KeyboardType.Password else KeyboardType.Password
    } else {
        keyboardType
    }
    val transformation = if (visualTransformation == PasswordVisualTransformation()) {
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    } else {
        visualTransformation
    }

    TextField(
        modifier = modifier.border(
            width = 1.dp,
            color = if (darkTheme) FormFieldBorderDark else FormFieldBorderLight,
            shape = RoundedCornerShape(10)
        ),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = hint, color = if (darkTheme) IconDark else IconLight)
        },
        singleLine = true,
        visualTransformation = transformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardTrans,
            imeAction = ImeAction.Done
        ),

        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = if (darkTheme) IconDark else IconLight,
            trailingIconColor = if (darkTheme) IconDark else IconLight,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = AccentColor,
            backgroundColor = if (darkTheme) FormFieldBgDark else FormFieldBgLight
        ),
        trailingIcon = {
            if (visualTransformation == PasswordVisualTransformation()) {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = if (darkTheme) IconDark else IconLight)
                }
            }
        }
    )
}
