package pe.edu.ulima.pm20232.aulavirtual.screens

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import pe.edu.ulima.pm20232.aulavirtual.components.ButtonWithIcon
import pe.edu.ulima.pm20232.aulavirtual.components.TextFieldWithLeadingIcon
import pe.edu.ulima.pm20232.aulavirtual.components.TextFieldWithLeadingIcon2
import pe.edu.ulima.pm20232.aulavirtual.services.MemberService
import pe.edu.ulima.pm20232.aulavirtual.services.UserService
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.Gray1200
import java.lang.reflect.Member

@Composable
fun ImageView(url: String, height: Int, width: Int) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            // You can apply transformations here if needed
            transformations(CircleCropTransformation())
        }
    )
    Image(
        painter = painter,
        contentDescription = null, // Set a proper content description if required
        modifier = Modifier.size(width.dp, height.dp)
    )
}
@Composable
fun CustomToolbar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono de flecha para retroceder a la izquierda
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Retroceder",
            modifier = Modifier.padding(end = 16.dp) .clickable { navController.navigate("home") }
        )

        // Icono de lápiz a la derecha
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar"
        )
    }
}

@Composable
fun ProfileScreen2(navController: NavController){
    val imageUrl = "https://s.hs-data.com/bilder/spieler/gross/17450.jpg?fallback=png" // Replace with your image URL
    var temp =""
    var memberx= ArrayList<Member> ()
    //memberx=serviceMemberUser.getMember(1)
    CustomToolbar(navController)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(start = 35.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        ImageView(url = imageUrl, width = 100, height = 100)
        Column() {
            Text("",modifier = Modifier.padding(start = 30.dp, top = 20.dp)
                ,fontSize = 20.sp, fontWeight = FontWeight.Bold)
            TextFieldWithLeadingIcon2(
                leadingIcon = Icons.Default.AccountBox, // Replace with your desired icon
                placeholder = "ctevez Crossfitero",
                text = temp,
                onTextChanged = {
                    temp = it
                }
            )


        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(top = 120.dp, start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column() {
            TextFieldWithLeadingIcon2(
                leadingIcon = Icons.Default.Call, // Replace with your desired icon
                placeholder = "999 888 777",
                text = temp,
                onTextChanged = {
                    temp = it
                }
            )
            TextFieldWithLeadingIcon2(
                leadingIcon = Icons.Default.Email, // Replace with your desired icon
                placeholder = "20041122@aloe.ulima.edu.pe",
                text = temp,
                onTextChanged = {
                    temp = it
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /* Agrega la lógica de actualización de datos aquí */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Actualizar Datos",
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )}
            }

            Box() {
                Row(){
                    Column(modifier = Modifier
                        .height(65.dp)
                        .padding(start = 15.dp, end = 0.dp)){
                        Text("22", fontSize = 30.sp , fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally ))
                        Text("Ejercicios Asignados", fontSize = 12.sp , modifier = Modifier.align(Alignment.CenterHorizontally ))
                    }
                    Column(modifier = Modifier
                        .height(85.dp)
                        .padding(start = 25.dp, end = 10.dp)){
                        Text("4" ,fontSize = 30.sp ,fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally ))
                        Text("Partes del Cuerpo Entrenadas", fontSize = 12.sp , modifier = Modifier.align(Alignment.CenterHorizontally ))
                    }
                }
            }
        }
    }
    Row(modifier = Modifier.padding(top = 675.dp, start = 50.dp, end = 50.dp)){
        Button(
            onClick = { /* Agrega la lógica de actualización de datos aquí */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cerrar Sessión",
                style = MaterialTheme.typography.button,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )}
    }
}