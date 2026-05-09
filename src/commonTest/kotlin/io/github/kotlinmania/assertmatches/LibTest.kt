// port-lint: source src/lib.rs
package io.github.kotlinmania.assertmatches

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LibTest {
    sealed class Foo {
        data class A(val value: Int) : Foo()
        data class B(val value: String) : Foo()
        data class C(val value: String) : Foo()
    }

    @Test
    fun testAssertSucceed() {
        val a: Foo = Foo.A(123)

        assertMatches(a, "Foo.A(_)") { it is Foo.A }
        assertMatches(a, "Foo.A(123)") { it is Foo.A && it.value == 123 }
        assertMatches(a, "Foo.A(i) if i == 123") { it is Foo.A && it.value == 123 }
        assertMatches(a, "Foo.A(42) | Foo.A(123)") {
            it is Foo.A && (it.value == 42 || it.value == 123)
        }

        val b: Foo = Foo.B("foo")

        assertMatches(b, "Foo.B(_)") { it is Foo.B }
        assertMatches(b, """Foo.B("foo")""") { it is Foo.B && it.value == "foo" }
        assertMatches(b, """Foo.B(s) if s == "foo"""") { it is Foo.B && it.value == "foo" }
        assertMatchesYield(b, "Foo.B(s)", { it is Foo.B }) { matched ->
            assertEquals("foo", (matched as Foo.B).value)
        }
        assertMatchesYield(b, "Foo.B(s)", { it is Foo.B }) { matched ->
            assertEquals("foo", (matched as Foo.B).value)
            check(true)
        }
        assertMatchesYield(
            b,
            """Foo.B(s) if s == "foo"""",
            { it is Foo.B && it.value == "foo" },
        ) { matched ->
            assertEquals("foo", (matched as Foo.B).value)
        }
        assertMatchesYield(
            b,
            """Foo.B(s) if s == "foo"""",
            { it is Foo.B && it.value == "foo" },
        ) { matched ->
            assertEquals("foo", (matched as Foo.B).value)
            check(true)
        }

        val c: Foo = Foo.C("foo")

        assertMatches(c, "Foo.B(_) | Foo.C(_)") { it is Foo.B || it is Foo.C }
        assertMatches(c, """Foo.B("foo") | Foo.C("foo")""") {
            (it is Foo.B && it.value == "foo") || (it is Foo.C && it.value == "foo")
        }
        assertMatches(c, """Foo.B(s) | Foo.C(s) if s == "foo"""") {
            when (it) {
                is Foo.B -> it.value == "foo"
                is Foo.C -> it.value == "foo"
                else -> false
            }
        }
        assertMatchesYield(c, "Foo.B(s) | Foo.C(s)", { it is Foo.B || it is Foo.C }) { matched ->
            val s = when (matched) {
                is Foo.B -> matched.value
                is Foo.C -> matched.value
                else -> error("unreachable")
            }
            assertEquals("foo", s)
        }
        assertMatchesYield(c, "Foo.B(s) | Foo.C(s)", { it is Foo.B || it is Foo.C }) { matched ->
            val s = when (matched) {
                is Foo.B -> matched.value
                is Foo.C -> matched.value
                else -> error("unreachable")
            }
            assertEquals("foo", s)
            check(true)
        }
        assertMatchesYield(
            c,
            """Foo.B(s) | Foo.C(s) if s == "foo"""",
            {
                when (it) {
                    is Foo.B -> it.value == "foo"
                    is Foo.C -> it.value == "foo"
                    else -> false
                }
            },
        ) { matched ->
            val s = when (matched) {
                is Foo.B -> matched.value
                is Foo.C -> matched.value
                else -> error("unreachable")
            }
            assertEquals("foo", s)
        }
        assertMatchesYield(
            c,
            """Foo.B(s) | Foo.C(s) if s == "foo"""",
            {
                when (it) {
                    is Foo.B -> it.value == "foo"
                    is Foo.C -> it.value == "foo"
                    else -> false
                }
            },
        ) { matched ->
            val s = when (matched) {
                is Foo.B -> matched.value
                is Foo.C -> matched.value
                else -> error("unreachable")
            }
            assertEquals("foo", s)
            check(true)
        }
    }

    @Test
    fun testAssertPanic0() {
        val a: Foo = Foo.A(123)
        assertFailsWith<AssertionError> {
            assertMatches(a, "Foo.B(_)") { it is Foo.B }
        }
    }

    @Test
    fun testAssertPanic1() {
        val b: Foo = Foo.B("foo")
        assertFailsWith<AssertionError> {
            assertMatches(b, """Foo.B("bar")""") { it is Foo.B && it.value == "bar" }
        }
    }

    @Test
    fun testAssertPanic2() {
        val b: Foo = Foo.B("foo")
        assertFailsWith<AssertionError> {
            assertMatches(b, """Foo.B(s) if s == "bar"""") {
                it is Foo.B && it.value == "bar"
            }
        }
    }

    @Test
    fun testAssertPanic3() {
        val b: Foo = Foo.B("foo")
        assertFailsWith<AssertionError> {
            assertMatchesYield(b, "Foo.B(s)", { it is Foo.B }) { matched ->
                assertEquals("bar", (matched as Foo.B).value)
            }
        }
    }

    @Test
    fun testAssertPanic4() {
        val b: Foo = Foo.B("foo")
        assertFailsWith<AssertionError> {
            assertMatchesYield(
                b,
                """Foo.B(s) if s == "bar"""",
                { it is Foo.B && it.value == "bar" },
            ) { matched ->
                assertEquals("foo", (matched as Foo.B).value)
            }
        }
    }

    @Test
    fun testAssertPanic5() {
        val b: Foo = Foo.B("foo")
        assertFailsWith<AssertionError> {
            assertMatchesYield(
                b,
                """Foo.B(s) if s == "foo"""",
                { it is Foo.B && it.value == "foo" },
            ) { matched ->
                assertEquals("bar", (matched as Foo.B).value)
            }
        }
    }

    @Test
    fun testAssertPanic6() {
        val b: Foo = Foo.B("foo")
        assertFailsWith<AssertionError> {
            assertMatchesYield(
                b,
                """Foo.B(s) if s == "foo"""",
                { it is Foo.B && it.value == "foo" },
            ) { matched ->
                assertEquals("foo", (matched as Foo.B).value)
                throw AssertionError("assertion failed: false")
            }
        }
    }

    @Test
    fun testAssertNoMove() {
        val b: Foo = Foo.A(0)
        assertMatches(b, "Foo.A(0)") { it is Foo.A && it.value == 0 }
    }

    @Test
    fun assertWithMessage() {
        val a: Foo = Foo.A(0)

        assertMatches(a, "Foo.A(_)", "o noes") { it is Foo.A }
        assertMatches(a, "Foo.A(n) if n == 0", "o noes") { it is Foo.A && it.value == 0 }
        assertMatchesYield(a, "Foo.A(n)", "o noes", { it is Foo.A }) { matched ->
            assertEquals(0, (matched as Foo.A).value)
        }
        assertMatchesYield(a, "Foo.A(n)", "o noes", { it is Foo.A }) { matched ->
            assertEquals(0, (matched as Foo.A).value)
            check(matched.value < 1)
        }
        assertMatchesYield(
            a,
            "Foo.A(n) if n == 0",
            "o noes",
            { it is Foo.A && it.value == 0 },
        ) { matched ->
            assertEquals(0, (matched as Foo.A).value)
        }
        assertMatchesYield(
            a,
            "Foo.A(n) if n == 0",
            "o noes",
            { it is Foo.A && it.value == 0 },
        ) { matched ->
            assertEquals(0, (matched as Foo.A).value)
            check(matched.value < 1)
        }
        assertMatches(a, "Foo.A(_)", "o noes $a") { it is Foo.A }
        assertMatches(a, "Foo.A(n) if n == 0", "o noes $a") { it is Foo.A && it.value == 0 }
        assertMatchesYield(a, "Foo.A(n)", "o noes $a", { it is Foo.A }) { matched ->
            assertEquals(0, (matched as Foo.A).value)
        }
        assertMatchesYield(a, "Foo.A(n)", "o noes $a", { it is Foo.A }) { matched ->
            assertEquals(0, (matched as Foo.A).value)
            check(matched.value < 1)
        }
        assertMatches(a, "Foo.A(_)", "o noes value=$a") { it is Foo.A }
        assertMatches(a, "Foo.A(n) if n == 0", "o noes value=$a") {
            it is Foo.A && it.value == 0
        }
        assertMatchesYield(a, "Foo.A(n)", "o noes value=$a", { it is Foo.A }) { matched ->
            assertEquals(0, (matched as Foo.A).value)
        }
        assertMatchesYield(a, "Foo.A(n)", "o noes value=$a", { it is Foo.A }) { matched ->
            assertEquals(0, (matched as Foo.A).value)
            check(matched.value < 1)
        }
        assertMatchesYield(
            a,
            "Foo.A(n) if n == 0",
            "o noes value=$a",
            { it is Foo.A && it.value == 0 },
        ) { matched ->
            assertEquals(0, (matched as Foo.A).value)
        }
    }

    private fun panicMessage(block: () -> Unit): String {
        val err = assertFailsWith<AssertionError>("function did not panic", block)
        return err.message ?: error("function panicked with non-String value")
    }

    @Test
    fun testPanicMessage() {
        val a: Foo = Foo.A(1)

        // value, predicate
        assertEquals(
            "assertion failed: `A(value=1)` does not match `Foo.B(_)`",
            panicMessage { assertMatches(a, "Foo.B(_)") { it is Foo.B } },
        )

        // value, predicate (with guard inlined)
        assertEquals(
            """assertion failed: `A(value=1)` does not match `Foo.B(s) if s == "foo"`""",
            panicMessage {
                assertMatches(a, """Foo.B(s) if s == "foo"""") {
                    it is Foo.B && it.value == "foo"
                }
            },
        )

        // value, predicate, arm
        assertEquals(
            "assertion failed: `A(value=1)` does not match `Foo.B(_)`",
            panicMessage { assertMatchesYield(a, "Foo.B(_)", { it is Foo.B }) {} },
        )

        // value, predicate (with guard inlined), arm
        assertEquals(
            """assertion failed: `A(value=1)` does not match `Foo.B(s) if s == "foo"`""",
            panicMessage {
                assertMatchesYield(
                    a,
                    """Foo.B(s) if s == "foo"""",
                    { it is Foo.B && it.value == "foo" },
                ) {}
            },
        )

        // value, predicate, args
        assertEquals(
            "assertion failed: `A(value=1)` does not match `Foo.B(_)`: msg",
            panicMessage { assertMatches(a, "Foo.B(_)", "msg") { it is Foo.B } },
        )

        // value, predicate (with guard inlined), args
        assertEquals(
            """assertion failed: `A(value=1)` does not match `Foo.B(s) if s == "foo"`: msg""",
            panicMessage {
                assertMatches(a, """Foo.B(s) if s == "foo"""", "msg") {
                    it is Foo.B && it.value == "foo"
                }
            },
        )

        // value, predicate, arm, args
        assertEquals(
            "assertion failed: `A(value=1)` does not match `Foo.B(_)`: msg",
            panicMessage { assertMatchesYield(a, "Foo.B(_)", "msg", { it is Foo.B }) {} },
        )

        // value, predicate (with guard inlined), arm, args
        assertEquals(
            """assertion failed: `A(value=1)` does not match `Foo.B(s) if s == "foo"`: msg""",
            panicMessage {
                assertMatchesYield(
                    a,
                    """Foo.B(s) if s == "foo"""",
                    "msg",
                    { it is Foo.B && it.value == "foo" },
                ) {}
            },
        )
    }
}
