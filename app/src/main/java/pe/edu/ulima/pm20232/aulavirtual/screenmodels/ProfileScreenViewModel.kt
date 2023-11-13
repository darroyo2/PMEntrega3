package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.pm20232.aulavirtual.configs.PreferencesManager

class ProfileScreenViewModel(): ViewModel() {
    lateinit var preferencesManager: PreferencesManager

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri
    var userId: Int by mutableStateOf(0)

    fun setImageUri(uri: Uri?) {
        viewModelScope.launch {
            _imageUri.value = uri.toString()
        }
    }

    fun loadUserId(){
        println("1 +++++++++++++++++++++++++++")
        println(preferencesManager.getUserId())
        println("2 +++++++++++++++++++++++++++")
    }
}
