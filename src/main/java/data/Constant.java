package data;

public class Constant {

    public static class Data {
        public static final String SECURITY_CODE = "123";
    }

    public static class Tags {
        public static final String ALL = "ALL";
        public static final String ARTERIA = "ARTERIA";
        public static final String ARIA = "ARIA";
        public static final String CROSS = "X86-64";
    }

    public static class JenkinsParameters {
        public static final String JENKINS_DEVICE_IP = System.getenv("DEVICE_IP");
        public static final String JENKINS_BROWSER = System.getenv("BROWSER");
        public static final Boolean JENKINS_START_SELENOID = Boolean.valueOf(System.getenv("START_SELENOID"));
        public static final Boolean JENKINS_DROP_KRIS_SQL = Boolean.valueOf(System.getenv("DROP_KRIS_SQL"));
        public static final Boolean JENKINS_RESTORE_KRIS_SQL = Boolean.valueOf(System.getenv("RESTORE_KRIS_SQL"));
    }

}
