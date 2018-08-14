import com.troystopera.hashi.gen.Random
import com.troystopera.hashi.gen.Seed
import com.troystopera.hashi.io.ByteArrayReader
import com.troystopera.hashi.io.ByteArrayWriter
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteWrapperTests {

    @Test
    fun testWrapperByte() {
        val inByte = Random(Seed.randomSeed()).nextInt(256).toByte()
        val bytes = ByteArray(1)
        ByteArrayWriter(bytes).writeByte(inByte)
        assertEquals(inByte, ByteArrayReader(bytes).readByte())
    }

    @Test
    fun testWrapperInt() {
        val inInt = Random(Seed.randomSeed()).nextInt(Int.MAX_VALUE)
        val bytes = ByteArray(4)
        ByteArrayWriter(bytes).writeInt(inInt)
        assertEquals(inInt, ByteArrayReader(bytes).readInt())
    }

    @Test
    fun testWrapperLong() {
        val inLong = Seed.randomSeed()
        val bytes = ByteArray(8)
        ByteArrayWriter(bytes).writeLong(inLong)
        assertEquals(inLong, ByteArrayReader(bytes).readLong())
    }

    @Test
    fun testWrapperMulti() {
        val random = Random(Seed.randomSeed())

        val byte = random.nextInt(256).toByte()
        val int = random.nextInt(Int.MAX_VALUE)
        val long = random.seed

        val bytes = ByteArray(13)
        val writer = ByteArrayWriter(bytes)
        val reader = ByteArrayReader(bytes)

        writer.writeByte(byte)
        writer.writeInt(int)
        writer.writeLong(long)

        assertEquals(byte, reader.readByte())
        assertEquals(int, reader.readInt())
        assertEquals(long, reader.readLong())
    }

}