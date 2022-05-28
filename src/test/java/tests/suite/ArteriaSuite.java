package tests.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({
        "tests.general",
        "tests.arch.arteria"
})
@Suite


public class ArteriaSuite {
}
