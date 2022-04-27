package com.example.lyfstile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import java.io.File


class MainActivity : AppCompatActivity() {
    private var lyfViewModel: LyfViewModel? = null

    interface Backup {
        fun bind(context: Context)
        fun downloadDB()
        fun uploadFile()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        try {
            Log.i("MyAmplifyApp", "Initialized Amplify new launch")

            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
            Amplify.Auth.fetchAuthSession(
                { Log.i("AmplifyQuickstart", "Auth session = $it") },
                { error -> Log.e("AmplifyQuickstart", "Failed to fetch auth session", error) }
            )
            Amplify.Auth.signInWithWebUI(this,
                { Log.i("AuthQuickStart", "Signin OK = $it") },
                { Log.e("AuthQuickStart", "Signin failed", it) }
            )

        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
        downloadDB()
        lyfViewModel = ViewModelProvider(this)[LyfViewModel::class.java]
        lyfViewModel!!.bind(this)
    }

    fun downloadDB() {
        val db = File("/data/data/com.example.lyfstile/databases/user_db")
        Amplify.Storage.downloadFile("db", db,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp", "Download Failure", it) }
        )
        val dbwal = File("/data/data/com.example.lyfstile/databases/user_db-wal")
        Amplify.Storage.downloadFile("dbwal", dbwal,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp", "Download Failure", it) }
        )
        val dbshm = File("/data/data/com.example.lyfstile/databases/user_db-shm")
        Amplify.Storage.downloadFile("dbshm", dbshm,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp", "Download Failure", it) }
        )

        val dbw = File("/data/data/com.example.lyfstile/databases/weather_db")
        Amplify.Storage.downloadFile("dbw", dbw,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp",  "Download Failure", it) }
        )
        val dbwwal = File("/data/data/com.example.lyfstile/databases/weather_db-wal")
        Amplify.Storage.downloadFile("dbwwal", dbwwal,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp",  "Download Failure", it) }
        )
        val dbwshm = File("/data/data/com.example.lyfstile/databases/weather_db-shm")
        Amplify.Storage.downloadFile("dbwshm", dbwshm,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp",  "Download Failure", it) }
        )


    }

    fun uploadFile() {
        var pathdb = getDatabasePath("user_db").absolutePath
        var pathdbshm = getDatabasePath("user_db-shm").absolutePath
        var pathdbwal = getDatabasePath("user_db-wal").absolutePath

        var db = File(pathdb)
        var dbshm = File(pathdbshm)
        var dbwal = File(pathdbwal)

        Log.i("MyAmplifyApp", db.readText())
        Amplify.Storage.uploadFile("db", db,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
        Log.i("MyAmplifyApp", dbshm.readText())
        Amplify.Storage.uploadFile("dbshm", dbshm,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
        Log.i("MyAmplifyApp", dbwal.readText())
        Amplify.Storage.uploadFile("dbwal", dbwal,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
        uploadWeather()
    }

    private fun uploadWeather()
    {
        var pathdb = getDatabasePath("weather_db").absolutePath
        var pathdbshm = getDatabasePath("weather_db-shm").absolutePath
        var pathdbwal = getDatabasePath("weather_db-wal").absolutePath



        var db = File(pathdb)
        var dbshm = File(pathdbshm)
        var dbwal = File(pathdbwal)

        Log.i("MyAmplifyApp", db.readText())
        Amplify.Storage.uploadFile("dbw", db,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
        Log.i("MyAmplifyApp", dbshm.readText())
        Amplify.Storage.uploadFile("dbwshm", dbshm,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
        Log.i("MyAmplifyApp", dbwal.readText())
        Amplify.Storage.uploadFile("dbwwal", dbwal,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
    }


}