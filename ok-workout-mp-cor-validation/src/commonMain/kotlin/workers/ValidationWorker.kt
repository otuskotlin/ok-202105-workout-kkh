package workers

import ICorChainDsl
import ValidationBuilder
import handlers.worker

fun <C> ICorChainDsl<C>.validation(block: ValidationBuilder<C>.() -> Unit) {
    worker {
        title = "Validation"
        description = "Validation of logic"

        handle {
            ValidationBuilder<C>().apply(block).build().exec(this)
        }

    }
}