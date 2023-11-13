package pe.edu.ulima.pm20232.aulavirtual.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import pe.edu.ulima.pm20232.aulavirtual.R
import pe.edu.ulima.pm20232.aulavirtual.configs.BottomBarScreen

@Composable
fun BottomNavigationBar(navController: NavHostController, screens: List<BottomBarScreen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    var isClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (screen.route == "share") {
        BottomNavigationItem(
            label = {
                Text(text = screen.title)
            },
            icon = {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Navigation Icon"
                )
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true,
            unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
            onClick = {
                isClicked = true
            })
        if (isClicked) {
            ShowShareDialog ({ isClicked = false}, context)
        }

    } else {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
            }
}

@Composable
fun ShowShareDialog(testDialog: @Composable () -> Unit, context: Context) {
    var isDialogOpen by remember { mutableStateOf(true) }

    if (isDialogOpen) {
        // Crear un diálogo personalizado para compartir
        Dialog(
            onDismissRequest = {
                isDialogOpen = false
            }
        ) {
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp)
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
                        text = "Gracias por compartir",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ULIMAIcon(
                            iconRes = R.drawable.ic_whatsapp,
                            onClick = { shareOnWhatsApp(context, "https://github.com/Sugawara20/Entrega2") })
                        ULIMAIcon(
                            iconRes = R.drawable.ic_facebook,
                            onClick = { shareOnFacebook(context, "https://github.com/Sugawara20/Entrega2") })
                    }

                    // Agregar un botón de cierre
                    Button(
                        onClick = {
                            isDialogOpen = false
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Cerrar")
                    }
                }
            }
        }
    } else {
        // Botón para abrir el diálogo
        isDialogOpen = false
    }
}


@Composable
fun ULIMAIcon(iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "ULIMA Icon",
            modifier = Modifier
                .size(48.dp)
                .clickable { onClick() } // Llama a la función al hacer clic
        )
        Text(text = "ULIMA")
    }
}
fun shareOnFacebook(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)

    intent.putExtra(Intent.EXTRA_TITLE, text)
    intent.setPackage("com.facebook.katana") // Package name de Facebook
    //   if (isAppInstalled(context, "com.facebook.katana")) {
    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
        // Manejar errores, como si la aplicación de Facebook no está instalada

        Toast.makeText(context, "Excepción: ${e.javaClass.simpleName}" , Toast.LENGTH_SHORT).show()
        println("Excepción: ${e.javaClass.simpleName}")
    }
}
fun shareOnWhatsApp(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    intent.putExtra(Intent.EXTRA_TITLE, text)
    intent.setPackage("com.whatsapp") // Package name de WhatsApp

    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
        // Manejar errores, como si la aplicación de WhatsApp no está instalada
        Toast.makeText(context, "La aplicación de WhatsApp no está instalada", Toast.LENGTH_SHORT).show()
    }
}
