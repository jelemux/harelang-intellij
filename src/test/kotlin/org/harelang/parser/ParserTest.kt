package org.harelang.parser


import com.intellij.testFramework.ParsingTestCase
import org.harelang.HareFileType
import org.harelang.HareParserDefinition
import org.junit.Test

class ParserTests : ParsingTestCase("", HareFileType.defaultExtension, HareParserDefinition()) {
    override fun getTestDataPath() = "src/test/testdata/parsing"

    override fun skipSpaces() = true

    private fun printTree(code: String) {
        println(name)

        myFile = createPsiFile(
            testName, code
        )
        ensureParsed(myFile)
        println(toParseTreeText(myFile, skipSpaces(), includeRanges()))
    }

    @Test
    fun testPrintParseImports() {
        printTree("""use a::b::c; use a::b::{a}; use a::b::*; use a::b::{a, b, c = d};""")
    }

    @Test
    fun testParseImports() {
        doCodeTest("""use a::b::c; use a::b::{a}; use a::b::*; use a::b::{a, b, c = d};""")
    }

    @Test
    fun testParseFunction() {
        doCodeTest(
            """fn huh(a:int) void = {
       let i = 0;
        let s = "";
        i = s;
    };"""
        )
    }

    @Test
    fun testParseFunction2() {
        doCodeTest(
            """fn huh(a:int) void; fn a::b::foo() i32;"""
        )
    }

    @Test
    fun testParseGlobalBinding() {
        doCodeTest("""
            let a:i32 = 1;
            const a:i64 = 1232;
        """.trimIndent())
    }

    @Test
    fun testParseDef() {
        doCodeTest("""
            def a: i32 = i;
        """.trimIndent())
    }

}