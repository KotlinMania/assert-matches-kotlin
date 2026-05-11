// port-lint: source src/lib.rs
package io.github.kotlinmania.assertmatches

/**
 * Provides a function, [assertMatches], which tests whether a value
 * matches a given predicate, throwing an [AssertionError] if the match fails.
 *
 * See the [assertMatches] documentation for more information.
 *
 * Also provides a debug-only counterpart, [debugAssertMatches].
 *
 * See the [debugAssertMatches] documentation for more information about that
 * helper.
 *
 * Kotlin has no equivalent of Rust's compile-time pattern grammar, so the
 * upstream `macro_rules!` shapes are exposed here as overloaded inline
 * functions: the predicate carries the pattern + optional guard, an optional
 * `arm` lambda yields a value, and an optional `message` is appended to the
 * panic text. The "or-pattern" form `Foo::A(_) | Foo::B(_)` translates to a
 * single `||`-joined boolean expression in the predicate.
 */

/**
 * Asserts that an expression matches a given predicate.
 *
 * The predicate carries both the pattern and any guard expression: a
 * Rust call such as `assertMatches!(a, Foo::A(_))` translates to
 * `assertMatches(a, "Foo.A") { it is Foo.A }`, and a guarded call such as
 * `assertMatches!(a, Foo::A(i) if i > 0)` translates to
 * `assertMatches(a, "Foo.A(i) if i > 0") { it is Foo.A && it.value > 0 }`.
 *
 * `patternDesc` is the textual description that appears in the failure
 * message in place of the upstream `stringify!(pattern)` expansion.
 *
 * ## Example
 *
 * ```
 * import io.github.kotlinmania.assertmatches.assertMatches
 *
 * sealed class Foo {
 *     data class A(val value: Int) : Foo()
 *     data class B(val value: String) : Foo()
 * }
 *
 * val a: Foo = Foo.A(1)
 *
 * // Assert that `a` matches `Foo.A`.
 * assertMatches(a, "Foo.A") { it is Foo.A }
 *
 * // Assert that `a` matches the pattern and that the contained value
 * // meets the condition `i > 0`.
 * assertMatches(a, "Foo.A(i) if i > 0") { it is Foo.A && it.value > 0 }
 * ```
 */
public inline fun <T> assertMatches(
    value: T,
    patternDesc: String,
    predicate: (T) -> Boolean,
) {
    if (!predicate(value)) {
        throw AssertionError("assertion failed: `$value` does not match `$patternDesc`")
    }
}

/**
 * Asserts that an expression matches a given predicate, appending a custom
 * message to the failure text.
 *
 * The Rust macro accepts trailing format-style arguments after the pattern;
 * in Kotlin the message is computed by the caller and passed as a single
 * string. Use Kotlin string templates to mirror the upstream
 * `format_args!(...)` shape.
 *
 * ## Example
 *
 * ```
 * import io.github.kotlinmania.assertmatches.assertMatches
 *
 * val a: Foo = Foo.A(0)
 *
 * assertMatches(a, "Foo.A", "o noes") { it is Foo.A }
 * assertMatches(a, "Foo.A", "o noes $a") { it is Foo.A }
 * ```
 */
public inline fun <T> assertMatches(
    value: T,
    patternDesc: String,
    message: String,
    predicate: (T) -> Boolean,
) {
    if (!predicate(value)) {
        throw AssertionError("assertion failed: `$value` does not match `$patternDesc`: $message")
    }
}

/**
 * Asserts that an expression matches a given predicate, then runs `arm` on
 * the value to perform additional assertions or to yield a value from the
 * call.
 *
 * This mirrors the upstream `assertMatches!(expr, pattern => arm)` shape: the
 * predicate carries the pattern (and optional guard) and `arm` runs only when
 * the predicate succeeds.
 *
 * Named separately from [assertMatches] so that a Boolean-returning `arm`
 * does not collide with a Boolean-returning predicate at overload resolution.
 *
 * ## Example
 *
 * ```
 * import io.github.kotlinmania.assertmatches.assertMatchesYield
 *
 * val b: Foo = Foo.B("foobar")
 *
 * // Assert that `b` matches `Foo.B` and run additional assertions on the
 * // bound value.
 * assertMatchesYield(b, "Foo.B(s)", { it is Foo.B }) { matched ->
 *     val s = (matched as Foo.B).value
 *     check(s.startsWith("foo"))
 *     check(s.endsWith("bar"))
 * }
 *
 * // Assert that `b` matches `Foo.B` and yield the contained string `s`.
 * val s: String = assertMatchesYield(b, "Foo.B(s)", { it is Foo.B }) { matched ->
 *     (matched as Foo.B).value
 * }
 * ```
 */
public inline fun <T, R> assertMatchesYield(
    value: T,
    patternDesc: String,
    predicate: (T) -> Boolean,
    arm: (T) -> R,
): R {
    if (predicate(value)) {
        return arm(value)
    }
    throw AssertionError("assertion failed: `$value` does not match `$patternDesc`")
}

/**
 * Asserts that an expression matches a given predicate, runs `arm` on the
 * value, and appends a custom message to the failure text on mismatch.
 */
public inline fun <T, R> assertMatchesYield(
    value: T,
    patternDesc: String,
    message: String,
    predicate: (T) -> Boolean,
    arm: (T) -> R,
): R {
    if (predicate(value)) {
        return arm(value)
    }
    throw AssertionError("assertion failed: `$value` does not match `$patternDesc`: $message")
}
