package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.SendMailQueue
import com.bigtreetc.sample.domain.entity.SendMailQueueCriteria
import java.util.stream.Collector
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions

@ConfigAutowireable
@Dao
interface SendMailQueueDao {
  /**
   * メール送信キューを取得します。
   *
   * @param criteria
   * @param options
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: SendMailQueueCriteria,
    options: SelectOptions,
    collector: Collector<SendMailQueue, *, R>,
  ): R

  /**
   * メール送信キューを一括登録します。
   *
   * @param sendMailQueues
   * @return
   */
  @BatchInsert fun insert(sendMailQueues: List<SendMailQueue>): IntArray

  /**
   * メール送信キューを一括更新します。
   *
   * @param sendMailQueues
   * @return
   */
  @BatchUpdate fun update(sendMailQueues: List<SendMailQueue>): IntArray
}
