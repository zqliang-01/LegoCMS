package com.legocms.data.base;

import static java.text.MessageFormat.format;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.CoreException;
import com.legocms.core.vo.Vo;
import com.legocms.data.sql.IdGenerator;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private Date createTime;

    protected BaseEntity() { }

    protected BaseEntity(String code) {
        super();
        this.id = IdGenerator.getCurrent().nextId(this);
        this.code = (StringUtil.isBlank(code) ? id.toString() : code);
        this.setVersion(1);
        this.createTime = DateUtil.getCurrentDate();
    }

    /** 创建VO 子类覆盖 */
    public Vo buildVO() {
        throw new RuntimeException("此方法子类须覆写！");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity baseEntity = (BaseEntity) obj;
        return this.getId().longValue() == baseEntity.getId().longValue();
    }

    @Override
    public int hashCode() {
        CoreException.check(id != null, this.getClass().getName() + " id为null");
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        sb.append("[id=" + this.getId());
        sb.append("[code=" + this.getCode());
        sb.append(",version=" + this.getVersion());
        sb.append("]");
        return sb.toString();
    }

    public final Map<String, String> buildReadableSnapshot() {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        doBuildReadableSnapshot(attributes);
        return attributes;
    }

    public final String buildReadableSnapshotString() {
        Map<String, String> attributes = buildReadableSnapshot();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(format("{0}:{1}", entry.getKey(), entry.getValue()));
            i++;
            if (i < attributes.entrySet().size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        throw new CoreException("未实现 Snapshot");
    }

    public void checkVersion(Integer version) {
        if (this.version.compareTo(version) != 0) {
            throw new CoreException("Error Entity version");
        }
    }
}
