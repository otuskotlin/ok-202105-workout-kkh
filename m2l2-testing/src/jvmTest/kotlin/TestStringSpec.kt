import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestStringSpec : StringSpec({
    "test_jvm" {
        KotestClass().testFun() shouldBe "JVM"
    }
})

class TestShouldSpec : ShouldSpec({
    context("String.length") {
        "kotlin".length shouldBe 6
        "".length shouldBe 0
    }
})

class TestShouldSpec2 : ShouldSpec({
    context("String.length") {
        should("return the length of the string") {
            "kotlin".length shouldBe 6
            "".length shouldBe 0
        }
    }
})

class TestAnnotationSpec : AnnotationSpec() {

    @Test
    fun test1() {
        "1" shouldBe 1.toString()
    }
}
