// port-lint: source tests/selective_import1.rs
package io.github.kotlinmania.assertmatches

import kotlin.test.Test

class SelectiveImport1Test {
    @Test
    fun testAssertSucceed() {
        val a: UInt = 42u
        assertMatches(a, "42u") { it == 42u }
    }
}
