package com.shopping.project.data.local

import androidx.room.RoomDatabase

abstract class DataBase :RoomDatabase() {

//    companion object{
//        private var objectInstance:DataBase? = null
//
//        private val lock = Any()
//
//        fun getInstance(context:Context):DataBase{
//            synchronized(lock){
//                if(objectInstance==null){
//                    objectInstance = Room.databaseBuilder(context.applicationContext,DataBase::class.java,"myDatabase.db").build()
//
//                }
//
//                return objectInstance!!
//            }
//        }
//    }

}