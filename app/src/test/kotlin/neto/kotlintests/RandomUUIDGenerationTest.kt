package neto.kotlintests

import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
class RandomUUIDGenerationTest {

    @Test
    fun sortedSetTest() {
        assertEquals(sortedSetOf(3, 2, 1).toString(), "[1, 2, 3]")
    }

    @Test
    fun generateTest() {

//        arrayOf(100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000).forEach { extracted(it) }
//        repeatToGetDurations(1_000)

        println("UUID 1")
        val uuids1 = measureTimedValue { generate(1_000_000) }.also { println("1 millions UUID generated in ${it.duration}") }.value

        println("UUID 2")
        val uuids2 = measureTimedValue{ generate(1_000_000) + uuids1.take(100_000) }.also { println("1 millions UUID + 100000 from set above generated in ${it.duration}") }.value

        val uuids1Minus2 = uuids1 - uuids2
        println("uuids1Minus2.size = ${uuids1Minus2.size}")

        val uuids2Minus1 = uuids2 - uuids1
        println("uuids2Minus1.size = ${uuids2Minus1.size}")

    }

    @Test
    fun generateFileTest() {
        val uuids: TreeSet<UUID> = generate(10)
        Files.write(Paths.get("uuid1.txt"), uuids.map(UUID::toString))
    }

    fun printUUIDs(uuids: Set<UUID>) {
        uuids.forEach { uuid ->
            println("uuid = $uuid, mostSignif = ${uuid.mostSignificantBits}, leastSignif = ${uuid.leastSignificantBits}")
        }
    }


    private fun generate(qtd: Int): TreeSet<UUID> {

        val uuids = sortedSetOf<UUID>()

        repeat(qtd) {
            uuids.add(UUID.randomUUID())
        }

        return uuids
    }

    private fun repeatToGetDurations(qtd: Int) {

        println("############# qtd = $qtd #############")

        repeat(10) {
            val timedValue = measureTimedValue {
                generate(qtd)
            }
            println("uuidList.size = ${timedValue.value.size}; duration = ${timedValue.duration}")
        }
    }

    @Test
    fun compareUUIDTest() {
        val uuid1 = UUID.fromString("b0bc447f-9a42-4870-abe2-d09fa583eada")
        val uuid2 = UUID.fromString("2dc0d8ce-fdba-4141-b606-acb4631b5a36")

        println(uuid1 > uuid2)
    }
}