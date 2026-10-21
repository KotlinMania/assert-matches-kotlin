#if canImport(Testing)
import Testing
import AssertMatches
import KotlinRuntimeSupport

@Suite("AssertMatches Swift Export Tests")
struct AssertMatchesExportTests {
    @Test("assertMatches succeeds for matching value")
    func testAssertMatchesSucceed() {
        assertMatches(value: Int32(123), patternDesc: "123") { val in
            (val as? Int32) == 123
        }
    }

    @Test("assertMatches with message succeeds for matching value")
    func testAssertMatchesWithMessage() {
        assertMatches(value: "hello", patternDesc: "hello", message: "must match hello") { val in
            (val as? String) == "hello"
        }
    }

    @Test("assertMatchesYield yields transformed value")
    func testAssertMatchesYield() {
        let result = assertMatchesYield(
            value: "world",
            patternDesc: "world",
            predicate: { val in (val as? String) == "world" },
            arm: { val in (val as? String)?.uppercased() }
        )
        #expect((result as? String) == "WORLD")
    }

    @Test("assertMatchesYield with message yields transformed value")
    func testAssertMatchesYieldWithMessage() {
        let result = assertMatchesYield(
            value: Int32(21),
            patternDesc: "21",
            message: "doubling 21",
            predicate: { val in (val as? Int32) == 21 },
            arm: { val in
                if let num = val as? Int32 {
                    return Int32(num * 2)
                }
                return nil
            }
        )
        #expect((result as? Int32) == 42)
    }

    @Test("debugAssertMatches succeeds for matching value")
    func testDebugAssertMatchesSucceed() {
        debugAssertMatches(value: Int64(999), patternDesc: "999") { val in
            (val as? Int64) == 999
        }
        debugAssertMatches(value: "debug", patternDesc: "debug", message: "debug match") { val in
            (val as? String) == "debug"
        }
    }

    @Test("debugAssertMatchesYield yields value")
    func testDebugAssertMatchesYield() {
        let result = debugAssertMatchesYield(
            value: "test",
            patternDesc: "test",
            predicate: { val in (val as? String) == "test" },
            arm: { val in
                if let s = val as? String {
                    return Int32(s.count)
                }
                return nil
            }
        )
        #expect((result as? Int32) == 4)
    }
}
#elseif canImport(XCTest)
import XCTest
import AssertMatches
import KotlinRuntimeSupport

final class AssertMatchesExportTests: XCTestCase {
    func testAssertMatchesSucceed() {
        assertMatches(value: Int32(123), patternDesc: "123") { val in
            (val as? Int32) == 123
        }
    }

    func testAssertMatchesWithMessage() {
        assertMatches(value: "hello", patternDesc: "hello", message: "must match hello") { val in
            (val as? String) == "hello"
        }
    }

    func testAssertMatchesYield() {
        let result = assertMatchesYield(
            value: "world",
            patternDesc: "world",
            predicate: { val in (val as? String) == "world" },
            arm: { val in (val as? String)?.uppercased() }
        )
        XCTAssertEqual(result as? String, "WORLD")
    }

    func testDebugAssertMatches() {
        debugAssertMatches(value: Int64(999), patternDesc: "999") { val in
            (val as? Int64) == 999
        }
    }
}
#endif
