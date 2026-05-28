package listeners;



import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    // Current retry count
    private int retryCount = 0;

    // Maximum retry count
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {

        // Retry until max count reached
        if (retryCount < maxRetryCount) {

            retryCount++;

            System.out.println(
                    "Retrying Test: "
                            + result.getName()
                            + " | Retry Count: "
                            + retryCount
            );

            return true;
        }

        return false;
    }
}