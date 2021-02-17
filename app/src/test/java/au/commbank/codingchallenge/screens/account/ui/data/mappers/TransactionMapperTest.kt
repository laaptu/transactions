package au.commbank.codingchallenge.screens.account.ui.data.mappers

import au.commbank.codingchallenge.common.BaseTest
import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.screens.account.data.Transaction
import au.commbank.codingchallenge.screens.account.ui.data.TransactionItem
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import java.util.*
import java.util.stream.Stream

//seems like this test is redundant as we are basically testing TreeMap of TransactionMapper
class TransactionMapperTest : BaseTest() {

    @Mock
    lateinit var logger: Logger
    lateinit var transactionMapper: TransactionMapper

    @BeforeEach
    override fun init() {
        super.init()
        transactionMapper = TransactionMapper(logger)
    }


    @DisplayName("Validate Merging of Transactions")
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @MethodSource("getArguments")
    fun validateMergeTransaction(
        transaction: List<Transaction>, pendingTransaction: List<Transaction>,
        mergeOrder: LinkedHashMap<String, List<Transaction>>
    ) {
        val merge: TreeMap<String, MutableList<TransactionItem>> =
            transactionMapper.merge(transaction, pendingTransaction)

        assertEquals(mergeOrder.size, merge.size)
        val resultIterator = merge.iterator()
        val expectedIterator = merge.iterator()

        while (expectedIterator.hasNext()) {
            val resEntry = resultIterator.next()
            val expEntry = expectedIterator.next()
            assertEquals(expEntry, resEntry)
            assertEquals(expEntry.value.size, resEntry.value.size)

            val resListIterator = resEntry.value.iterator()
            val expListIterator = expEntry.value.iterator()

            while (expListIterator.hasNext()) {
                val expItem = expListIterator.next()
                val resItem = resListIterator.next()
                assertEquals(expItem.id, resItem.id)
            }
        }
    }

    companion object {
        private val transaction1 = Transaction(
            id = "87f6f9d078c3bc5db5578f3b4add9470",
            description = "Credit Interest",
            effectiveDate = "3/06/2021",
            amount = 0.02f,
            atmId = null
        )
        private val transaction2 = Transaction(
            id = "d4fae4b45e689707e7dea506afc8c0e7",
            description = "TELSTRA CORP LTD BPAY 23796 1000006591234",
            effectiveDate = "4/06/2020",
            amount = -89.00f,
            atmId = null
        )

        private val transaction3 = Transaction(
            id = "821ae63dbe0c573eff8b69d451fb21bc",
            effectiveDate = "21/06/2019",
            description = "Wdl ATM CBA ATM CIRCULAR QUAY STATION NSW 221092 AUS",
            amount = -200.00f,
            atmId = "129382"
        )

        private val pendingTransaction1 = Transaction(
            id = "e2eff6c2dafd909df8508f891b385d88",
            description = "WILSON PARKING SYDOBS SYDNEY NS AUS<br/>LAST 4 CARD DIGITS: 6901",
            effectiveDate = "21/06/2019",
            amount = -12.00f,
            atmId = null
        )

        @JvmStatic
        private fun getArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    listOf(transaction1, transaction2, transaction3),
                    listOf(pendingTransaction1),
                    linkedMapOf(
                        transaction1.effectiveDate to listOf(transaction1),
                        transaction2.effectiveDate to listOf(transaction2),
                        transaction3.effectiveDate to listOf(transaction3, pendingTransaction1)
                    )
                )
            )
    }


}