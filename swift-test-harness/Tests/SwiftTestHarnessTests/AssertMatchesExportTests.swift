#if canImport(Testing)
import Testing
import AssertMatches

@Suite("AssertMatches Swift Export Tests")
struct AssertMatchesExportTests {
    @Test("AssertMatches swift module imported cleanly")
    func testSwiftModuleLoads() throws {
        #expect(Bool(true), "AssertMatches swift module imported cleanly")
    }
}
#elseif canImport(XCTest)
import XCTest
import AssertMatches

final class AssertMatchesExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(true, "AssertMatches swift module imported cleanly")
    }
}
#endif
