package gh.cloneconf.nedium.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.PostScreenDestination
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gh.cloneconf.nedium.MainActivity
import gh.cloneconf.nedium.R
import gh.cloneconf.nedium.screens.search.posts.PostResults

@Destination(
    deepLinks = [
        DeepLink(uriPattern = "https://medium.com/search"),
        DeepLink(uriPattern = "https://medium.com/search?q={defaultQ}"),
    ]
)
@Composable
fun SearchScreen(navigator: DestinationsNavigator, defaultQ: String = "") {

    val focusManager = LocalFocusManager.current


    Scaffold {
        Column {

            var q by rememberSaveable { mutableStateOf(defaultQ) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.drawBehind {
                    // see: https://stackoverflow.com/questions/68592618/how-to-add-border-on-bottom-only-in-jetpack-compose
                    val strokeWidth = 2
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        Color.Gray,
                        Offset(0f, y),
                        Offset(size.width, y),
                        2f
                    )
                }
            ) {


                OutlinedTextField(
                    placeholder = { Text(stringResource(R.string.search_for_articles)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp, 3.dp, 10.dp, 3.dp),
                    value = q,
                    onValueChange = { q = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                        imeAction = ImeAction.Default
                    ),
                    maxLines = 1,
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = { navigator.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, null)
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    trailingIcon = {
                        if (q.isNotEmpty()) IconButton(onClick = { q = "" }) {
                            Icon(Icons.Default.Close, null)
                        }
                    },
                )


            }




            PostResults(q = q) {
                navigator.navigate(PostScreenDestination(it))
            }


        }
    }
}
