package com.ctech.eaty.base.redux

import io.reactivex.functions.BiFunction


interface Reducer<State> : BiFunction<State, Result, State>