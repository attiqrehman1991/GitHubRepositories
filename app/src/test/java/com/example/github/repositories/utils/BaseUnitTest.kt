package com.example.github.repositories.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {

    val exception = RuntimeException("Network Issue")

    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTastExecutorRule = InstantTaskExecutorRule()
}