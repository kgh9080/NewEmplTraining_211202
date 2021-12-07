package com.nuri.test.beans.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AttachFileVO {
    private String fileName;
    private String uploadPath;

}
