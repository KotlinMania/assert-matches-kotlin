# Immediate Actions - High-Value Files

Based on AST analysis and symbol parity verification against upstream Rust crate `assert_matches` (1.5.0 in `tmp/assert_matches/`).

## Summary

- **Files Present:** 2/2 focused source files (`AssertMatches.kt`, `DebugAssertMatches.kt`)
- **Symbol Parity:** 100% matched
  - Production symbols: 1/1 (100.0%) - 0 missing
  - Test symbols: 14/14 (14 test functions matched, including `panicMessage` in `LibTest.kt`) - 100.0%
  - Production definitions: 1/1 (100.0%)
  - Test definitions: 14/14 (100.0%)
  - Extra (Kotlin-only): 37 real + 0 stubs (type-safe overloads for expressions, messages, and assertions)
- **Swift Export Test Parity:** 6/6 tests passing in Swift 6 mode (`swift-test-harness/`)
- **Android Publication:** Configured with `kotlinmania:android-publication:v1` and verified public classes

## Work Items Completed

- Removed forbidden `// port-lint: source lib.rs` directives per AGENTS.md §3 / Rule 4.
- Split into focused Kotlin source files: `AssertMatches.kt` and `DebugAssertMatches.kt`.
- Strict Swift 6 export configuration wired with `io.github.kotlinmania:swift-export-embeddable:2.4.20-km.1`.
- Android publication bundle fragment wired into `build.gradle.kts`.
- Verification test suite added in `verification/`.
- Full 17-target build and test gate verified (`./gradlew test` and `./gradlew build`).
