package com.ctech.eaty.util

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


object FileUtils {

    @Throws(IOException::class)
    fun cleanDirectory(directory: File): Long {
        val files = verifiedListFiles(directory)

        var length = 0L
        var exception: IOException? = null
        for (file in files) {
            try {
                length += forceDelete(file)
            } catch (ioe: IOException) {
                exception = ioe
            }

        }

        if (null != exception) {
            throw exception
        }
        return length
    }

    @Throws(IOException::class)
    private fun verifiedListFiles(directory: File): Array<File> {
        if (!directory.exists()) {
            val message = directory.toString() + " does not exist"
            throw IllegalArgumentException(message)
        }

        if (!directory.isDirectory) {
            val message = directory.toString() + " is not a directory"
            throw IllegalArgumentException(message)
        }

        return directory.listFiles() ?: // null if security restricted
                throw IOException("Failed to list contents of " + directory)
    }

    @Throws(IOException::class)
    private fun forceDelete(file: File): Long {
        return if (file.isDirectory) {
            deleteDirectory(file)
        } else {
            val filePresent = file.exists()
            val length = file.length()
            if (!file.delete()) {
                if (!filePresent) {
                    throw FileNotFoundException("File does not exist: " + file)
                }
                val message = "Unable to delete file: " + file
                throw IOException(message)
            }
            length
        }
    }

    @Throws(IOException::class)
    fun deleteDirectory(directory: File): Long {
        if (!directory.exists()) {
            return 0L
        }

        val length = cleanDirectory(directory)

        if (!directory.delete()) {
            val message = "Unable to delete directory $directory."
            throw IOException(message)
        }
        return length
    }

}