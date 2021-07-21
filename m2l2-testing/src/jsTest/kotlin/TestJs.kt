import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestJs : StringSpec({
    "test js" {
        KotestClass().testFun() shouldBe "JS"
    }
    "test string js" {
        1.toString() shouldBe "1"
    }
})