package org.fileserver.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="file")
public class File {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;
    @Column(name="deleted")
    boolean deleted;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "file")
    Metadata metadata;
    @Transient
    byte[] bytes;

    public File() {
    }

    public File(String id) {
        this.id = id;
    }

    public File(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getId() {
        return id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
