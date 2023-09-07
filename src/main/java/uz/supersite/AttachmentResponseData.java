package uz.supersite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponseData {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
