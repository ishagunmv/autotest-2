package tests.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static data.Constant.Tags.*;

@SelectPackages("tests.webinterface")
@Suite
@IncludeTags({ALL, CROSS})

public class CrossSuite {
}
