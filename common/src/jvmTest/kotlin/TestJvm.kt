import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestJvm : StringSpec({
    "test jvm" {
        KotestClasses().testFun() shouldBe "JVM"
    }
})