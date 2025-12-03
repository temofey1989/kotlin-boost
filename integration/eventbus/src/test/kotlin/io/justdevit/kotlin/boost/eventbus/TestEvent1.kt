package io.justdevit.kotlin.boost.eventbus

sealed class TestEvent : Event()

data class TestEvent1(val data: String) : TestEvent()

data class TestEvent2(val data: String) : TestEvent()
