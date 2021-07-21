import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestJs : StringSpec({
    "test js" {
        KotestClasses().testFun() shouldBe "JS"
    }
})