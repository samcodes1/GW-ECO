package com.rtechnologies.echofriend.entities.task;

public interface TaskUserProjection {
    public Long getTaskuserid();
    public Long getTaskidfk();
    public Long getUseridfk();
    public Boolean getIscomplete();
    public Long getTaskID();
    public String getTaskdescription();
    public Integer getPointsassigned();
    public String getTaskname();
    public Long getTaskcreatedby();
    public Long getTaskcategoryfk();
    public Long getTaskcount();
}