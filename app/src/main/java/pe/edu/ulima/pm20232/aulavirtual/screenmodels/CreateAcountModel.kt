package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import pe.edu.ulima.pm20232.aulavirtual.services.UserService
import pe.edu.ulima.pm20232.aulavirtual.services.MemberService

class CreateAcountModel: ViewModel() {
    var names: String by mutableStateOf("")
    var lastnames: String by mutableStateOf("")
    var dni: String by mutableStateOf("")
    var email: String by mutableStateOf("")
    var phone: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var password2: String by mutableStateOf("")

    var message: String by mutableStateOf("")
    var bottomSheetCollapse: Boolean by mutableStateOf(true)
    var termsAndConditionsChecked: Boolean by mutableStateOf(false)

    fun access(navController: NavController, applicationContext: Context): Unit{

        println("BTN PRESSED")
        println(names)
        println(lastnames)
        if(names == "admin" && lastnames == "admin"){
            navController.navigate("login")
        }
            else{
                println("Error 404, llamar a soporte")
            }
        }
    }
