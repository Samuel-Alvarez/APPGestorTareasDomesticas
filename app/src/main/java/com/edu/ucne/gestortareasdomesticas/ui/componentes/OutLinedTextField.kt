package com.edu.ucne.gestortareasdomesticas.ui.componentes

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
@Composable
fun OutLinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMsg: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.outlinedShape,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = Color(0xFF77BDF5),
        disabledTextColor = Color(0xFF03A9F4),
        containerColor = Color.Transparent,
        cursorColor = Color(0xFF5E7C94),
        errorCursorColor = Color.Red,
        selectionColors = LocalTextSelectionColors.current,
        focusedBorderColor = Color(0xFF03A9F4),
        unfocusedBorderColor = Color(0xFF03A9F4),
        disabledBorderColor = Color(0xFF1B4D75),
        errorBorderColor = Color.Red,
        focusedLeadingIconColor = Color(0xFF1B4D75),
        unfocusedLeadingIconColor = Color(0xFF1B4D75),
        disabledLeadingIconColor = Color(0xFF1B4D75),
        errorLeadingIconColor = Color.Red,
        focusedTrailingIconColor = Color(0xFF1B4D75),
        unfocusedTrailingIconColor = Color(0xFF1B4D75),
        disabledTrailingIconColor = Color(0xFF61ADCF),
        errorTrailingIconColor = Color.Red,
        focusedLabelColor = Color(0xFF71B2CF),
        unfocusedLabelColor = Color(0xFF35667C),
        disabledLabelColor = Color(0xFF5086B1),
        errorLabelColor = Color.Red,
        placeholderColor = Color(0xFF6086A3),
        disabledPlaceholderColor = Color(0xFF63A0D1)
    )
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        Color.Black
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            Color.Black,
            Color.Black
        )
    ) {
        @OptIn(ExperimentalMaterial3Api::class)
        (BasicTextField(
            value = value,
            modifier = if (label != null) {
                modifier
                    .semantics(mergeDescendants = true) {}
                    .padding(top = 8.dp)
            } else {
                modifier
            }
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(Color.Black),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    supportingText = supportingText,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    container = {
                        TextFieldDefaults.OutlinedBorderContainerBox(
                            true,
                            isError,
                            interactionSource,
                            colors,
                            shape
                        )
                    }
                )
            }
        ))
        if(isError)
        {
            Row(horizontalArrangement = Arrangement.Center,modifier= Modifier.fillMaxWidth()){
                Text("*$errorMsg*", modifier= Modifier.padding(vertical=4.dp), color = Color.Red )
            }
        }
    }
}