import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestJvm : StringSpec({
    "test_jvm" {
        KotestClass().testFun() shouldBe "JVM"
    }
    "test j" {
        KotestClass().testFun() shouldBe "JVM"
    }
})
