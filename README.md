# assert-matches-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fassert--matches--kotlin-blue.svg)](https://github.com/KotlinMania/assert-matches-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/assert-matches-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/assert-matches-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/assert-matches-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/assert-matches-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`murarth/assert_matches`](https://github.com/murarth/assert_matches).

**Original Project:** This port is based on [`murarth/assert_matches`](https://github.com/murarth/assert_matches). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `murarth/assert_matches`

> The text below is reproduced and lightly edited from [`https://github.com/murarth/assert_matches`](https://github.com/murarth/assert_matches). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## assert_matches

Provides a macro, `assert_matches`, which tests whether a value
matches a given pattern, causing a panic if the match fails.

[Documentation](https://docs.rs/assert_matches/)

```rust
#[macro_use] extern crate assert_matches;

#[derive(Debug)]
enum Foo {
    A(i32),
    B(i32),
}

let a = Foo::A(1);

assert_matches!(a, Foo::A(_));

assert_matches!(a, Foo::A(i) if i > 0);
```

To include in your project, only when tests are compiled, add the following
to your Cargo.toml:

```toml
[dev-dependencies]
assert_matches = "1.5"
```

And add the following to your crate root:

```rust
#[cfg(test)] #[macro_use]
extern crate assert_matches;
```

## License

`assert_matches` is distributed under the terms of both the MIT license and the
Apache License (Version 2.0).

See LICENSE-APACHE and LICENSE-MIT for details.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:assert-matches-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`murarth/assert_matches`](https://github.com/murarth/assert_matches). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the assert_matches authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`murarth/assert_matches`](https://github.com/murarth/assert_matches) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
