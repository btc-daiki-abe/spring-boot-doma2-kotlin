package com.bigtreetc.sample.domain.util

import com.fasterxml.jackson.dataformat.csv.CsvGenerator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.util.function.Function
import java.util.stream.Stream
import lombok.extern.slf4j.Slf4j

/** TSVファイル出力ユーティリティ */
@Slf4j
object CsvUtils {
  private val csvMapper = createCsvMapper()

  /**
   * CSVマッパーを生成する。
   *
   * @return
   */
  private fun createCsvMapper(): CsvMapper {
    val mapper = CsvMapper()
    mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true)
    mapper.findAndRegisterModules()
    return mapper
  }

  /**
   * CSVファイルを出力します。
   *
   * @param clazz
   * @param data
   */
  @Throws(IOException::class)
  fun writeCsv(
    outputStream: OutputStream?,
    clazz: Class<*>?,
    data: Stream<*>,
    mapper: Function<Any?, *>?,
  ) {
    write(outputStream, clazz, data, mapper, StandardCharsets.UTF_8.name(), ',')
  }

  /**
   * CSVファイルを出力します。
   *
   * @param clazz
   * @param data
   * @param charsetName
   */
  @Throws(IOException::class)
  fun writeCsv(
    outputStream: OutputStream?,
    clazz: Class<*>?,
    data: Stream<*>,
    mapper: Function<Any?, *>?,
    charsetName: String,
  ) {
    write(outputStream, clazz, data, mapper, charsetName, ',')
  }

  /**
   * TSVファイルを出力します。
   *
   * @param clazz
   * @param data
   */
  @Throws(IOException::class)
  fun writeTsv(
    outputStream: OutputStream?,
    clazz: Class<*>?,
    data: Stream<*>,
    mapper: Function<Any?, *>?,
  ) {
    write(outputStream, clazz, data, mapper, StandardCharsets.UTF_8.name(), '\t')
  }

  /**
   * TSVファイルを出力します。
   *
   * @param clazz
   * @param data
   * @param charsetName
   */
  @Throws(IOException::class)
  fun writeTsv(
    outputStream: OutputStream?,
    clazz: Class<*>?,
    data: Stream<*>,
    mapper: Function<Any?, *>?,
    charsetName: String,
  ) {
    write(outputStream, clazz, data, mapper, charsetName, '\t')
  }

  /**
   * CSVファイルを出力します。
   *
   * @param outputStream
   * @param clazz
   * @param data
   * @param charsetName
   * @param delimiter
   * @return
   */
  @Throws(IOException::class)
  private fun write(
    outputStream: OutputStream?,
    clazz: Class<*>?,
    data: Stream<*>,
    mapper: Function<Any?, *>?,
    charsetName: String,
    delimiter: Char,
  ) {
    // CSVスキーマ、デリミタをセットする
    val schema = csvMapper.schemaFor(clazz).withColumnSeparator(delimiter)

    BufferedWriter(OutputStreamWriter(outputStream, charsetName)).use { writer ->
      var header = true
      val iterator = data.iterator()
      while (iterator.hasNext()) {
        val obj = iterator.next()!!
        val mapped = if ((mapper != null)) mapper.apply(obj) else obj
        val csvWriter =
          if ((header)) csvMapper.writer(schema.withHeader())
          else csvMapper.writer(schema.withoutHeader())
        val csvLine = csvWriter.writeValueAsString(mapped)
        writer.write(csvLine)
        header = false
      }
    }
  }
}
