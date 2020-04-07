package com.legocms.core.vo.cms;

import java.util.List;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CmsModelVo extends Vo {

    private static final long serialVersionUID = 5604774021198674640L;

    private String code;
    private String name;
    private boolean hasFiles;
    private boolean hasImages;
    private TypeInfo parent;
    private TypeInfo template;

    private List<CmsModelAttributeVo> attributes;

    public void setHasFiles(boolean hasFiles) {
        this.hasFiles = hasFiles;
    }

    public boolean getHasFiles() {
        return this.hasFiles;
    }

    public void setHasImages(boolean hasImages) {
        this.hasImages = hasImages;
    }

    public boolean getHasImages() {
        return this.hasImages;
    }
}
