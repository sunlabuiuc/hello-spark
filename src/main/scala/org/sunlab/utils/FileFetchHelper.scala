package org.sunlab.utils

import java.io.File
import java.net.URL

import scala.sys.process._

object FileFetchHelper extends Awakable {

  def checkAndFetch(path: String, url: String): Unit = {
    val target = new File(path)
    if (target.isFile) {
      logger.info(s"File: $path is exists..")
    } else {
      if (target.exists()) {
        logger.warn(s"Path: $path is not a file, erase it and download again")
        target.delete()
      } else {
        // create parents folder
        target.mkdirs()
        // erase the exactly
        target.delete()
      }

      val source = new URL(url)
      val conn = source.openConnection
      conn.setRequestProperty("Accept", "text/json")
      conn.setIfModifiedSince(new java.util.Date().getTime - 1000 * 60 * 30)

      source #> target !!

      logger.info(s"File: $path is fetched from $source")
    }
  }
}
