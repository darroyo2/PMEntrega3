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
import pe.edu.ulima.pm20232.aulavirtual.models.Integrante
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.RoutineScreenViewModel


@Composable
fun TopNavigationBar(navController: NavController, screens: List<TopBarScreen>, routineViewModel: RoutineScreenViewModel) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var isDialogVisible by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "ULima GYM") },
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
                onDismissRequest = {
                    isMenuExpanded = false
                },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                screens.forEachIndexed { index, item ->
                    if (item.route == "about") {
                        DropdownMenuItem(
                            onClick = {
                                // Handle menu item click
                                isMenuExpanded = false
                                isDialogVisible = true
                            }
                        ) {
                            Text(text = item.title)
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

    // Mostrar el cuadro de diálogo cuando isDialogVisible es true
    if (isDialogVisible) {
        // Mostrar el cuadro de diálogo y pasar un callback para restablecer isDialogVisible
        routineViewModel.integrantes.value?.let {
            ShowMembers(integrantes = it) {
                // Restablecer isDialogVisible a false cuando se cierra el cuadro de diálogo
                isDialogVisible = false
            }
        }
    }
}



@Composable
fun ShowMembers(integrantes: List<Integrante>, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss.invoke() }
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
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Integrantes del Grupo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                )

                integrantes.forEach { integrante ->
                    Text(
                        text = "${integrante.codigo} - ${integrante.nombre}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
