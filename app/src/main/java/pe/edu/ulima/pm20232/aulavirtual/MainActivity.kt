package pe.edu.ulima.pm20232.aulavirtual

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import pe.edu.ulima.pm20232.aulavirtual.components.BottomNavigationBar
import pe.edu.ulima.pm20232.aulavirtual.components.TopNavigationBar
import pe.edu.ulima.pm20232.aulavirtual.configs.BottomBarScreen
import pe.edu.ulima.pm20232.aulavirtual.configs.PreferencesManager
import pe.edu.ulima.pm20232.aulavirtual.configs.TopBarScreen
import pe.edu.ulima.pm20232.aulavirtual.factories.LoginScreenViewModelFactory
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.*
import pe.edu.ulima.pm20232.aulavirtual.screens.*
import pe.edu.ulima.pm20232.aulavirtual.screens.CreateAcountScreen
import pe.edu.ulima.pm20232.aulavirtual.storages.UserStorage
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.AulaVirtualTheme

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager
    private val ExercisesScreenModel by viewModels<ExercisesScreenViewModel>()
    private val CreateAcountModel by viewModels<CreateAcountModel>()
    private val loginScrennViewModel: LoginScreenViewModel by viewModels {
        LoginScreenViewModelFactory(applicationContext)
    }
    private val profileScrennViewModel by viewModels<ProfileScreenViewModel>()
    private val homeScrennViewModel by viewModels<HomeScreenViewModel>()
    private val pokemonDetailScrennViewModel by viewModels<PokemonDetailScreenViewModel>()
    private val routineScreenViewModel by viewModels<RoutineScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        preferencesManager = PreferencesManager(applicationContext)
        // set preferencesManager in viewModels
        profileScrennViewModel.preferencesManager = preferencesManager

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        setContent {
            AulaVirtualTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val blackList: List<String> = listOf("profile", "login")
                    val currentRoute = navBackStackEntry?.destination?.route
                    var showDialog by remember { mutableStateOf(false) }
                    Scaffold(
                        topBar = {
                            if(blackList.contains(currentRoute) == false) {
                                val screens: List<TopBarScreen> = listOf(
                                    TopBarScreen(
                                        route = "profile2",
                                        title = "Editar Perfil",
                                    ),
                                    TopBarScreen(
                                        route = "about",
                                        title = "Acerca de",
                                    ),
                                    TopBarScreen(
                                        route = "sign_out",
                                        title = "Cerrar Sesión",
                                    ),
                                )
                                TopNavigationBar(navController, screens)
                            }
                        },
                        bottomBar = {
                            if(blackList.contains(currentRoute) == false) {
                                val screens: List<BottomBarScreen> = listOf(
                                    BottomBarScreen(
                                        route = "home",
                                        title = "Mi Rutina",
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_checklist)
                                    ),
                                    BottomBarScreen(
                                        route = "exercises",
                                        title = "Ejercicios",
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_squarelist)
                                    ),
                                    BottomBarScreen(
                                        route = "share",
                                        title = "Compartir",
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_share)
                                    ),
                                )
                                BottomNavigationBar(navController = navController, screens)
                            }
                        },
                        content = {
                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = {
                                        showDialog = false
                                    },
                                    title = {
                                        Text(text = "Título del cuadro de diálogo")
                                    },
                                    text = {
                                        val imageUrl = "https://pokefanaticos.com/pokedex/assets/images/pokemon_imagenes/25.png"
                                        val uri = Uri.parse(imageUrl)
                                        val painter = rememberImagePainter(
                                            data = uri.scheme + "://" + uri.host + uri.path + (if (uri.query != null) uri.query else ""),
                                            builder = {
                                                // You can apply transformations here if needed
                                                transformations(CircleCropTransformation())
                                            }
                                        )
                                        Column(){
                                            Text("Este es un cuadro de diálogo de alerta en Compose. Puedes personalizar su contenido aquí.")
                                            Row(){
                                                Image(
                                                    painter = painter,
                                                    contentDescription = null, // Set a proper content description if required
                                                    modifier = Modifier.size(40.dp, 40.dp)
                                                )
                                                Image(
                                                    painter = painter,
                                                    contentDescription = null, // Set a proper content description if required
                                                    modifier = Modifier.size(40.dp, 40.dp)
                                                )
                                            }
                                        }
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                // Lógica para manejar el botón de confirmación
                                                showDialog = false
                                            }
                                        ) {
                                            Text("Aceptar")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                // Lógica para manejar el botón de descartar
                                                showDialog = false
                                            }
                                        ) {
                                            Text("Cancelar")
                                        }
                                    }
                                )
                            }

                            NavHost(navController, startDestination = "login") {
                                composable(route = "splash") {
                                    SplashScreen {
                                        navController.navigate("login")
                                    }
                                }
                                composable(route = "home") {
                                    Log.d("HOME", "home screen")
                                    HomeScreen(navController, homeScrennViewModel)
                                }
                                composable(route = "exercises") {
                                    Log.d("EXERCISES", "exercises")
                                    ExercisesScreen(navController, ExercisesScreenModel)
                                }
                                composable(route = "pokemon") {
                                    Log.d("POKEMON", "pokemons screen")
                                    PokemonScreen(navController)
                                }
                                composable(route = "reset_password") {
                                    Log.d("ROUTER", "reset password")
                                    ResetPasswordScreen(navController)
                                }
                                composable(route = "profile") {
                                    Log.d("ROUTER", "profile")
                                    ProfileScreen(navController, profileScrennViewModel)
                                }
                                composable(route = "profile2") {
                                    Log.d("ROUTER2", "profile2")
                                    ProfileScreen2(navController)
                                }
                                composable(route = "create") {
                                    Log.d("ROUTER2", "profile2")
                                    CreateAcountScreen( CreateAcountModel, navController)
                                }
                                composable(route = "routine?user_id={user_id}&member_id={member_id}", arguments = listOf(
                                    navArgument("user_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    },
                                    navArgument("member_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                ), content = { entry ->
                                    val memberId = entry.arguments?.getInt("member_id")!!
                                    val userId = entry.arguments?.getInt("user_id")!!
                                    routineScreenViewModel.memberId = memberId
                                    routineScreenViewModel.userId = userId
                                    routineScreenViewModel.fetchBodyPartsExercises()
                                    routineScreenViewModel.fetchBodyParts()
                                    routineScreenViewModel.fetchExercieses()
                                    RoutineScreen(routineScreenViewModel, navController)
                                })
                                composable(route = "pokemon/edit?pokemon_id={pokemon_id}", arguments = listOf(
                                    navArgument("pokemon_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                ), content = { entry ->
                                    val pokemonId = entry.arguments?.getInt("pokemon_id")!!
                                    pokemonDetailScrennViewModel.pokemonId = pokemonId
                                    PokemonDetailScreen(navController, pokemonDetailScrennViewModel)
                                })
                                composable(route = "login") {
                                    Log.d("ROUTER", "login")
                                    val dataStore = UserStorage(applicationContext)
                                    if(dataStore.getUserId.collectAsState(initial = 9999).value != 0){
                                        val userId = dataStore.getUserId.collectAsState(initial = 0).value
                                        val memberId = userId
                                        Log.d("ROUTER", dataStore.getUserId.collectAsState(initial = 0).value.toString())
                                        if (userId != null && memberId != null){
                                            routineScreenViewModel.memberId = userId
                                            routineScreenViewModel.userId = userId
                                            routineScreenViewModel.fetchBodyPartsExercises()
                                            routineScreenViewModel.fetchBodyParts()
                                            routineScreenViewModel.fetchExercieses()
                                            RoutineScreen(routineScreenViewModel, navController)
                                        }
                                    }else{
                                        LoginScreen(loginScrennViewModel, navController)
                                    }
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}
