package pe.edu.ulima.pm20232.aulavirtual.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import pe.edu.ulima.pm20232.aulavirtual.configs.TopBarScreen

@Composable
fun TopNavigationBar(navController: NavController, screens: List<TopBarScreen>) {

    var isMenuExpanded by remember { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false)}
    TopAppBar(

        title = { Text(text = "ULima GYM") },
        /*navigationIcon = {
            IconButton(
                onClick = {
                    // Handle navigation icon click (e.g., open drawer or navigate back)
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        },*/
        actions = {
            IconButton(
                onClick = {
                    isMenuExpanded = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                screens.forEachIndexed { index, item ->
                    if (item.route == "about") {
                        DropdownMenuItem(
                            onClick = {
                                // Handle menu item click
                                isMenuExpanded = false
                                isClicked = true
                            }
                        ) {
                            Text(text = item.title)
                        }
                        if (isClicked) {
                            ShowMembers { isClicked = false}
                        }
                    } else {
                        DropdownMenuItem(
                            onClick = {
                                // Handle menu item click
                                isMenuExpanded = false
                                navController.navigate(item.route)
                            }
                        ) {
                            Text(text = item.title)
                        }
                    }
                }
            }
        },
    )
}
@Composable
fun ShowMembers(testDialog : @Composable () -> Unit)  {
    Dialog(
        onDismissRequest = { /* No hacer nada al cerrar */ }
    ) {
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .border(1.dp, Color.Black) // Agrega un borde negro
                    .padding(8.dp)
            ) {
                Text(
                    text = "Integrantes del Grupo\n " +
                            "20202370 - Diego Arroyo\n" +
                            "20152154 - Brayan Oropeza\n"+
                            "20018950 - Jesus Espinoza\n" +
                            "20105566 - Adrian Alcala\n" +
                            "20261514 - Axel Coder\n",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                )

            }
        }
    }
}