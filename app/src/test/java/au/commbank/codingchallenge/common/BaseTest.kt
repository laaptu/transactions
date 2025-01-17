package au.commbank.codingchallenge.common

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.MockitoAnnotations

abstract class BaseTest {
    @RegisterExtension
    @JvmField
    val coroutinesTestRule = CoroutinesTestRule()

    @RegisterExtension
    @JvmField
    val instantTaskExecutorRule = TaskExecutorRule()

    @OptIn()
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        coroutinesTestRule.testDispatcher.runBlockingTest(block)
    }

    open fun init() {
        MockitoAnnotations.initMocks(this)
    }
}