package com.example.lyfstile

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity(){
    private var lyfViewModel: LyfViewModel? = null
    interface Backup
    {
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
    fun downloadDB()
    {
        val db = File("/data/data/com.example.lyfstile/databases/user_db")
        Amplify.Storage.downloadFile("db", db,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp",  "Download Failure", it) }
        )
        val dbwal = File("/data/data/com.example.lyfstile/databases/user_db-wal")
        Amplify.Storage.downloadFile("dbwal", dbwal,
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp",  "Download Failure", it) }
        )
        val dbshm = File("/data/data/com.example.lyfstile/databases/user_db-shm")
        Amplify.Storage.downloadFile("dbshm", dbshm,
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
    }



}