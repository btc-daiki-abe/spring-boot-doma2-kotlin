package com.bigtreetc.sample.common.util

import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.function.BiFunction
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import lombok.SneakyThrows
import org.slf4j.LoggerFactory

object ZipFileUtils {
  private val log = LoggerFactory.getLogger(CompressUtils::class.java)
  private val CHARSET_MS932: Charset = Charset.forName("MS932")

  /**
   * Zipファイルを展開しながら1ファイルずつ変換します。
   *
   * @param inputStream
   * @param consumer
   * @return
   */
  fun <T> transform(
    inputStream: InputStream,
    consumer: BiFunction<ZipInputStream, ZipEntry, T>,
  ): List<T>? {
    var list: List<T>? = null
    var tempFile: File? = null

    try {
      tempFile = toTempFile(inputStream)
      list = transform(toFileInputStream(tempFile), StandardCharsets.UTF_8, consumer)
    } catch (e: IllegalArgumentException) {
      if (tempFile != null) {
        list = transform(toFileInputStream(tempFile), CHARSET_MS932, consumer)
      }
    } finally {
      try {
        tempFile?.deleteOnExit()
        inputStream.close()
      } catch (e: IOException) {
        // ignore
      }
    }

    return list
  }

  private fun <T> transform(
    inputStream: InputStream,
    charset: Charset,
    consumer: BiFunction<ZipInputStream, ZipEntry, T>,
  ): List<T> {
    val list = ArrayList<T>()
    try {
      ZipInputStream(inputStream, charset).use { zis ->
        var zipEntry: ZipEntry
        while ((zis.nextEntry.also { zipEntry = it }) != null) {
          if (log.isDebugEnabled()) {
            log.debug("read file. [filename={}]", zipEntry.name)
          }
          list.add(consumer.apply(zis, zipEntry))
        }
      }
    } catch (e: IOException) {
      throw IllegalStateException(e)
    }
    return list
  }

  @SneakyThrows
  private fun toFileInputStream(file: File?): FileInputStream {
    return FileInputStream(file)
  }

  @SneakyThrows
  private fun toTempFile(inputStream: InputStream): File {
    val tempFile: File = FileUtils.createTempFile(ZipFileUtils::class.java.simpleName)
    FileOutputStream(tempFile).use { fileOutputStream ->
      inputStream.transferTo(fileOutputStream)
      return tempFile
    }
  }
}
