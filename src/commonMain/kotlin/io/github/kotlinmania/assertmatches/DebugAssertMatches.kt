// port-lint: source src/lib.rs
package io.github.kotlinmania.assertmatches

/**
 * Asserts that an expression matches a given predicate.
 *
 * Unlike [assertMatches], `debugAssertMatches` is conceptually the
 * non-optimized-build counterpart: in Rust, `debug_assert_matches!`
 * statements are only enabled in non-optimized builds by default, and an
 * optimized build omits them unless `-C debug-assertions` is passed to the
 * compiler.
 *
 * Kotlin Multiplatform has no portable equivalent of Rust's
 * `debug_assertions` flag, so this helper always evaluates its arguments
 * and delegates to [assertMatches]. Callers who need release-mode elision
 * should guard the call themselves.
 *
 * See the [assertMatches] documentation for more information.
 */
public inline fun <T> debugAssertMatches(
    value: T,
    patternDesc: String,
    predicate: (T) -> Boolean,
) {
    assertMatches(value, patternDesc, predicate)
}

/**
 * Asserts that an expression matches a given predicate, with a custom
 * failure message. See [debugAssertMatches] for the always-on caveat that
 * applies to the entire `debugAssertMatches` family on Kotlin Multiplatform.
 */
public inline fun <T> debugAssertMatches(
    value: T,
    patternDesc: String,
    message: String,
    predicate: (T) -> Boolean,
) {
    assertMatches(value, patternDesc, message, predicate)
}

/**
 * Asserts that an expression matches a given predicate, then runs `arm` on
 * the value to yield a result. See [debugAssertMatches] for the always-on
 * caveat that applies to the entire `debugAssertMatches` family on Kotlin
 * Multiplatform.
 */
public inline fun <T, R> debugAssertMatchesYield(
    value: T,
    patternDesc: String,
    predicate: (T) -> Boolean,
    arm: (T) -> R,
): R = assertMatchesYield(value, patternDesc, predicate, arm)

/**
 * Asserts that an expression matches a given predicate, runs `arm` on the
 * value, and appends a custom message to the failure text on mismatch. See
 * [debugAssertMatches] for the always-on caveat that applies to the entire
 * `debugAssertMatches` family on Kotlin Multiplatform.
 */
public inline fun <T, R> debugAssertMatchesYield(
    value: T,
    patternDesc: String,
    message: String,
    predicate: (T) -> Boolean,
    arm: (T) -> R,
): R = assertMatchesYield(value, patternDesc, message, predicate, arm)
