package com.chrynan.launcher.util

import io.reactivex.Observable
import io.reactivex.Single

fun <T, R> Observable<List<T>>.mapEachFromEmittedList(map: (T) -> R): Observable<List<R>> =
        flatMapSingle {
            Observable.fromIterable(it)
                    .map { map(it) }
                    .toList()
        }

fun <T, R> Single<List<T>>.mapEachFromEmittedList(map: (T) -> R): Single<List<R>> =
        flatMap {
            Observable.fromIterable(it)
                    .map { map(it) }
                    .toList()
        }

fun <T> Observable<List<T>>.sortEachAlphabeticallyBy(selector: (T) -> String): Observable<List<T>> =
        map { it.sortAlphabeticallyBy(selector) }

fun <T> Single<List<T>>.sortEachAlphabeticallyBy(selector: (T) -> String): Single<List<T>> =
        map { it.sortAlphabeticallyBy(selector) }