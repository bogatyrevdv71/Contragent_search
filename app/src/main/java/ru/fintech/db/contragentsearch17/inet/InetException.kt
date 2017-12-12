package ru.fintech.db.contragentsearch17.inet

/**
 * Created by DB on 12.12.2017.
 *
 */
class InetException (val code: Int, e: Throwable) : Exception("Internet error", e)