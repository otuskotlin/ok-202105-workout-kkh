import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class DddTest : BehaviorSpec({
    given("a broomstick") {
        `when`("I sit on it") {
            then("I should be able to fly") {
                Broomstick().flyable() shouldBe true
            }
        }
        `when`("I throw it away") {
            then("it should come back") {
                Broomstick().comback() shouldBe true
            }
        }
    }
})