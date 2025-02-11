package com.bigtreetc.sample.common.util

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors
import lombok.SneakyThrows
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource

object FileUtils {

  /**
   * 一時ファイルを作成します。
   *
   * @param suffix
   * @return
   */
  fun createTempFile(suffix: String): File {
    val tmpDir = System.getProperty("java.io.tmpdir")
    try {
      return File.createTempFile(tmpDir, suffix)
    } catch (e: IOException) {
      throw IllegalArgumentException("could not create temp file. $suffix", e)
    }
  }

  /**
   * ディレクトリがない場合は作成します。
   *
   * @param location
   */
  fun createDirectory(location: Path) {
    try {
      Files.createDirectory(location)
    } catch (ignore: FileAlreadyExistsException) {
      // ignore
    } catch (e: IOException) {
      throw IllegalArgumentException("could not create directory. $location", e)
    }
  }

  /**
   * 親ディレクトリを含めてディレクトリがない場合は作成します。
   *
   * @param location
   */
  fun createDirectories(location: Path) {
    try {
      Files.createDirectories(location)
    } catch (ignore: FileAlreadyExistsException) {
      // ignore
    } catch (e: IOException) {
      throw IllegalArgumentException("could not create directory. $location", e)
    }
  }

  /**
   * ファイルの一覧を取得します。
   *
   * @param location
   * @return
   */
  fun listAllFiles(location: Path): List<Path> {
    try {
      Files.walk(location, 1).use { walk ->
        return walk
          .filter { path: Path -> path != location }
          .map { other: Path? -> location.relativize(other) }
          .collect(Collectors.toList())
      }
    } catch (e: IOException) {
      throw IllegalArgumentException("failed to list uploaded files. ", e)
    }
  }

  /**
   * ファイルを読み込みます。
   *
   * @param location
   * @param filename
   * @return
   */
  @SneakyThrows
  fun loadFile(location: Path, filename: String): Resource {
    try {
      val file = location.resolve(filename)
      val resource = UrlResource(file.toUri())

      if (resource.exists() || resource.isReadable) {
        return resource
      }

      throw FileNotFoundException("could not read file. $filename")
    } catch (e: MalformedURLException) {
      throw IllegalArgumentException(
        "malformed Url resource. [location=$location, filename=$filename]",
        e,
      )
    }
  }

  /**
   * ファイルを削除します。
   *
   * @param location
   * @param filename
   */
  fun deleteFile(location: Path, filename: String) {
    Objects.requireNonNull(filename, "filename can't be null")
    try {
      Files.deleteIfExists(location.resolve(filename))
    } catch (e: IOException) {
      throw IllegalStateException("failed to delete file. $filename", e)
    }
  }
}
