package com.kn.roomtask

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutorRoom private constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {
    fun diskIO(): Executor {
        return diskIO
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private val LOCK = Any()
        private var sInstance: AppExecutorRoom? = null

        @JvmStatic
        val instance: AppExecutorRoom?
            get() {
                if (sInstance == null) {
                    synchronized(LOCK) {
                        sInstance = AppExecutorRoom(
                            Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            MainThreadExecutor()
                        )
                    }
                }
                return sInstance
            }
    }

}