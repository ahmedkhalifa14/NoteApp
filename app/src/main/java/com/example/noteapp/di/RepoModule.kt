package com.example.noteapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.noteapp.MyApplication
import com.example.noteapp.db.NoteDao
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.repo.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideLocalRepo(noteDatabase: NoteDatabase): LocalRepo {
        return LocalRepoImp(noteDatabase)
    }

    @Provides
    @Singleton
    fun provideRemoteRepo(database: FirebaseFirestore): RemoteRepo {
        return RemoteRepoImp(database)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(auth, database,appPreferences,gson)
    }

    @Singleton
    @Provides
    fun provideNoteDb( @ApplicationContext context: Context)=
         Room.databaseBuilder(context, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

//    @Singleton
//    @Provides
//    fun provideNoteDao(noteDatabase: NoteDatabase)=
//        noteDatabase.noteDao()



}