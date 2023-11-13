package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.content.Context
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.pm20232.aulavirtual.configs.BackendClient
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import pe.edu.ulima.pm20232.aulavirtual.services.UserService2
import org.json.JSONObject
import pe.edu.ulima.pm20232.aulavirtual.configs.HttpStdResponse
import pe.edu.ulima.pm20232.aulavirtual.configs.PreferencesManager
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage
import java.util.concurrent.Flow


class LoginScreenViewModel(private val context: Context): ViewModel() {
    var user: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var message: String by mutableStateOf("")
    var bottomSheetCollapse: Boolean by mutableStateOf(true)
    var termsAndConditionsChecked: Boolean by mutableStateOf(false)

    private val userService = BackendClient.buildService(UserService2::class.java)
    private val coroutine: CoroutineScope = viewModelScope
    val dataStore = UserStorage(context)

    fun access(navController: NavController): Unit{
        println("BTN PRESSED")
        println(user)
        println(password)
        println("BTN PRESSED")
        println(user)
        println(password)
        coroutine.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = userService.findOne(user, password)?.execute()
                    if (response != null) {
                        if (response.body()!!.success == true) {
                            val responseData = response.body()!!
                            val jsonData = JSONObject(responseData.data)
                            val userId = jsonData.getInt("user_id")
                            val memberId = jsonData.getInt("member_id")
                            // localstorage
                            dataStore.saveUserId(userId)
                            println("routine?user_id=${userId}&member_id=${memberId}")
                            launch(Dispatchers.Main) {
                                navController.navigate("routine?user_id=${userId}&member_id=${memberId}")
                            }
                        } else {
                            // Maneja errores
                            message = response.body()!!.message
                        }
                    }
                }
            } catch (e: Exception) {
                //val crashlytics = FirebaseCrashlytics.getInstance()
                println("1 +++++++++++++++++++++++++++++++++++++++++++++++++++++")
                //val exception = Exception(e.printStackTrace().toString())
                val crashlytics = Firebase.crashlytics
                crashlytics.setCustomKeys {
                    key("current_level", 3)
                    key("last_UI_action", "logged_in")
                }
                crashlytics.recordException(e)
                val db = FirebaseFirestore.getInstance()
                val document = HashMap<String, Any>()
                document["usuario"] = user
                document["contraseña"] = password

                // Agrega un nuevo documento con un ID automático
                /*
                db.collection("usuarios")
                    .add(document)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Firestore", "Documento agregado con ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error al agregar el documento", e)
                    }
                e.printStackTrace()
                */
                /*
                db.collection("usuarios")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            Log.d("Firestore", "${document.id} => ${document.data}")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error al obtener documentos", e)
                    }
                * */
                /*
                val userRef = db.collection("usuarios").document("ID_DEL_DOCUMENTO_A_ACTUALIZAR")

                val updates = HashMap<String, Any>()
                updates["email"] = "nuevo_email@example.com"

                userRef.update(updates)
                    .addOnSuccessListener {
                        Log.d("Firestore", "Documento actualizado")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error al actualizar el documento", e)
                    }
                * */

                val userRef = db.collection("usuarios").document("gCG87ysGzINpftXlic7B")

                userRef.delete()
                    .addOnSuccessListener {
                        Log.d("Firestore", "Documento eliminado")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error al eliminar el documento", e)
                    }

                //Firebase.crashlytics.log("message")
                println("2 +++++++++++++++++++++++++++++++++++++++++++++++++++++")
                //crashlytics.recordException(exception)
            } finally {

            }
        }
        /*
        if(user == "admin" && password == "123"){
            navController.navigate("profile")
        }else{
            val userService: UserService = UserService()
            val userId = userService.checkUser(user, password)
            if(userId != 0){
                navController.navigate("home?user_id=${userId}")
            }else{
                message = "Usuario y contraseña no coinciden"
            }
        }
         */
    }
}