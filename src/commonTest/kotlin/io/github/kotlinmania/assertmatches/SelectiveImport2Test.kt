// port-lint: source tests/selective_import2.rs
package io.github.kotlinmania.assertmatches

import kotlin.test.Test

class SelectiveImport2Test {
    @Test
    fun testAssertSucceed() {
        val a: UInt = 42u
        debugAssertMatches(a, "42u") { it == 42u }
    }
}
