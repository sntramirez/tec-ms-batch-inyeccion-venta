package ec.femsasalud.com.sales.injection.batch.shared.common;

public class ProcessingResult {
    private boolean success;
    private String message;
    private String processedBy;
    private String transactionId;

    public ProcessingResult(boolean success, String message, String processedBy, String transactionId) {
        this.success = success;
        this.message = message;
        this.processedBy = processedBy;
        this.transactionId = transactionId;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getProcessedBy() { return processedBy; }
    public String getTransactionId() { return transactionId; }
}

