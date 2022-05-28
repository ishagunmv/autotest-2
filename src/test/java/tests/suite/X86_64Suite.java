package tests.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({
        "tests.general",
        "tests.arch.x86_64"
})
@Suite

public class X86_64Suite {
}
