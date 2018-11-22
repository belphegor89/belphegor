package utils.executioncontext;

import org.apache.log4j.Logger;

import java.util.Map;

public class ExecutionContextHandler {

    private static final Logger logger = Logger.getLogger(ExecutionContextHandler.class);
    private static final String PREFIX = "EC_";

    private static ThreadLocal<ExecutionContext> handler = new ThreadLocal<ExecutionContext>() {
        @Override
        protected ExecutionContext initialValue() {
            return new ExecutionContext(PREFIX);
        }
    };

    public static String getExecutionContextValueByKey(String key) {
        ExecutionContext executionContext = handler.get();
        if (executionContext.getValues().containsKey(key)) {
            return executionContext.getValue(key);
        } else {
            logger.warn("Requested " + key + " execution context key is absent.");
        }
        return key;
    }

    public static void setExecutionContextValueByKey(String key, String value) {
        handler.get().setValue(key, value);
    }

    public static void resetExecutionContextValue() {
        handler.get().reset();
    }

    public static Map<String, String> getAllValues() {
        return handler.get().getValues();
    }
}
