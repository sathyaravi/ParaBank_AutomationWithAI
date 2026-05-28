package utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.DataProvider;

public class DataProviderUtil {

    @DataProvider(name = "loginData")

    public static Object[][] getLoginData() {

        JsonNode jsonData =
                JsonReaderUtil.readJsonFile(
                        "src/test/resources/loginData.json"
                );

        Object[][] data =
                new Object[jsonData.size()][1];

        for (int i = 0; i < jsonData.size(); i++) {

            data[i][0] = jsonData.get(i);
        }

        return data;
    }
}