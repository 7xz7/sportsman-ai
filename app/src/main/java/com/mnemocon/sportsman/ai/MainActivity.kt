package com.mnemocon.sportsman.ai
// Импорт необходимых библиотек и классов
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mnemocon.sportsman.ai.databinding.ActivityMainBinding
import kotlin.Result.Companion.success

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient
    // Объявление переменных для связки, аутентификации и авторизации через Google
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Запрос разрешений на использование камеры
        if (allPermissionsGranted()) {
            startLogic()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // [START config_signin]
        // Настройка входа в систему Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("651908455888-j7made5nss0ak0b9vshr36fe2nn8n815.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]

        // [START initialize_auth]
        // Инициализация Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

    }
    // Проверка на наличие необходимых разрешений
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    // Обработка результатов запроса разрешений
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startLogic()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    // Запуск основной логики приложения
    private fun startLogic() {
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            // Name, email address, and profile photo Url
            val name = currentUser.displayName
            val email = currentUser.email
            val photoUrl = currentUser.photoUrl

            // Check if user's email is verified
            val emailVerified = currentUser.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken () instead.
            val uid = currentUser.uid
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =  supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController

        //val navController = findNavController(R.id.nav_host_fragment_activity_main)
        //Передаем идентификатор каждого меню в виде набора идентификаторов, поскольку каждое
        // меню должно рассматриваться как пункты назначения верхнего уровня.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.startCounting, R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    // Показать нижнее меню навигации
    fun showBottomNavigation()
    {
        binding.navView.visibility = View.VISIBLE
        val actionBar: ActionBar? = supportActionBar //Get action bar reference
        actionBar?.show() //Hide the action bar
    }
    // Скрыть нижнее меню навигации
    fun hideBottomNavigation()
    {
        binding.navView.visibility = View.GONE
        val actionBar: ActionBar? = supportActionBar //Get action bar reference
        actionBar?.hide() //Hide the action bar
    }
    // Проверка авторизованного пользователя при старте активности
    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
            signIn{}
        // Проверяем, вошел ли пользователь в систему (non-null), и соответствующим образом обновляем пользовательский интерфейс.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]
    // Обработка результата авторизации через Google
    // [START onactivityresult]
    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Обрабатываем результат здесь
                val data = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In был успешным, аутентификация с помощью Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                    // Установить параметр success в значение true
                    success(true)
                } catch (e: ApiException) {
                    // Google Sign In failed, обновите пользовательский интерфейс соответствующим образом
                    Log.w(TAG, "Google sign in failed", e)
                    // Установить параметр success в значение false
                    success(false)
                }
            } else {
                // Код результата не был OK, установите параметр success в false
                success(false)
            }
        }
    // [END onactivityresult]
// Запуск процесса авторизации через Google
    // [START signin]
   fun signIn(success: (Boolean) -> Unit) {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }
    // [END signin]
// Авторизация через Firebase с использованием данных от Google
    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Успех входа в систему, обновление пользовательского интерфейса с информацией о вошедшем пользователе
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Если вход в систему не удался, выведите пользователю сообщение.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    // [END auth_with_google]
// Обновление пользовательского интерфейса на основе данных пользователя
    private fun updateUI(user: FirebaseUser?) {
    }

    // Объявление статических констант
    companion object {
        private const val TAG = "GoogleActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}