package ec.femsasalud.com.sales.injection.batch.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VOResponse {

    private String code;
    private String msg;
    private String creditNote;

    public VOResponse setResponse(VOResponse response, String code, String msg) {
        setCode(code);
        setMsg(msg);
        return response;
    }

    public VOResponse setResponse(VOResponse response, String code, String msg, String creditNote) {
        setCode(code);
        setMsg(msg);
        setCreditNote(creditNote);
        return response;
    }
}

