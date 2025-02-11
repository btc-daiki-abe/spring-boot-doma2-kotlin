package com.bigtreetc.sample.common

import java.util.concurrent.TimeUnit
import kotlin.math.max

/** スロットリングする */
class Throttler(maxRequestsPerPeriod: Int) {
  private var maxRequestsPerPeriod: Long = 0

  private val timePeriodMillis: Long = 1000

  private var timeSlot: TimeSlot? = null

  /**
   * コンストラクタ
   *
   * @param maxRequestsPerPeriod
   */
  init {
    this.maxRequestsPerPeriod = maxRequestsPerPeriod.toLong()
  }

  /**
   * 処理を実行します。
   *
   * @param callback
   */
  fun <T> process(callback: Callback<T>) {
    // 秒間アクセス数を超えている場合の遅延させるべきミリ秒数
    val delay = this.calculateDelay()

    if (0 < delay) {
      this.delay(delay)
    }

    callback.execute()
  }

  /**
   * 指定されたミリ秒スリープさせる。
   *
   * @param delay
   * @throws InterruptedException
   */
  protected fun delay(delay: Long) {
    if (0 < delay) {
      try {
        TimeUnit.MICROSECONDS.sleep(delay)
      } catch (e: InterruptedException) {}
    }
  }

  @Synchronized
  protected fun calculateDelay(): Long {
    val slot = nextSlot()

    if (!slot!!.isActive) {
      return slot.startTime - System.currentTimeMillis()
    }

    return 0
  }

  @Synchronized
  protected fun nextSlot(): TimeSlot? {
    if (timeSlot == null) {
      timeSlot = TimeSlot()
    }

    if (timeSlot!!.isFull) {
      timeSlot = timeSlot!!.next()
    }

    timeSlot!!.assign()

    return timeSlot
  }

  protected inner class TimeSlot protected constructor(val startTime: Long) {
    private var capacity = this@Throttler.maxRequestsPerPeriod

    private val duration = this@Throttler.timePeriodMillis

    constructor() : this(System.currentTimeMillis())

    @Synchronized
    fun assign() {
      capacity--
    }

    @Synchronized
    fun next(): TimeSlot {
      val startTime = max(System.currentTimeMillis(), (this.startTime + this.duration))
      val slot = TimeSlot(startTime)
      return slot
    }

    @get:Synchronized
    val isActive: Boolean
      get() = startTime <= System.currentTimeMillis()

    @get:Synchronized
    val isFull: Boolean
      get() = capacity <= 0
  }

  interface Callback<T> {
    fun execute(): T
  }
}
