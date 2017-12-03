package com.ctech.eaty.entity;

enum class DataSize constructor(private val size: Long, private val desc: String) {
    ONEGB(1073741824L, "GB"),
    FIVEHUNDREDMB(500000000L, "MB"),
    ONEMB(1048576L, "MB"),
    FIVEHUNDREDKB(500000L, "KB"),
    ONEKB(1024L, "KB");


    companion object {
        fun getDataSizeString(fileSize: Double): String {
            return when {
                fileSize > FIVEHUNDREDMB.size -> // if greater then 500mb
                    String.format("%1$.2f GB", fileSize / ONEGB.size)
                fileSize > FIVEHUNDREDKB.size -> String.format("%1$.2f MB", fileSize / ONEMB.size)
                fileSize > ONEKB.size -> String.format("%1$.2f KB", fileSize / ONEKB.size)
                else -> fileSize.toString() + " Bytes"
            }
        }
    }

}